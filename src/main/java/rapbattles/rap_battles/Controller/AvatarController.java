package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapbattles.rap_battles.Models.DTO.ImageUploadDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.ServiceImpl.UserAvatarServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.NotLoggedException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class AvatarController extends BaseController {

    public static final String LOGGED = "logged";

    @Autowired
    UserAvatarServiceImplem uasImplem;

    @PostMapping("/avatar")
    public void uploadAvatar(@RequestBody ImageUploadDTO dto, HttpSession session) throws NotLoggedException, IOException {
        validateLogged(session);
        UserDTO user = (UserDTO) session.getAttribute(LOGGED);
        uasImplem.uploadAvatarImage(dto,user);

    }

    @GetMapping("/avatar/{name}")
    public void downloadAvatar(@PathVariable("name") String avatarName){

    }

}
