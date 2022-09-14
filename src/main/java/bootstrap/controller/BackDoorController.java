package bootstrap.controller;

import bootstrap.model.Role;
import bootstrap.model.User;
import bootstrap.service.RoleService;
import bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// контроллер для первого входа, когда таблицы еще пустые и ни одного юзера не создано
@RestController
@RequestMapping("/api/v2")
public class BackDoorController {
    private static final List<Role> ROLES = Arrays.asList(new Role("ADMIN"), new Role("USER"));

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping("/init")
    public ModelAndView init() {
        List<Role> roles = roleService.getAll();
        if (roles == null || roles.isEmpty()) {
            ROLES.forEach(roleService::add);
        }
        User user = userService.getUserByEmail("ADMIN");
        if (user == null) {
            // singletonList возвращает immutable List - роль АДМИН нельзя изменить
            user = new User("ADMIN", "ADMIN", Collections.singletonList(new Role("ADMIN")));
            userService.addUser(user);
        }
        return new ModelAndView("/login");
    }
}
