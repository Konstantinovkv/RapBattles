package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.ServiceImpl.PostLikeServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.ForbiddenException;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/post_likes")
public class PostLikeController extends BaseController{

    @Autowired
    PostLikeServiceImplem pls;

    @GetMapping("/like/{post_id}")
    public String likePost(@PathVariable(value ="post_id") int post_ID, HttpSession session) throws ForbiddenException, MainException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        pls.likePost(post_ID,userDTO.getUser_ID());
        return "You have successfully liked the post.";
    }

    @GetMapping("/dislike/{post_id}")
    public String dislikePost(@PathVariable(value ="post_id") int post_ID, HttpSession session) throws ForbiddenException, MainException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        pls.dislikePost(post_ID,userDTO.getUser_ID());
        return "You have successfully disliked the post.";
    }
}
