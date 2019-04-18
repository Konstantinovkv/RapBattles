package rapbattles.rap_battles.Models.POJO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Comment {

    private int comment_ID;
    private int user_ID;
    private int reply_to_ID;
    private String content;
    private java.sql.Date date_time_created;
}
