package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Util.Exceptions.InvalidUsernameOrEmailException;

import javax.servlet.http.HttpSession;

public interface UserService {

    public void registerUser(User user, HttpSession session) throws InvalidUsernameOrEmailException;
}
