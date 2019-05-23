package rapbattles.rap_battles.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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
import rapbattles.rap_battles.DAO.UserDAOImplem;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Util.PasswordUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //need it so I can delete the user in the end
@DisplayName("Testing the UserController methods")    //since userDAO doesn't work from static methods like @AfterAll and AfterClass
public class Test3LoginUserController {

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
    @Order(1)
    @DisplayName("Logging in with right username")
    void testLoginUsername() throws Exception {
        user.setEmail(null);
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UserDTO response = om.readValue(resultContent, UserDTO.class);
        assertAll(
                () -> assertEquals(response.getUser_ID(), userID),
                () -> assertEquals(response.getUsername(), "TestAccount"),
                () -> assertEquals(response.getEmail(), "testAccount@gmail.com"),
                () -> assertEquals(response.isActivated(), true)
        );
    }

    @Test
    @Order(2)
    @DisplayName("Logging in with wrong username")
        //should not return 200 or 500
    void testLoginWrongUsername() throws Exception {
        user.setEmail(null);
        user.setUsername("WrongUsername");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @Order(3)
    @DisplayName("Logging in with empty username")
        //should not return 200 or 500
    void testLoginEmptyUsername() throws Exception {
        user.setEmail(null);
        user.setUsername("");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @Order(4)
    @DisplayName("Logging in with right email")
    void testLoginEmail() throws Exception {
        user.setUsername(null);
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UserDTO response = om.readValue(resultContent, UserDTO.class);
        assertAll(
                () -> assertEquals(response.getUser_ID(), userID),
                () -> assertEquals(response.getUsername(), "TestAccount"),
                () -> assertEquals(response.getEmail(), "testAccount@gmail.com"),
                () -> assertEquals(response.isActivated(), true)
        );
    }

    @Test
    @Order(5)
    @DisplayName("Logging in with wrong email")
        //should not return 200 or 500
    void testLoginWrongEmail() throws Exception {
        user.setUsername(null);
        user.setEmail("wrongEmail@gmail.com");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @Order(6)
    @DisplayName("Logging in with empty email")
        //should not return 200 or 500
    void testLoginEmptyEmail() throws Exception {
        user.setUsername(null);
        user.setEmail("");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @Order(7)
    @DisplayName("Logging in with wrong password")
        //should not return 200 or 500
    void testLoginWrongPassword() throws Exception {
        user.setPassword("WrongPassword123!");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        userDAO.deleteUserByID(userID);
        try (PrintStream ps = new PrintStream(file)) {
            ps.print(userID + 1);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
