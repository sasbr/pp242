package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void createUser(String firstName, String lastName, int telNumber, String userName, String password, Set<Role> roles);
    User readUserById(long id);
    void updateUser (User user);
    void deleteUser(long id);
    List<User> getAllUsers();
    User findByUsername(String username);
}
