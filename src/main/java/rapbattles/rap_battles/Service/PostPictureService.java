package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Util.Exceptions.MainException;

import java.io.IOException;

public interface PostPictureService {

    int uploadPostImage(String fileStr) throws IOException, MainException;

    byte[] downloadImage(String imageName)throws IOException;
}
