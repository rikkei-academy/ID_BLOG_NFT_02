package ra.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePassword {
    private String oldPassword;
    private String password;

    public ChangePassword(String oldPassword, String password) {
        this.oldPassword = oldPassword;
        this.password = password;
    }
}
