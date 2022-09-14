package bootstrap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// контроллер только отдает страницу
@Controller
public class RenderPageController {

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/")
    public String mainPage() {
        return "main";
    }
}
