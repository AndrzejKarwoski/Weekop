package dao;

import model.User;

import java.util.List;

public interface UserDAO extends GenericDAO<User, Long> {

    boolean delete(User user);

    List<User> getAll();
    User getUserByUsername(String username);

}