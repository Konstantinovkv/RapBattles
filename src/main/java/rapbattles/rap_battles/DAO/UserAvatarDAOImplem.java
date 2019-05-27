package rapbattles.rap_battles.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.Models.POJO.UserAvatar;

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
}
