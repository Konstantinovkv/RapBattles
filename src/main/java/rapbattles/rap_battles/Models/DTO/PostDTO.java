package rapbattles.rap_battles.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
