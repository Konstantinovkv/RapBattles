package rapbattles.rap_battles.Models.POJO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class User {


    private int user_ID;
    private String username;
    private String email;
    private String password;
    private String second_password;
    private String salt;

}
