package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAO.UserAvatarDAOImplem;
import rapbattles.rap_battles.Models.DTO.ImageUploadDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.UserAvatar;
import rapbattles.rap_battles.Service.UserAvatarService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class UserAvatarServiceImplem implements UserAvatarService {

    @Autowired
    UserAvatarDAOImplem uaDAO;

    public void uploadAvatarImage(ImageUploadDTO dto, UserDTO user) throws IOException {
        String base64 = dto.getFileStr();
        byte[] bytes = Base64.getDecoder().decode(base64);
        String name = user.getUser_ID()+System.currentTimeMillis()+".png";
        File newAvatar = new File("C:\\Users\\Konstantin\\Desktop\\ProjectImages\\Avatars\\"+name);
        FileOutputStream fos = new FileOutputStream(newAvatar);
        fos.write(bytes);
        UserAvatar userAvatar = new UserAvatar(user.getUser_ID(),name);
        uaDAO.uploadImage(userAvatar);
    }
}
