package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Util.Exceptions.InvalidUsernameOrEmailException;
import rapbattles.rap_battles.Util.Exceptions.InvalidPasswordException;

import javax.servlet.http.HttpSession;

public interface UserService {

    public UserDTO addUser(User user, HttpSession session) throws InvalidUsernameOrEmailException, InvalidPasswordException;
}
