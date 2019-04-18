package rapbattles.rap_battles.Models.POJO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class User {


    private int user_ID;
    private String username;
    private String email;
    private String password;
    private String new_password;
    private String salt;

}
