package rapbattles.rap_battles.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private int post_ID;
    private String username;
    private String title;
    private String content;
    private String picPath;
    private java.util.Date date_time_created;
    private String fileStr;

    public PostDTO(int post_ID, String username, String title, String content, String picPath, Date date_time_created) {
        this.post_ID = post_ID;
        this.username = username;
        this.title = title;
        this.content = content;
        this.picPath = picPath;
        this.date_time_created = date_time_created;
    }
}
