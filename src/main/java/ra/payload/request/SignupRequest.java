package ra.payload.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
@Data
public class SignupRequest {
    private String userName;
    private String password;
    private String email;
    private boolean userStatus;
    private Set<String> listRoles;



}
