package bootstrap.controller;

import bootstrap.service.BackDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

// контроллер для первого входа, когда таблицы еще пустые и ни одного юзера не создано
@RestController
public class BackDoorController {

    @Autowired
    private BackDoorService backDoorService;

    @RequestMapping("/init")
    public ModelAndView init() {
        backDoorService.init();
        return new ModelAndView("/login");
    }
}
