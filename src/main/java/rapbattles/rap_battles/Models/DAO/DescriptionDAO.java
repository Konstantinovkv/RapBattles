package rapbattles.rap_battles.Models.DAO;
import rapbattles.rap_battles.Models.POJO.Description;

public interface DescriptionDAO {

    void updateDescription(String description, int user_ID);

    void addDescription(String description, int user_ID);

    boolean findDescriptionById(int user_ID);
}
