package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.PostDAOImplem;
import rapbattles.rap_battles.Models.DTO.PostDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import rapbattles.rap_battles.Util.Exceptions.NotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class PostServiceImplem {

    @Autowired
    PostDAOImplem postDAO;

    public PostDTO getPostById(int post_ID) throws MainException {
        PostDTO postDTO = postDAO.getPostByID(post_ID);
        if (postDTO == null) {
            throw new NotFoundException("No such post exists.");
        }
        return postDTO;
    }

    public List<PostDTO> getAllPostsByUserID(int user_ID){
        return postDAO.getAllPostsByUserID(user_ID);
    }

    public void createPost(PostDTO postDTO, UserDTO userDTO) throws IOException, MainException {
        postDAO.createPost(postDTO, userDTO.getUser_ID());
    }
}
