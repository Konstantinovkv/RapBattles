package rapbattles.rap_battles.Models.POJO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostLike {

    private int post_like_ID;
    private int post_ID;
    private int user_ID;
    private boolean reaction;
}
