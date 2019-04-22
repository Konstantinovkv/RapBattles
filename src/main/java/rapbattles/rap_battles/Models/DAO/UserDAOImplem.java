package rapbattles.rap_battles.Models.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;

@Repository
public class UserDAOImplem implements UserDAO {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void registerUser(User user) {
        String sql = "INSERT INTO users(email,username,password,second_password) VALUES(?,?,?,?)";
        //adds a user to the users table
        jdbc.update(sql, new Object[]{user.getEmail(), user.getUsername(), user.getPassword(), user.getSecond_password()});
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO findUserByID(int user_ID) {
        return null;
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        return null;
    }

    @Override
    public void deleteUserByID(int user_ID) {

    }

//    private SqlParameterSource getSqlParameterByModel(User user){
//        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
//        if(user != null){
//            parameterSource.addValue("user_ID", user.getUser_ID());
//            parameterSource.addValue("email", user.getEmail());
//            parameterSource.addValue("username", user.getUsername());
//            parameterSource.addValue("password", user.getPassword());
//            parameterSource.addValue("salt", user.getSalt());
//        }
//        return parameterSource;
//    }
}
