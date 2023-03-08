package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;
    @Column(name = "userName", unique = true)
    private String userName;
    @Column(name = "passwords")
    private String passwords;
    @Column(name = "created")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "userAvatar")
    private String userAvatar;
    @Column(name = "phone")
    private String phone;
    @Column(name = "userStatus")
    private boolean userStatus;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "UserId"), inverseJoinColumns = @JoinColumn(name = "RoleId"))
    private Set<Roles> listRoles = new HashSet<>();
}
