package rapbattles.rap_battles.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rapbattles.rap_battles.DAO.UserDAOImplem;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Util.PasswordUtils;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing the UserController methods") //Order of the tests doesn't matter, I have it there so I can delete the account after the tests.
public class TestUserController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    UserDAOImplem userDAO;

    @Mock
    private HttpSession session;

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
        user = new User(userID,"TestAccount","testAccount@gmail.com","aA@123456","aA@123456",salt,false);
    }

    @Test
    @Order(1)
    @DisplayName("Testing registerUser; if ID doesn't match check ID in DB")
    public void registerUserTest() throws Exception {
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UserDTO response = om.readValue(resultContent, UserDTO.class);
        assertAll(
                () -> assertEquals(response.getUser_ID(),userID),
                () -> assertEquals(response.getUsername(),"TestAccount"),
                () -> assertEquals(response.getEmail(),"testAccount@gmail.com"),
                () -> assertEquals(response.isActivated(),false)
        );
        UserDTO userDTO = new UserDTO(userID,"TestAccount","testAccount@gmail.com",false);
        userDAO.setActiveToTrue(userDTO);
    }

    @Nested
    @DisplayName("Testing loginUser ")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestLoginUser{
        @Test
        @Order(2)
        @DisplayName("Logging in with right username")
        void testLoginUsername() throws Exception {
            user.setEmail(null);
            String jsonRequest = om.writeValueAsString(user);
            MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
            String resultContent = result.getResponse().getContentAsString();
            UserDTO response = om.readValue(resultContent, UserDTO.class);
            assertAll(
                    () -> assertEquals(response.getUser_ID(),userID),
                    () -> assertEquals(response.getUsername(),"TestAccount"),
                    () -> assertEquals(response.getEmail(),"testAccount@gmail.com"),
                    () -> assertEquals(response.isActivated(),true)
            );
        }

        @Test
        @Order(3)
        @DisplayName("Logging in with wrong username") //should not return 200 or 500
        void testLoginWrongUsername() throws Exception {
            String jsonRequest = om.writeValueAsString(user);
            MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
            MockHttpServletResponse response = result.getResponse();
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        }

        @Test
        @Order(4)
        @DisplayName("Logging in with empty username") //should not return 200 or 500
        void testLoginEmptyUsername() throws Exception {
            String jsonRequest = om.writeValueAsString(user);
            MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
            MockHttpServletResponse response = result.getResponse();
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        }

        @Test
        @Order(5)
        @DisplayName("Logging in with right email")
        void testLoginEmail() throws Exception {
            user.setUsername(null);
            String jsonRequest = om.writeValueAsString(user);
            MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
            String resultContent = result.getResponse().getContentAsString();
            UserDTO response = om.readValue(resultContent, UserDTO.class);
            assertAll(
                    () -> assertEquals(response.getUser_ID(),userID),
                    () -> assertEquals(response.getUsername(),"TestAccount"),
                    () -> assertEquals(response.getEmail(),"testAccount@gmail.com"),
                    () -> assertEquals(response.isActivated(),true)
            );
        }

        @Test
        @Order(6)
        @DisplayName("Logging in with wrong email") //should not return 200 or 500
        void testLoginWrongEmail() throws Exception {
            String jsonRequest = om.writeValueAsString(user);
            MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
            MockHttpServletResponse response = result.getResponse();
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        }

        @Test
        @Order(7)
        @DisplayName("Logging in with empty email") //should not return 200 or 500
        void testLoginEmptyEmail() throws Exception {
            String jsonRequest = om.writeValueAsString(user);
            MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
            MockHttpServletResponse response = result.getResponse();
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        }

        @Test
        @Order(8) //>>>Also deletes the account<<<
        @DisplayName("Logging in with wrong password") //should not return 200 or 500
        void testLoginWrongPassword() throws Exception {
            String jsonRequest = om.writeValueAsString(user);
            MvcResult result = mockMvc.perform(post("/user/login").content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
            MockHttpServletResponse response = result.getResponse();
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
            userDAO.deleteUserByID(userID);
            try(PrintStream ps = new PrintStream(file)){
                ps.print(userID+1);
            } catch (IOException e){
                e.getMessage();
            }
        }
    }

//    @AfterAll
//    static void testDeleteUser(){
//
//    }
}
