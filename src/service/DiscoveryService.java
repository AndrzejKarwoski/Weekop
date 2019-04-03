package service;

import dao.DAOFactory;
import dao.DiscoveryDAO;
import model.Discovery;
import model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DiscoveryService {

    public void addDiscovery(String name, String description, String url, User user){
        Discovery discovery = createDiscovery(name,description,url,user);
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDAO = factory.getDiscoveryDAO();
        discoveryDAO.create(discovery);
    }

    private Discovery createDiscovery(String name,String description,String url,User user){
        Discovery discovery = new Discovery();
        discovery.setName(name);
        discovery.setDescription(description);
        discovery.setUrl(url);
        User tempUser = new User(user);
        discovery.setUser(tempUser);
        discovery.setTimestamp(new Timestamp(new Date().getTime()));
        return discovery;
    }
    public Discovery getDiscoveryById(long discoveryId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDao = factory.getDiscoveryDAO();
        Discovery discovery = discoveryDao.read(discoveryId);
        return discovery;
    }

    public boolean updateDiscovery(Discovery discovery) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDao = factory.getDiscoveryDAO();
        boolean result = discoveryDao.update(discovery);
        return result;
    }

    public List<Discovery> getAllDiscoveries(Comparator<Discovery> comparator){
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDAO = factory.getDiscoveryDAO();
        List<Discovery> list = discoveryDAO.getAll();
        if(comparator != null && list != null) {
            list.sort(comparator);
        }
        return list;
    }
    public List<Discovery> getAllDiscoveriesFromLastDay(Comparator<Discovery> comparator){
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDAO = factory.getDiscoveryDAO();
        List<Discovery> list = discoveryDAO.getAllFromLastDay();
        if(comparator != null && list != null) {
            list.sort(comparator);
        }
        return list;
    }
}
