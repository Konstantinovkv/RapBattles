package rapbattles.rap_battles.Models.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DescriptionDAOImplem implements DescriptionDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public void updateDescription(String description, int user_ID){
        String sql = "UPDATE descriptions SET user_description = ? WHERE user_ID = ?";
        jdbc.update(sql, new Object[]{description, user_ID});
    }

    public void addDescription(String description, int user_ID){
        String sql = "INSERT INTO descriptions(user_ID, user_description) VALUES(?,?)";
        jdbc.update(sql, new Object[]{user_ID,description});
    }

    public boolean findDescriptionById(int user_ID){
        try{
            String sql = "SELECT user_ID FROM descriptions WHERE user_ID = "+user_ID;
            jdbc.execute(sql);
            return true;
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
    }
}