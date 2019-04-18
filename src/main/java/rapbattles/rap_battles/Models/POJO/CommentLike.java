package rapbattles.rap_battles.Models.POJO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommentLike {

    private int comment_like_ID;
    private int comment_ID;
    private int user_ID;
    private boolean reaction;

}
