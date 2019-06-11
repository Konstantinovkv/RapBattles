package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.DTO.PostDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Util.Exceptions.ForbiddenException;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import rapbattles.rap_battles.Util.Exceptions.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface PostService {

    PostDTO getPostById(int post_ID) throws MainException;
    List<PostDTO> getAllPostsByUserID(int user_ID);
    void createPost(PostDTO postDTO, UserDTO userDTO) throws IOException, MainException;
    void deletePost(int post_ID, String username) throws MainException, ForbiddenException;
    void updatePost(int post_ID, int user_ID, String content) throws NotFoundException, ForbiddenException;
}
