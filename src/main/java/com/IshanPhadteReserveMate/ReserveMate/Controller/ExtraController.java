package com.IshanPhadteReserveMate.ReserveMate.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class ExtraController {
    

    @GetMapping("coming-soon")
    public RedirectView getMethodName() {
        return new RedirectView("coming-soon.html");
    }
    

}
