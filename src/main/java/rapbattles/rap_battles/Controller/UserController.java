package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rapbattles.rap_battles.Models.DAO.UserDAOImplem;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserDAOImplem dao;

    //test
    @PostMapping(value = "/register")
    public void registerUser(@RequestBody User user, HttpSession session){
        dao.registerUser(user);
    }


}
