package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.POJO.Comment;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import rapbattles.rap_battles.Util.Exceptions.NotFoundException;

import java.util.List;

public interface CommentService {

    void writeComment(int user_ID, String content, int post_ID) throws MainException;

    Comment getCommentByID(int comment_ID) throws NotFoundException;

    List<Comment> getAllCommentsForPost(int post_ID) throws MainException;

    List<Comment> getAllCommentsByUser(int user_ID) throws MainException;

}
