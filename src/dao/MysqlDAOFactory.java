package dao;


public class MysqlDAOFactory extends DAOFactory {

    @Override
    public DiscoveryDAO getDiscoveryDAO() {
        return new DiscoveryDAOImplementation();
    }

    @Override
    public VoteDAO getVoteDAO() {
        return new VoteDAOImplementation();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImplementation();
    }
}
