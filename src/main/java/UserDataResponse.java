import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UserDataResponse {
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
