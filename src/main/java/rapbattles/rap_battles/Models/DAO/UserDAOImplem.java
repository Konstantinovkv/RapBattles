package rapbattles.rap_battles.Models.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImplem implements UserDAO {

    @Autowired
    private JdbcTemplate jdbc;

    //adds a user to the users table
    @Override
    public void registerUser(User user) {
        String sql = "INSERT INTO users(email,username,password,second_password,salt) VALUES(?,?,?,?,?)";
        jdbc.update(sql, new Object[]{user.getEmail(), user.getUsername(), user.getPassword(), user.getSecond_password(), user.getSalt()});
    }


    @Override
    public UserDTO findUserByEmail(String email) {
        try{
        String sql = "SELECT user_ID, email, username, activated FROM users WHERE email = ?";
        return (UserDTO) jdbc.queryForObject(sql, new Object[]{email}, new UserDTOMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public UserDTO findUserByID(int user_ID) {
        try{
            String sql = "SELECT user_ID, email, username, activated FROM users WHERE user_ID = ?";
            return (UserDTO) jdbc.queryForObject(sql, new Object[]{user_ID}, new UserDTOMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        try{
        String sql = "SELECT user_ID, email, username, activated FROM users WHERE username = ?";
        return (UserDTO) jdbc.queryForObject(sql, new Object[]{username}, new UserDTOMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void deleteUserByID(int user_ID) {

    }

    public void setActiveToTrue(UserDTO userDTO){
        String sql = "UPDATE users SET activated = 1 WHERE username = ?";
        jdbc.update(sql, new Object[]{userDTO.getUsername()});
    }

    private static final class UserMapper implements RowMapper {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUser_ID(rs.getInt("user_ID"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setSecond_password(rs.getString("second_password"));
            user.setSalt(rs.getString("salt"));
            user.setActivated(rs.getBoolean("activated"));
            return user;
        }

    }

    private static final class UserDTOMapper implements RowMapper {

        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDTO userDTO = new UserDTO();
            userDTO.setUser_ID(rs.getInt("user_ID"));
            userDTO.setEmail(rs.getString("email"));
            userDTO.setUsername(rs.getString("username"));
            userDTO.setActivated(rs.getBoolean("activated"));

            return userDTO;
        }
    }

    private SqlParameterSource getSqlParameterByModel(UserDTO userDTO){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        if(userDTO != null){
            parameterSource.addValue("user_ID", userDTO.getUser_ID());
            parameterSource.addValue("email", userDTO.getEmail());
            parameterSource.addValue("username", userDTO.getUsername());
            parameterSource.addValue("activated", userDTO.isActivated());
        }
        return parameterSource;
    }
}
