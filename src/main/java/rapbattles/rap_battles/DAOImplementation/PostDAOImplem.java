package rapbattles.rap_battles.DAOImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.DAO.PostDAO;
import rapbattles.rap_battles.Models.DTO.PostDTO;
import rapbattles.rap_battles.Models.POJO.Post;
import rapbattles.rap_battles.ServiceImpl.PostPictureServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class PostDAOImplem implements PostDAO {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    TextDAOImplem textDAO;

    @Autowired
    PostPictureServiceImplem ppsi;

    public PostDTO getPostDTOByID(int post_ID) {
        try {
            String sql = "SELECT post_ID, username, title, content, `path`, date_time_created\n" +
                    "FROM posts\n" +
                    "JOIN users\n" +
                    "ON(users.user_ID = posts.user_ID)\n" +
                    "JOIN texts\n" +
                    "ON (texts.text_ID = posts.text_ID)\n" +
                    "JOIN post_pictures\n" +
                    "ON(post_pictures.picture_ID = posts.picture_ID)\n" +
                    "WHERE post_ID = ?";
            return (PostDTO) jdbc.queryForObject(sql, new Object[]{post_ID}, new PostDTOMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Post getPostByID(int post_ID) {
        try {
            String sql = "SELECT post_ID, user_ID, title, text_ID, picture_ID, date_time_created FROM posts WHERE post_ID = ?";
            return (Post) jdbc.queryForObject(sql, new Object[]{post_ID}, new PostMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<PostDTO> getAllPostsByUserID(int user_ID) {
        String sql = "SELECT post_ID, username, title, content, `path`, date_time_created\n" +
                "FROM posts\n" +
                "JOIN users\n" +
                "ON(users.user_ID = posts.user_ID)\n" +
                "JOIN texts\n" +
                "ON (texts.text_ID = posts.text_ID)\n" +
                "JOIN post_pictures\n" +
                "ON(post_pictures.picture_ID = posts.picture_ID)\n" +
                "WHERE users.user_ID = ?";
        return jdbc.query(sql, new Object[]{user_ID}, (resultSet, i) -> mapRowPostDTO(resultSet));
    }

    public void createPost(PostDTO postDTO, int user_ID) throws IOException, MainException {
        java.util.Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        int text_ID = textDAO.writeText(postDTO.getContent());
        int picture_ID = ppsi.uploadPostImage(postDTO.getFileStr());
        String sql = "INSERT INTO posts (user_ID, title,date_time_created, text_ID, picture_ID) VALUES (?,?,?,?,?)";
        jdbc.update(sql, new Object[]{user_ID, postDTO.getTitle(), timestamp, text_ID, picture_ID});
    }

    public void deletePost(int post_ID) {
        String sql = "DELETE FROM posts WHERE post_ID=?";
        jdbc.update(sql, new Object[]{post_ID});
    }

    private static final class PostDTOMapper implements RowMapper {
        public PostDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PostDTO postDTO = new PostDTO();
            postDTO.setPost_ID(rs.getInt("post_ID"));
            postDTO.setUsername(rs.getString("username"));
            postDTO.setTitle(rs.getString("title"));
            postDTO.setContent(rs.getString("content"));
            postDTO.setPicPath(rs.getString("path"));
            postDTO.setDate_time_created(rs.getTimestamp("date_time_created"));
            return postDTO;
        }
    }

    private PostDTO mapRowPostDTO(ResultSet rs) throws SQLException {
        return new PostDTO(rs.getInt("post_ID"), rs.getString("username"),
                rs.getString("title"), rs.getString("content"),
                rs.getString("path"), rs.getTimestamp("date_time_created"));
    }

    private static final class PostMapper implements RowMapper {
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            Post post = new Post();
            post.setPost_ID(rs.getInt("post_ID"));
            post.setUser_ID(rs.getInt("user_ID"));
            post.setTitle(rs.getString("title"));
            post.setText_ID(rs.getInt("text_ID"));
            post.setPicture_ID(rs.getInt("picture_ID"));
            post.setDate_time_created(rs.getDate("date_time_created"));
            return post;
        }
    }
}
