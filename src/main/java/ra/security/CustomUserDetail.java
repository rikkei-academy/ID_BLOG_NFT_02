package ra.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ra.model.entity.Users;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {
    private int userId;
    private String userName;
    @JsonIgnore
    private String passwords;
    public Date created;
    public String email;
    public String userAvatar;
    public String phone;
    public boolean userStatus;



    private Collection<? extends GrantedAuthority> authorities;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public static CustomUserDetail mapUserToUserDetail(Users user){
        List<GrantedAuthority> listAuthorities = user.getListRoles().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().name()))
                .collect(Collectors.toList());
        return new CustomUserDetail (
                user.getUserId(),
                user.getUserName(),
                user.getPasswords(),
                user.getCreated(),
                user.getEmail(),
                user.getUserAvatar(),
                user.getPhone(),
                user.isUserStatus(),
                listAuthorities
        );
    }

    @Override
    public String getPassword() {
        return this.passwords;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

