package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import rapbattles.rap_battles.DAOImplementation.ActivationCodeDAOImplem;
import rapbattles.rap_battles.DAOImplementation.UserDAOImplem;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Service.UserService;
import rapbattles.rap_battles.Util.EmailSender;
import rapbattles.rap_battles.Util.Exceptions.*;
import rapbattles.rap_battles.Util.PasswordUtils;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImplem implements UserService {

    @Autowired
    UserDAOImplem dao;

    @Autowired
    ActivationCodeDAOImplem activationDao;


    public static final String LOGGED = "logged";  // a constant for the session

    //Pretty much register user as a whole and send email confirmation
    @Override
    public UserDTO addUser(User user, HttpSession session) throws MainException {
        validateUsernameAndEmail(user);
        checkIfUsernameOrEmailExists(user);
        validateAndGenerateSecurePassword(user);
        dao.registerUser(user);
        session.setAttribute(LOGGED, user);
        return emailAndConfirmation(user);
    }

    //Service for logging in.
    @Override
    public UserDTO login(@RequestBody User user, HttpSession session) throws MainException {
        checkIfAccountIsActivated(user);
        checkUsernameOrEmail(user);
        return checkPassword(user, session);
    }

    @Override
    public int removeUser(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute(LOGGED);
        int id = user.getUser_ID();
        dao.deleteUserByID(user.getUser_ID());
        return id;
    }

    //Checks if the correct password has been entered.
    private UserDTO checkPassword(User user, HttpSession session) throws MainException {
        boolean passwordMatch = false;
        if (dao.findUserByUsernameDTO(user.getUsername()) == null){
            passwordMatch = PasswordUtils.verifyUserPassword(user.getPassword(), dao.findUserByEmail(user.getEmail()).getPassword(),
                    dao.findUserByEmail(user.getEmail()).getSalt());
        }
        if (dao.findUserByEmailDTO(user.getEmail())==null){{
            passwordMatch = PasswordUtils.verifyUserPassword(user.getPassword(), dao.findUserByUsername(user.getUsername()).getPassword(),
                    dao.findUserByUsername(user.getUsername()).getSalt());
        }}
        if (passwordMatch) {
            if(dao.findUserByUsernameDTO(user.getUsername()) == null) {
                session.setAttribute(LOGGED, dao.findUserByEmailDTO(user.getEmail()));
                session.setMaxInactiveInterval(-1);
                return dao.findUserByEmailDTO(user.getEmail());
            }
            if(dao.findUserByEmailDTO(user.getEmail()) == null) {
                session.setAttribute(LOGGED, dao.findUserByUsernameDTO(user.getUsername()));
                session.setMaxInactiveInterval(-1);
                return dao.findUserByUsernameDTO(user.getUsername());
            }
        }
        else {
            throw new WrongEmailOrPasswordException("Wrong username,email or password.");
        }
        return null;
    }

    //Checks if the login info (username or email) are correct or existing.
    private void checkUsernameOrEmail(User user) throws MainException {
        if (dao.findUserByEmailDTO(user.getEmail())==null){
            if(dao.findUserByUsernameDTO(user.getUsername()) == null){
                throw new WrongEmailOrPasswordException("Wrong username, email or password.");
            }
        }

        if (dao.findUserByUsernameDTO(user.getUsername()) == null){
            if(dao.findUserByEmailDTO(user.getEmail()) == null){
                throw new WrongEmailOrPasswordException("Wrong username, email or password.");
            }
        }
    }

    //Checks if he account has been verified by email.
    private void checkIfAccountIsActivated(User user) throws MainException {
        checkIfUserExists(user);
        if (dao.findUserByEmailDTO(user.getEmail())==null){
            if(!dao.findUserByUsernameDTO(user.getUsername()).isActivated()){
                throw new AccountNotActivatedException("Your account is not activated.");
            }
        }
        if (dao.findUserByUsernameDTO(user.getUsername())==null){
            if(!dao.findUserByEmailDTO(user.getEmail()).isActivated()){
                throw new AccountNotActivatedException("Your account is not activated.");
            }
        }
    }

    //Checks if the 2 passwords match and validates if password matches requirements.
    private void validateAndGenerateSecurePassword(User user) throws MainException {
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

    //Checks if the user exists.
    private void checkIfUserExists(User user) throws MainException {
        if (dao.findUserByUsername(user.getUsername()) == null&&dao.findUserByEmail(user.getEmail()) == null) {
            throw new InvalidUsernameOrEmailException("This user does not exist.");
        }
    }

    //Checks if the provided username and email are valid.
    private void validateUsernameAndEmail(User user) throws MainException{
        if(!validateEmail(user.getEmail())){
            throw new InvalidUsernameOrEmailException("This is not a valid email.");
        }
        if(user.getUsername().length()<3||user.getUsername().length()>20){
            throw new InvalidUsernameOrEmailException("This is not a valid username.");
        }
    }

    //Checks if username or email have already been used before to register an account.
    private void checkIfUsernameOrEmailExists(User user) throws MainException{
        if (dao.findUserByUsernameDTO(user.getUsername()) != null) {
            throw new InvalidUsernameOrEmailException("This username is already taken.");
        }
        if (dao.findUserByEmailDTO(user.getEmail()) != null) {
            throw new InvalidUsernameOrEmailException("You have already registered with this email.");
        }
    }

    // Generates secure password with salt.
    private void generateSecurePassword(User user){
        String salt = PasswordUtils.getSalt(30);
        String securedPassword = PasswordUtils.generateSecurePassword(user.getPassword(), salt);
        user.setPassword(securedPassword);
        user.setSecond_password(securedPassword);
        user.setSalt(salt);
    }

    // Validates if the password matches the requirements.
    private boolean validatePassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=*()_<>])(?=\\S+$).{8,}$";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(password);
        return matcher.matches();
    }

    // For validating the email.
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    // Generates a random activation code for the email link when registering.
    private String getRandomString() {
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

    //Generates random code and sends an email that needs to be confirmed to activate the account.
    private UserDTO emailAndConfirmation(User user){
        String activation_code = getRandomString();
        new Thread(new EmailSender(user.getEmail(),user.getUsername(),activation_code)).start();
        activationDao.uploadActivationCode(dao.findUserByUsernameDTO(user.getUsername()).getUser_ID(),activation_code);
        return dao.findUserByEmailDTO(user.getEmail());
    }

    //activates account.
    public void activateAccountService(String activation_code) throws MainException {
        if (activationDao.findUserIdByActivationCode(activation_code)==null){
            throw new WrongActivationCodeException("The activation code is wrong.");
        }
        UserDTO userDTO = dao.findUserByID(activationDao.findUserIdByActivationCode(activation_code).getUser_ID());
        dao.setActiveToTrue(userDTO);
    }
}
