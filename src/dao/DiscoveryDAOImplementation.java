package dao;

import model.Discovery;
import model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import util.ConnectionProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscoveryDAOImplementation implements DiscoveryDAO {

    private static final String CREATE_DISCOVERY =
            "INSERT INTO discovery(name, description, url, user_id, date, up_vote, down_vote) "
                    + "VALUES(:name, :description, :url, :user_id, :date, :up_vote, :down_vote);";
    private static final String READ_ALL_DISCOVERIES =
            "SELECT user.user_id, username, email, is_active, password, discovery_id, name, description, url, date, up_vote, down_vote "
                    + "FROM discovery LEFT JOIN user ON discovery.user_id=user.user_id;";
    private static final String READ_DISCOVERY =
            "SELECT user.user_id, username, email, is_active, password, discovery_id, name, description, url, date, up_vote, down_vote "
                    + "FROM discovery LEFT JOIN user ON discovery.user_id=user.user_id WHERE discovery_id=:discovery_id;";
    private static final String UPDATE_DISCOVERY =
            "UPDATE discovery SET name=:name, description=:description, url=:url, user_id=:user_id, date=:date, up_vote=:up_vote, down_vote=:down_vote "
                    + "WHERE discovery_id=:discovery_id;";
    private static final String READ_ALL_DISCOVERIES_FROM_LAST_DAY =
            "SELECT user.user_id, username, email, is_active, password, discovery_id, name, description, url, date, up_vote, down_vote "
                    + "FROM discovery LEFT JOIN user ON discovery.user_id=user.user_id WHERE date >= now() - INTERVAL 1 DAY";
    private static final String DELETE_ALL_BY_USER_ID =
            "DELETE FROM discovery WHERE user_id=:user_id";

    private NamedParameterJdbcTemplate template;

    public DiscoveryDAOImplementation() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }

    @Override
    public Discovery create(Discovery discovery) {
        Discovery result = new Discovery(discovery);
        KeyHolder holder = new GeneratedKeyHolder();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("name",discovery.getName());
        parameterMap.put("description",discovery.getDescription());
        parameterMap.put("url",discovery.getUrl());
        parameterMap.put("user_id",discovery.getUser().getId());
        parameterMap.put("date",discovery.getTimestamp());
        parameterMap.put("up_vote",discovery.getUpVote());
        parameterMap.put("down_vote",discovery.getDownVote());
        SqlParameterSource parameterSource = new MapSqlParameterSource(parameterMap);
        int update = template.update(CREATE_DISCOVERY,parameterSource,holder);
        if(update > 0 ){
            result.setId(holder.getKey().longValue());
        }
        return result;
    }

    @Override
    public Discovery read(Long key) {
        SqlParameterSource paramSource = new MapSqlParameterSource("discovery_id", key);
        Discovery discovery = template.queryForObject(READ_DISCOVERY, paramSource, new DiscoveryMapRower());
        return discovery;
    }

    @Override
    public boolean update(Discovery discovery) {
        boolean result = false;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("discovery_id", discovery.getId());
        paramMap.put("name", discovery.getName());
        paramMap.put("description", discovery.getDescription());
        paramMap.put("url", discovery.getUrl());
        paramMap.put("user_id", discovery.getUser().getId());
        paramMap.put("date", discovery.getTimestamp());
        paramMap.put("up_vote", discovery.getUpVote());
        paramMap.put("down_vote", discovery.getDownVote());
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(UPDATE_DISCOVERY, paramSource);
        if(update > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(Long key) {
        return false;
    }

    @Override
    public List<Discovery> getAll() {
        List<Discovery> list = template.query(READ_ALL_DISCOVERIES,new DiscoveryMapRower());
        return  list;
    }
    @Override
    public List<Discovery> getAllFromLastDay() {
        List<Discovery> list = template.query(READ_ALL_DISCOVERIES_FROM_LAST_DAY,new DiscoveryMapRower());
        return  list;
    }
    @Override
    public boolean deleteAllByUserID(Long key){
        SqlParameterSource namedParameter = new MapSqlParameterSource("user_id", key);
        template.update(DELETE_ALL_BY_USER_ID,namedParameter);
        return true;
    }

    private class DiscoveryMapRower implements RowMapper<Discovery>{

        @Override
        public Discovery mapRow(ResultSet resultSet, int i) throws SQLException {
            Discovery discovery = new Discovery();
            discovery.setId(resultSet.getLong("discovery_id"));
            discovery.setName(resultSet.getString("name"));
            discovery.setDescription(resultSet.getString("description"));
            discovery.setUrl(resultSet.getString("url"));
            discovery.setUpVote(resultSet.getInt("up_vote"));
            discovery.setDownVote(resultSet.getInt("down_vote"));
            discovery.setTimestamp(resultSet.getTimestamp("date"));
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            discovery.setUser(user);
            return discovery;
        }
    }
}
