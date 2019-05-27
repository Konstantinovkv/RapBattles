package rapbattles.rap_battles.DAO;

import rapbattles.rap_battles.Models.POJO.UserAvatar;

public interface UserAvatarDAO {

    void uploadImage(UserAvatar userAvatar);

    public String findImageById(int user_ID);
}
