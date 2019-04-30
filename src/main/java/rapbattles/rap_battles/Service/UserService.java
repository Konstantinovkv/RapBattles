package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Util.Exceptions.*;

import javax.servlet.http.HttpSession;

public interface UserService {

    UserDTO addUser(User user, HttpSession session) throws MainException;

    UserDTO login(User user, HttpSession session) throws MainException;
}
