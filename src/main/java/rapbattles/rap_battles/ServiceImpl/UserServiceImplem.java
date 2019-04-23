package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.Controller.BaseController;
import rapbattles.rap_battles.Models.DAO.ActivationCodeDAOImplem;
import rapbattles.rap_battles.Models.DAO.UserDAOImplem;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Service.UserService;
import rapbattles.rap_battles.Util.EmailSender;
import rapbattles.rap_battles.Util.Exceptions.InvalidUsernameOrEmailException;
import rapbattles.rap_battles.Util.Exceptions.InvalidPasswordException;
import rapbattles.rap_battles.Util.PasswordUtils;

import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class UserServiceImplem extends BaseController implements UserService {

    @Autowired
    UserDAOImplem dao;

    @Autowired
    ActivationCodeDAOImplem activationDao;


    public static final String LOGGED = "logged";  // a constant for the session

    //Pretty much register user as a whole and send email confirmation
    @Override
    public UserDTO addUser(User user, HttpSession session) throws InvalidUsernameOrEmailException, InvalidPasswordException {
        validateUsernameAndEmail(user);
        checkIfUsernameOrEmailExists(user);
        validateAndGenerateSecurePassword(user);
        dao.registerUser(user);
        session.setAttribute(LOGGED, user);
        return emailAndConfirmation(user);
    }

    //Checks if the 2 passwords match and validates if password matches requirements
    public void validateAndGenerateSecurePassword(User user) throws InvalidPasswordException {
        if(!user.getPassword().equals(user.getSecond_password())){
            throw new InvalidPasswordException("Passwords don't match. Please check your spelling.");
        }
        if (validatePassword(user.getPassword())) {
            generateSecurePassword(user);
        }
        else{
            throw new InvalidPasswordException("Password must be 8 characters or more, have at least one upper and lower case character" +
                    " and have at least 1 special character and digit and no spaces.");
        }
    }

    //Checks if the provided username and email are valid
    public void validateUsernameAndEmail(User user) throws InvalidUsernameOrEmailException{
        if(!validateEmail(user.getEmail())){
            throw new InvalidUsernameOrEmailException("This is not a valid email.");
        }
        if(user.getUsername().length()<3){
            throw new InvalidUsernameOrEmailException("This is not a valid username.");
        }
    }

    //Checks if username or email have already been used before to register an account
    public void checkIfUsernameOrEmailExists(User user) throws InvalidUsernameOrEmailException{
        if (dao.findUserByUsername(user.getUsername()) != null) {
            throw new InvalidUsernameOrEmailException("This username is already taken.");
        }
        if (dao.findUserByEmail(user.getEmail()) != null) {
            throw new InvalidUsernameOrEmailException("You have already registered with this email.");
        }
    }

    // Generates secure password with salt
    public void generateSecurePassword(User user){
        String salt = PasswordUtils.getSalt(30);
        String securedPassword = PasswordUtils.generateSecurePassword(user.getPassword(), salt);
        user.setPassword(securedPassword);
        user.setSecond_password(securedPassword);
        user.setSalt(salt);
    }

    // Validates if the password matches the requirements
    protected boolean validatePassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=*()_<>])(?=\\S+$).{8,}$";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(password);
        return matcher.matches();
    }

    // For validating the email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    // Generates a random activation code for the email link when registering
    protected String getRandomString() {
        String choices = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder randomString = new StringBuilder();
        Random rnd = new Random();
        while (randomString.length() < 18) {
            int index = (int) (rnd.nextFloat() * choices.length());
            randomString.append(choices.charAt(index));
        }
        String randomStr = randomString.toString();
        return randomStr;
    }

    //Generates random code and sends an email that needs to be confirmed to activate the account
    public UserDTO emailAndConfirmation(User user){
        String activation_code = getRandomString();
        new Thread(new EmailSender(user.getEmail(),user.getUsername(),activation_code)).start();
        activationDao.uploadActivationCode(dao.findUserByUsername(user.getUsername()).getUser_ID(),activation_code);
        return dao.findUserByEmail(user.getEmail());
    }

    //activates account
    public void activateAccountService(String activation_code){
        UserDTO userDTO = dao.findUserByID(activationDao.findUserIdByActivationCode(activation_code).getUser_ID());
        dao.setActiveToTrue(userDTO);
    }
}
