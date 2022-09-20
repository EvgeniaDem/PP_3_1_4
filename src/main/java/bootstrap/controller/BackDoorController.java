package bootstrap.controller;

import bootstrap.model.Role;
import bootstrap.model.User;
import bootstrap.service.RoleService;
import bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

// контроллер для первого входа, когда таблицы еще пустые и ни одного юзера не создано
@RestController
@RequestMapping
public class BackDoorController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping("/init")
    public ModelAndView init() {
        roleService.getAll();

        User user = userService.getUserByEmail("ADMIN");
        if (user == null) {
            // singletonList возвращает immutable List - роль АДМИН нельзя изменить
            user = new User("ADMIN", "ADMIN", Collections.singletonList(new Role("ADMIN")));
            userService.addUser(user);
        }
        return new ModelAndView("/login");
    }
}
