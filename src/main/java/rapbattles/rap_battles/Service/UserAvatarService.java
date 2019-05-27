package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.DTO.ImageUploadDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;

import java.io.IOException;

public interface UserAvatarService {

    public void uploadAvatarImage(ImageUploadDTO dto, UserDTO user)throws IOException;
}
