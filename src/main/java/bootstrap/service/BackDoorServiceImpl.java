package bootstrap.service;

import bootstrap.model.Role;
import bootstrap.model.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BackDoorServiceImpl implements BackDoorService {

    private static final List<Role> ROLES = Arrays.asList(new Role("ADMIN"), new Role("USER"));

    private final RoleService roleService;

    private final UserService userService;

    public BackDoorServiceImpl(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void init() {
        List<Role> roles = roleService.getAll();
        if (roles == null || roles.isEmpty()) {
            ROLES.forEach(roleService::add);
        }
        User user = userService.getUserByEmail("ADMIN");
        if (user == null) {
            user = new User("ADMIN", "ADMIN", Collections.singletonList(new Role("ADMIN")));
            userService.addUser(user);
        }
    }
}
