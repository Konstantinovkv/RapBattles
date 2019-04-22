package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.Controller.BaseController;
import rapbattles.rap_battles.Models.DAO.UserDAOImplem;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Service.UserService;
import rapbattles.rap_battles.Util.EmailSender;
import rapbattles.rap_battles.Util.Exceptions.InvalidUsernameOrEmailException;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class UserServiceImplem extends BaseController implements UserService {

    @Autowired
    UserDAOImplem dao;


    public static final String LOGGED = "logged";  // a constant for the session

    @Override
    public void registerUser(User user, HttpSession session) throws InvalidUsernameOrEmailException {
        if(!validateEmail(user.getEmail())){
            throw new InvalidUsernameOrEmailException("This is not a valid email.");
        }
        if(user.getUsername().length()<3){
            throw new InvalidUsernameOrEmailException("This is not a valid username.");
        }
        dao.registerUser(user);
        session.setAttribute(LOGGED, user);
        new Thread(new EmailSender(user.getEmail(),user.getUsername())).start();
    }

    // For validating the email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }
}
