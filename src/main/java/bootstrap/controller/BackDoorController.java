package bootstrap.controller;

import bootstrap.model.Role;
import bootstrap.model.User;
import bootstrap.service.RoleService;
import bootstrap.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class BackDoorController {
    private static final List<Role> ROLES = Arrays.asList(new Role("ADMIN"), new Role("USER"));

    private final RoleService roleService;
    private final UserService userService;

    public BackDoorController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @RequestMapping("/init")
    public ModelAndView init() {
        List<Role> roles = roleService.getAll();
        if (roles == null || roles.isEmpty()) {
            ROLES.forEach(roleService::add);
        }
        User user = userService.getUserByEmail("ADMIN");
        if (user == null) {
            user = new User("ADMIN", "ADMIN", Collections.singletonList(new Role("ADMIN")));
            userService.addUser(user);
        }
        return new ModelAndView("/login");
    }
}
