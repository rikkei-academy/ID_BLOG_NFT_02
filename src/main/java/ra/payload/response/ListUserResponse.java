package ra.payload.response;

import lombok.Data;

import java.util.Date;
@Data
public class ListUserResponse {
    private int userId;
    private String userName;
    private Date created;
    private String email;
    private String userAvatar;
    private String phone;
    private Boolean userStatus;
}
