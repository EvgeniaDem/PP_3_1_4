package bootstrap.service;

import bootstrap.model.Role;
import bootstrap.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    // добавила из BackDoorController
    private static final List<Role> ROLES = Arrays.asList(new Role("ADMIN"), new Role("USER"));

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository.getRoleByName(name);
    }

/*    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }*/

    // перенесла сюда логику из BackDoorController
    public List<Role> getAll() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            roleRepository.saveAll(ROLES);
        }
        return roles;
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }
}
