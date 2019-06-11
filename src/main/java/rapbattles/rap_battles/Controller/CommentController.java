package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Comment;
import rapbattles.rap_battles.ServiceImpl.CommentServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.ForbiddenException;
import rapbattles.rap_battles.Util.Exceptions.MainException;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController extends BaseController{

    @Autowired
    CommentServiceImplem csi;

    public static final String LOGGED = "logged";

    @PostMapping("/comment_post/{post_id}")
    public String writeComment(@PathVariable(value="post_id") int post_ID, @RequestBody Comment comment, HttpSession session) throws ForbiddenException, MainException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        csi.writeComment(userDTO.getUser_ID(),comment.getContent(),post_ID);
        return "Comment successfully writen.";
    }

    @GetMapping("/get/{comment_id}")
    public Comment getCommentByID(@PathVariable(value="comment_id") int comment_ID) throws MainException {
        return csi.getCommentByID(comment_ID);
    }

    @GetMapping("/get_for_post/{post_id}")
    public List<Comment> getAllCommentsForPost(@PathVariable(value="post_id") int post_ID) throws MainException {
        return csi.getAllCommentsForPost(post_ID);
    }

    @GetMapping("/get_for_user/{user_id}")
    public List<Comment> getAllCommentsByUser(@PathVariable(value="user_id") int user_ID) throws MainException {
        return csi.getAllCommentsByUser(user_ID);
    }
}
