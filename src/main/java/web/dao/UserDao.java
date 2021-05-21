package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    void createUser(String firstName, String lastName, int telNumber);
    User readUserById(long id);
    void updateUser (User user);
    void deleteUser(long id);
    List<User> getAllUsers();
}