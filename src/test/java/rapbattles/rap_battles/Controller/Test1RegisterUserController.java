package rapbattles.rap_battles.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import java.util.Scanner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        user = new User(userID,"TestAccount","testAccount@gmail.com","aA@123456","aA@123456",salt,false);
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
                () -> assertEquals(response.getUser_ID(),userID),
                () -> assertEquals(response.getUsername(),"TestAccount"),
                () -> assertEquals(response.getEmail(),"testAccount@gmail.com"),
                () -> assertEquals(response.isActivated(),false)
        );
    }
}
