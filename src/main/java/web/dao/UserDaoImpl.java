package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createUser(String firstName, String lastName, int telNumber, String userName, String password, Set<Role> roles) {
        entityManager.persist(new User(firstName, lastName, telNumber, userName, password, roles));
    }

    @Override
    public User readUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User findByUsername(String username) {
        return entityManager.createQuery("select u from User u where u.userName = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}