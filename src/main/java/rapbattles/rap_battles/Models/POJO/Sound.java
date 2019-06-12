package rapbattles.rap_battles.Models.POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Sound {

    private int sound_ID;
    private String path;
    private int user_ID;
    private int post_ID;

    public Sound(String path, int user_ID) {
        this.path = path;
        this.user_ID = user_ID;
    }
}
