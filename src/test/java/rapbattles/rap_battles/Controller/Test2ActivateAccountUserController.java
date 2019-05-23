package rapbattles.rap_battles.Controller;

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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("Testing the activate account UserController method")
public class Test2ActivateAccountUserController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    UserDAOImplem userDAO;

    @Autowired
    ActivationCodeDAO activationCodeDAO;

    private static int userID;
    private static File file;

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
    }

    @Test
    @DisplayName("Testing activateAccount")
    public void activateAccountTest() throws Exception {
        String activationCode = activationCodeDAO.findActivationCodeByUserID(userID);
        MvcResult result = mockMvc.perform(get("/user/activate/"+activationCode).content(activationCode)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        assertEquals(resultContent,"<h1>Your account has been activated.</h1>");
    }
}
