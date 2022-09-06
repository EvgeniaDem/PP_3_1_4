package bootstrap.dao;

import bootstrap.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    void addUser(User user);

    void editUser(User user);

    void deleteUser(User user);

}
