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
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("Testing the UserController methods")
public class TestUserController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("Testing registerUser; ID must be increased each time")
    public void registerUserTest() throws Exception {
        User user = new User("TestAccount","testAccount@gmail.com","aA@123456","aA@123456");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/user/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UserDTO response = om.readValue(resultContent, UserDTO.class);
        assertAll(
                () -> assertEquals(response.getUser_ID(),9),
                () -> assertEquals(response.getUsername(),"TestAccount"),
                () -> assertEquals(response.getEmail(),"testAccount@gmail.com"),
                () -> assertEquals(response.isActivated(),false)
        );
    }
}
