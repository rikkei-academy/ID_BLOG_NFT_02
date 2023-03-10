package ra.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
public class UpdaUserQuyen {
    private Set<String> listRoles;

    public UpdaUserQuyen(Set<String> listRoles) {
        this.listRoles = listRoles;
    }
}
