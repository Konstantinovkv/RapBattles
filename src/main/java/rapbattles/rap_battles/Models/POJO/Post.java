package rapbattles.rap_battles.Models.POJO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Post {

    private int post_ID;
    private int user_ID;
    private String title;
    private int text_ID;
    private int picture_ID;
    private java.sql.Date date_time_created;
}
