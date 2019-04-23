package rapbattles.rap_battles.Models.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {

    private int user_ID;
    private String username;
    private String email;
    private boolean activated;

    public UserDTO(String username) {
        this.username = username;
    }
}
