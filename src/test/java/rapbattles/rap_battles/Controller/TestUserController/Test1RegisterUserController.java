package rapbattles.rap_battles.Controller.TestUserController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rapbattles.rap_battles.DAO.ActivationCodeDAO;
import rapbattles.rap_battles.DAOImplementation.UserDAOImplem;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Util.PasswordUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("Testing the register UserController method")
public class Test1RegisterUserController {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    UserDAOImplem userDAO;

    @Autowired
    ActivationCodeDAO activationCodeDAO;

    ObjectMapper om = new ObjectMapper();

    private static int userID;
    private static File file;
    private User user;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        file = new File("UserID");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        userID = sc.nextInt();
        String salt = PasswordUtils.getSalt(30);
        user = new User(userID, "TestAccount", "testAccount@gmail.com", "aA@123456", "aA@123456", salt, false);
    }

    @Test
    @DisplayName("Registering with too short username")//should not return 200 or 500
    void testRegisterTooShortUsername() throws Exception {
        user.setEmail("randomEmail@gmail.com");
        user.setUsername("Ko");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with too long username")//should not return 200 or 500
    void testRegisterWrongUsername() throws Exception {
        user.setEmail("randomEmail@gmail.com");
        user.setUsername("KonstantinVladimirovKonstantinov12345");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with taken username")//should not return 200 or 500
    void testRegisterTakenUsername() throws Exception {
        user.setEmail("randomEmail@gmail.com");
        user.setUsername("KosBos");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with invalid email")//should not return 200 or 500
    void testRegisterInvalidEmail() throws Exception {
        user.setUsername("RandomUsername");
        user.setEmail("konstantinovkvgmail.com");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with invalid email2")//should not return 200 or 500
    void testRegisterInvalidEmail2() throws Exception {
        user.setUsername("RandomUsername");
        user.setEmail("konstantinovkv@");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with invalid email3")//should not return 200 or 500
    void testRegisterInvalidEmail3() throws Exception {
        user.setUsername("RandomUsername");
        user.setEmail("@gmail");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with invalid email3")//should not return 200 or 500
    void testRegisterInvalidEmail4() throws Exception {
        user.setUsername("RandomUsername");
        user.setEmail("randomEmail@gmail");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with taken email")//should not return 200 or 500
    void testRegisterTakenEmail() throws Exception {
        user.setUsername("RandomUsername");
        user.setEmail("konstantinovkv@gmail.com");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with invalid password too short")//should not return 200 or 500
    void testRegisterInvalidPassword1() throws Exception {
        user.setUsername("RandomUsername");
        user.setEmail("randomEmail@gmail.com");
        user.setPassword("Sh!8");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with invalid password no special Symbol")//should not return 200 or 500
    void testRegisterInvalidPassword2() throws Exception {
        user.setUsername("RandomUsername");
        user.setEmail("randomEmail@gmail.com");
        user.setPassword("123Password");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with invalid password with no upper case")//should not return 200 or 500
    void testRegisterInvalidPassword3() throws Exception {
        user.setUsername("RandomUsername");
        user.setEmail("randomEmail@gmail.com");
        user.setPassword("123password!");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with invalid password with no lowerCase")//should not return 200 or 500
    void testRegisterInvalidPassword4() throws Exception {
        user.setUsername("RandomUsername");
        user.setEmail("randomEmail@gmail.com");
        user.setPassword("123PASSWORD!");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Registering with invalid password with no numbers")//should not return 200 or 500
    void testRegisterInvalidPassword5() throws Exception {
        user.setUsername("RandomUsername");
        user.setEmail("randomEmail@gmail.com");
        user.setPassword("asdPASSWORD!");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Testing registerUser; if ID doesn't match check ID in DB")
    public void registerUserTest() throws Exception {
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UserDTO response = om.readValue(resultContent, UserDTO.class);
        assertAll(
                () -> assertEquals(response.getUser_ID(), userID),
                () -> assertEquals(response.getUsername(), "TestAccount"),
                () -> assertEquals(response.getEmail(), "testAccount@gmail.com"),
                () -> assertEquals(response.isActivated(), false)
        );
    }
}
