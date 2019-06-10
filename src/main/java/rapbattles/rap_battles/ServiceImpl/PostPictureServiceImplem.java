package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.PostPictureDAOImplementation;
import rapbattles.rap_battles.Models.POJO.PostPicture;
import rapbattles.rap_battles.Service.PostPictureService;
import rapbattles.rap_battles.Util.Exceptions.InvalidURLException;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

@Service
public class PostPictureServiceImplem implements PostPictureService {

    @Autowired
    PostPictureDAOImplementation ppDAO;

    Random random = new Random();

    public static final String IMAGE_URL = "C:\\Users\\Konstantin\\Desktop\\ProjectImages\\Posts\\";

    public int uploadPostImage(String fileStr) throws IOException, MainException {
        if (fileStr.equals("") || fileStr == null) {
            throw new InvalidURLException("URL is not valid or empty!");
        }
        byte[] bytes = Base64.getDecoder().decode(fileStr);
        String name = random.nextInt(100000) +""+ System.currentTimeMillis() + ".png";
        File newImage = new File(IMAGE_URL + name);
        FileOutputStream fos = new FileOutputStream(newImage);
        fos.write(bytes);
        PostPicture postPicture = new PostPicture(name);
        return ppDAO.uploadPostPicture(name);
    }
}
