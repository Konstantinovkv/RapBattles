package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.CommentDAOImplem;
import rapbattles.rap_battles.DAOImplementation.PostDAOImplem;
import rapbattles.rap_battles.Models.POJO.Comment;
import rapbattles.rap_battles.Service.CommentService;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import rapbattles.rap_battles.Util.Exceptions.NotFoundException;

import java.util.List;

@Service
public class CommentServiceImplem implements CommentService {

    @Autowired
    CommentDAOImplem commentDAO;

    @Autowired
    PostDAOImplem postDAO;

    public void writeComment(int user_ID, String content, int post_ID) throws MainException {
        if (postDAO.getPostByID(post_ID) == null){
            throw new NotFoundException("There is no post with this ID.");
        }
        commentDAO.writeComment(user_ID,content,post_ID);
    }

    public Comment getCommentByID(int comment_ID) throws NotFoundException {
        Comment comment = commentDAO.getCommentByID(comment_ID);
        if (comment==null){
            throw new NotFoundException("There is no comment with this ID");
        }
        return comment;
    }

    public List<Comment> getAllCommentsForPost(int post_ID) throws MainException {
        if (commentDAO.getAllCommentsForPost(post_ID)==null){
            throw new NotFoundException("No comments found for this post.");
        }
        return commentDAO.getAllCommentsForPost(post_ID);
    }

    public List<Comment> getAllCommentsByUser(int user_ID) throws MainException{
        if (commentDAO.getAllCommentsByUser(user_ID)==null){
            throw new NotFoundException("No comments found for this user.");
        }
        return commentDAO.getAllCommentsByUser(user_ID);
    }
}
