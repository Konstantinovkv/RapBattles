package rapbattles.rap_battles.DAO;

import rapbattles.rap_battles.Models.POJO.Comment;

import java.util.List;

public interface CommentDAO {

    void writeComment(int user_ID, String content, int post_ID);

    Comment getCommentByID(int comment_ID);

    List<Comment> getAllCommentsForPost(int post_ID);

    List<Comment> getAllCommentsByUser(int user_ID);
}
