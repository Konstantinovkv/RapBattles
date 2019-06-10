package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.DTO.ImageUploadDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import java.io.IOException;

public interface UserAvatarService {

    void uploadAvatarImage(ImageUploadDTO dto, UserDTO user)throws IOException, MainException;

    byte[] downloadImage(String imageName)throws IOException;
}