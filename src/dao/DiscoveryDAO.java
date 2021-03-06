package dao;

import model.Discovery;

import java.util.List;

public interface DiscoveryDAO extends GenericDAO<Discovery, Long>{

    List<Discovery> getAll();
    List<Discovery> getAllFromLastDay();

    boolean deleteAllByUserID(Long key);
}