package rapbattles.rap_battles.Models.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserActivationCode {

    private int user_ID;
    private String activation_code;

    public UserActivationCode(String activation_code) {
        this.activation_code = activation_code;
    }
}
