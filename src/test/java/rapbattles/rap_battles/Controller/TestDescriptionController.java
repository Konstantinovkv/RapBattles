package rapbattles.rap_battles.Controller;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Description;
import rapbattles.rap_battles.Util.Exceptions.NotLoggedException;
import static org.junit.jupiter.api.Assertions.*;
import static rapbattles.rap_battles.ServiceImpl.UserServiceImplem.LOGGED;

import org.junit.jupiter.api.*;

import javax.servlet.http.HttpSession;

public class TestDescriptionController {

    DescriptionController dc;

    @Mock
    private HttpSession session;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        dc = new DescriptionController();
        UserDTO userDTO = new UserDTO(1,"KosBos","konstantinovkv@gmail.com",true);
        Mockito.doReturn(userDTO).when(session).getAttribute(LOGGED);
    }

    @Test
    @DisplayName("Testing the update user description function")
    void testUpdateUserDescription() throws NotLoggedException {
        Description desc = new Description();
        desc.setUser_ID(1);
        desc.setUser_description("Rap God one and only!");
        assertEquals (desc,dc.updateUserDescription(desc,session ));
    }
}
