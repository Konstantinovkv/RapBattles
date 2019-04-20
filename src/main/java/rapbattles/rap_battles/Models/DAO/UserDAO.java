package rapbattles.rap_battles.Models.DAO;

import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;

public interface UserDAO {

    public void registerUser(User user);

    public UserDTO findUserByEmail(String email);

    public UserDTO findUserByID(int user_ID);

    public UserDTO findUserByUsername(String username);

    public void deleteUserByID(int user_ID);

}
