package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAO.UserAvatarDAOImplem;
import rapbattles.rap_battles.Models.DTO.ImageUploadDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.UserAvatar;
import rapbattles.rap_battles.Service.UserAvatarService;
import rapbattles.rap_battles.Util.Exceptions.InvalidURLException;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class UserAvatarServiceImplem implements UserAvatarService {

    public static final String IMAGE_URL = "C:\\Users\\Konstantin\\Desktop\\ProjectImages\\Avatars\\";

    @Autowired
    UserAvatarDAOImplem uaDAO;

    public void uploadAvatarImage(ImageUploadDTO dto, UserDTO user) throws IOException, MainException {
        String base64 = dto.getFileStr();
        if (base64.equals("")||base64==null){
            throw new InvalidURLException("URL is not valid or empty!");
        }
        byte[] bytes = Base64.getDecoder().decode(base64);
        String name = user.getUser_ID()+"_"+System.currentTimeMillis()+".png";
        File newImage = new File(IMAGE_URL +name);
        FileOutputStream fos = new FileOutputStream(newImage);
        fos.write(bytes);
        UserAvatar userAvatar = new UserAvatar(user.getUser_ID(),name);
        uaDAO.uploadImage(userAvatar);
    }

    public byte[] downloadImage(String imageName)throws IOException{
        File newImage = new File(IMAGE_URL +imageName+".png");
        FileInputStream fis = new FileInputStream(newImage);
        return fis.readAllBytes();
    }
}
