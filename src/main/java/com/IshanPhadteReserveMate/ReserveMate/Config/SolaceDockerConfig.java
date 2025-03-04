// package com.IshanPhadteReserveMate.ReserveMate.Config;

// import com.github.dockerjava.api.DockerClient;
// import com.github.dockerjava.api.command.CreateContainerResponse;
// import com.github.dockerjava.api.model.HostConfig;
// import com.github.dockerjava.api.model.PortBinding;
// import com.github.dockerjava.api.model.Ports;
// import com.github.dockerjava.api.model.ExposedPort;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class SolaceDockerConfig {

//     @Bean
//     public void startSolaceContainer() {
//         DockerClient dockerClient = DockerClientProvider.createDockerClient();

//         CreateContainerResponse container = dockerClient.createContainerCmd("solace/solace-pubsub-standard")
//                 .withName("solace-broker")
//                 .withEnv("username_admin_globalaccesslevel=admin", "username_admin_password=admin", "VPNNAME=default")
//                 .withHostConfig(new HostConfig()
//                         .withPortBindings(new PortBinding(Ports.Binding.bindPort(55555), new ExposedPort(55555))))
//                 .exec();

//         dockerClient.startContainerCmd(container.getId()).exec();
//         System.out.println("Solace Broker Started via Docker API");
//     }
// }
