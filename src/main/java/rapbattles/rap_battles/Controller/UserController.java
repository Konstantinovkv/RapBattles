package rapbattles.rap_battles.Controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.ServiceImpl.UserServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.InvalidUsernameOrEmailException;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController{

    public static final String LOGGED = "logged";


    @Autowired
    UserServiceImplem usi;

    @PostMapping(value = "/register")
    public void registerUser(@RequestBody User user, HttpSession session) throws InvalidUsernameOrEmailException {
        usi.registerUser(user, session);
    }


}
