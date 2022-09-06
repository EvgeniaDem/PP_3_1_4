package bootstrap.service;

import bootstrap.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    User getUserByEmail(String email);

    void addUser(User user);

    void editUser(User user);

    void deleteUserById(Long id);
}