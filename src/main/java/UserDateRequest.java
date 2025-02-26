import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDateRequest {
    private  String   id;
    private  String name;
    private  String surname;
    private  String phone;
    private  String email;
    private  String role;
    private  String gender;
    private  String birthDate;
    private  String avatarUrl;
    private  String backgroundUrl;
    //
    private String blocked;
}
