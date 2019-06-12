package rapbattles.rap_battles.ServiceImpl;

import rapbattles.rap_battles.Models.DTO.UploadDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Sound;
import rapbattles.rap_battles.Service.SoundService;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import java.io.IOException;

public class SoundServiceImplem extends UploadService implements SoundService {

    public static final String SOUND_URL = "C:\\Users\\Konstantin\\Desktop\\ProjectImages\\Sounds\\";

    public void uploadSounds(UploadDTO dto, UserDTO user) throws IOException, MainException {
        Sound sound = new Sound(uploadFile(dto.getFileStr(),user,SOUND_URL,".mp3"), user.getUser_ID());
    }
}
