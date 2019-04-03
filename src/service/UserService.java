package service;

import dao.DAOFactory;
import dao.DiscoveryDAO;
import dao.UserDAO;
import dao.VoteDAO;
import model.Discovery;
import model.User;
import model.Vote;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
    public void addUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        String md5Pass = encryptPassword(password);
        user.setPassword(md5Pass);
        user.setEmail(email);
        user.setActive(true);
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        userDao.create(user);
    }
    public String encryptPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.update(password.getBytes());
        String md5Password = new BigInteger(1, digest.digest()).toString(16);
        return md5Password;
    }
    public User getUserById(long id){
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        User user = userDAO.read(id);
        return user;
    }
    public User getUserByUsername(String username){
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        User user = userDAO.getUserByUsername(username);
        return user;
    }

    public void deleteUser(User user){
        User userCopy = new User(user);
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        DiscoveryDAO discoveryDAO = factory.getDiscoveryDAO();
        VoteDAO voteDAO = factory.getVoteDAO();
        voteDAO.deleteAllByUserID(userCopy.getId());
        discoveryDAO.deleteAllByUserID(userCopy.getId());
        userDAO.delete(userCopy);
    }
}
