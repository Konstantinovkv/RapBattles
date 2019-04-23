package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.ServiceImpl.UserServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.InvalidPasswordException;
import rapbattles.rap_battles.Util.Exceptions.InvalidUsernameOrEmailException;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController{

    public static final String LOGGED = "logged";


    @Autowired
    UserServiceImplem usi;

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody User user, HttpSession session) throws InvalidUsernameOrEmailException, InvalidPasswordException {
        return usi.addUser(user, session);
    }

    @PostMapping("/activate/{activation_code}")
    public void activateAccount(@PathVariable(value = "activation_code") String activation_code){
        usi.activateAccountService(activation_code);
    }


}
