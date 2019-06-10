package rapbattles.rap_battles.DAOImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.DAO.UserAvatarDAO;
import rapbattles.rap_battles.Models.POJO.UserAvatar;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserAvatarDAOImplem implements UserAvatarDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public void uploadImage(UserAvatar userAvatar) {
        String sql = "INSERT INTO avatars(user_ID,path) VALUES(?,?)ON DUPLICATE KEY UPDATE path=?";
        jdbc.execute(sql, (PreparedStatementCallback<Boolean>) ps -> {
            ps.setInt(1,userAvatar.getUser_ID());
            ps.setString(2,userAvatar.getPath());
            ps.setString(3,userAvatar.getPath());
            return ps.execute();
        });
    }

    public String findImageById(int user_ID){
        try{
            String sql = "SELECT user_ID, path FROM avatars WHERE user_ID = ?";
            UserAvatar userAvatar = (UserAvatar) jdbc.queryForObject(sql, new Object[]{user_ID}, new UserAvatarMapper());
            return userAvatar.getPath();
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    private static final class UserAvatarMapper implements RowMapper {
        public UserAvatar mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserAvatar userAvatar = new UserAvatar();
            userAvatar.setUser_ID(rs.getInt("user_ID"));
            userAvatar.setPath(rs.getString("path"));
            return userAvatar;
        }
    }
}
