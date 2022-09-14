package bootstrap.service;

import bootstrap.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    void addUser(User user);

    void editUser(User user);

    void deleteUserById(Long id);
}