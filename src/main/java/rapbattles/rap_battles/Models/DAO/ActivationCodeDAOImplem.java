package rapbattles.rap_battles.Models.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.Models.POJO.UserActivationCode;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ActivationCodeDAOImplem implements ActivationCodeDAO{

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void uploadActivationCode(int user_ID, String activation_code) {
        String sql = "INSERT INTO user_activation_code(user_ID,activation_code) VALUES(?,?)";

        jdbc.update(sql, new Object[]{user_ID, activation_code});
    }

    public UserActivationCode findUserIdByActivationCode(String activation_code){
        try {
        String sql = "SELECT user_ID, activation_code  FROM user_activation_code WHERE activation_code= ?";
        UserActivationCode userActivationCode = jdbc.queryForObject(sql, new Object[]{activation_code}, (resultSet, i) -> mapRow(resultSet));
        return userActivationCode;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private UserActivationCode mapRow(ResultSet rs) throws SQLException {
        return new UserActivationCode(rs.getInt("user_ID"), rs.getString("activation_code"));
    }

    private static final class UserActivationCodeRowMapper implements RowMapper {

        public UserActivationCode mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserActivationCode userActivationCode = new UserActivationCode();
            userActivationCode.setUser_ID(rs.getInt("user_ID"));
            userActivationCode.setActivation_code(rs.getString("activation_code"));

            return userActivationCode;
        }
    }


    private SqlParameterSource getSqlParameterByModel(UserActivationCode userActivationCode){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        if(userActivationCode != null){
            parameterSource.addValue("user_ID", userActivationCode.getUser_ID());
            parameterSource.addValue("activation_code", userActivationCode.getActivation_code());
        }
        return parameterSource;
    }

}