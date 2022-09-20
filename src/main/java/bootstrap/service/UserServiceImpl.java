package bootstrap.service;

import bootstrap.model.Role;
import bootstrap.model.User;
import bootstrap.repository.RoleRepository;
import bootstrap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
// открывает/закрывает транзакции БД
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("Empty list");
        }
        return users;
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else throw new UsernameNotFoundException("User with ID: " + userId + "not found");
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
        // проверяем, что пароль не имеет префикс как ниже
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

    // реализуем public interface UserDetailsService
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
