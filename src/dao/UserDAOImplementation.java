package dao;

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

public class UserDAOImplementation implements UserDAO {
    private static final String CREATE_USER =
            "INSERT INTO user(username, email, password, is_active) VALUES(:username, :email, :password, :active);";
    private static final String READ_USER =
            "SELECT user_id, username, email, password, is_active FROM user WHERE user_id = :id";
    private static final String READ_USER_BY_USERNAME =
            "SELECT user_id, username, email, password, is_active FROM user WHERE username = :username";
    private static final String DELETE_USER_FROM_ROLES =
            "DELETE FROM user_role WHERE username = :username";
    private static final String DELETE_USER =
            "DELETE FROM user WHERE user_id = :user_id";
    private static final String UPDATE_USER_PASSWORD =
            "UPDATE user SET password=:password WHERE user_id=:user_id";
    private NamedParameterJdbcTemplate template;

    public UserDAOImplementation(){
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }

    @Override
    public User create(User user) {
        User result = new User(user);
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        int update = template.update(CREATE_USER,parameterSource,holder);
        if(update > 0 ){
            result.setId(holder.getKey().longValue());
            setPrivigiles(result);
        }
        return result;
    }

    private void setPrivigiles(User user) {
        final String userRoleQuery = "INSERT INTO user_role(username) VALUES(:username)";
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
        template.update(userRoleQuery, paramSource);
    }

    @Override
    public User read(Long key) {
        User result = null;
        SqlParameterSource parameterSource = new MapSqlParameterSource("id",key);
        result = template.queryForObject(READ_USER,parameterSource,new UserRowMapper());
        return result;
    }

    @Override
    public boolean update(User object) {
        return false;
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setEmail(resultSet.getString("email"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    }

    @Override
    public boolean updatePassword(User user) {
        Map<String,Object> parameterMap = new HashMap<>();
        parameterMap.put("user_id",user.getId());
        parameterMap.put("password",user.getPassword());
        SqlParameterSource parameterSource = new MapSqlParameterSource(parameterMap);
        template.update(UPDATE_USER_PASSWORD,parameterSource);
        return true;
    }

    @Override
    public boolean delete(Long key) {
        return false;
    }
    @Override
    public boolean delete(User user) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("username", user.getUsername());
        template.update(DELETE_USER_FROM_ROLES,namedParameter);
        namedParameter = new MapSqlParameterSource("user_id", user.getId());
        template.update(DELETE_USER,namedParameter);
        return true;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        User resultUser = null;
        SqlParameterSource paramSource = new MapSqlParameterSource("username", username);
        resultUser = template.queryForObject(READ_USER_BY_USERNAME, paramSource, new UserRowMapper());
        return resultUser;
    }
}
