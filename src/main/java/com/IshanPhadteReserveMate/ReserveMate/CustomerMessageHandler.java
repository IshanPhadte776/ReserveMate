package com.IshanPhadteReserveMate.ReserveMate;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class CustomerMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomerMessageHandler.class);
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @JmsListener(destination = "updates/#", containerFactory = "cFactory")
    public void processMsg(Message<?> msg) {
        String topicId = msg.getHeaders().get("topicId", String.class);
        logMessage(msg);

        if (topicId != null && emitters.containsKey(topicId)) {
            sendUpdateToClient(topicId, msg.getPayload().toString());
        }
    }

    private void logMessage(Message<?> msg) {
        logger.info("Received: {}", msg.getPayload());
    }

    private void sendUpdateToClient(String topicId, String message) {
        SseEmitter emitter = emitters.get(topicId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                emitters.remove(topicId);
            }
        }
    }

    @RestController
    @RequestMapping("/updates")
    public static class ReservationUpdatesController {

        private final CustomerMessageHandler messageHandler;

        public ReservationUpdatesController(CustomerMessageHandler messageHandler) {
            this.messageHandler = messageHandler;
        }

        @GetMapping(value = "/stream/{topicId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public SseEmitter streamUpdates(@PathVariable String topicId) {
            SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
            messageHandler.emitters.put(topicId, emitter);

            emitter.onCompletion(() -> messageHandler.emitters.remove(topicId));
            emitter.onTimeout(() -> messageHandler.emitters.remove(topicId));

            return emitter;
        }
    }
}

