package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapbattles.rap_battles.Models.DTO.PostDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.ServiceImpl.PostServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import rapbattles.rap_battles.Util.Exceptions.NotLoggedException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController extends BaseController {

    @Autowired
    PostServiceImplem psi;

    public static final String LOGGED = "logged";

    @GetMapping("/post_id/{post_ID}")
    public PostDTO getPostById(@PathVariable(value = "post_ID") int post_ID) throws MainException {
        return psi.getPostById(post_ID);
    }

    @GetMapping("/user_id/{user_ID}")
    public List<PostDTO> getAllPostsByUserID(@PathVariable(value = "user_ID") int user_ID){
        return psi.getAllPostsByUserID(user_ID);
    }

    @PostMapping("/create_post")
    public void createPost(@RequestBody PostDTO postDTO, HttpSession session) throws IOException, MainException, NotLoggedException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        psi.createPost(postDTO,userDTO);
    }
}
