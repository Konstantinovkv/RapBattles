package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.DTO.PostDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Util.Exceptions.MainException;

import java.io.IOException;
import java.util.List;

public interface PostService {

    PostDTO getPostById(int post_ID) throws MainException;
    List<PostDTO> getAllPostsByUserID(int user_ID);
    void createPost(PostDTO postDTO, UserDTO userDTO) throws IOException, MainException;
}
