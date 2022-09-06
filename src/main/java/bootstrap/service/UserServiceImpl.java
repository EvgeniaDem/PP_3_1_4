package bootstrap.service;

import bootstrap.model.Role;
import bootstrap.model.User;
import bootstrap.repository.RoleRepository;
import bootstrap.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public void addUser(User user) {
        if (getUserByEmail(user.getEmail()) == null) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user.setRoles(updateRoles(user.getRoles())));
        }
    }

    @Override
    public void editUser(User user) {
        if (!user.getPassword().startsWith("$2a$10$")) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user.setRoles(updateRoles(user.getRoles())));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    private List<Role> updateRoles(List<Role> roles) {
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(Role::getName)
                .map(roleRepository::getRoleByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
