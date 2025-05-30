package security;

import com.mykola.railroad.dto.TypeACL;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthenticatedUserInfo {
    public Integer id;
    public String email;
    public String password;
    public List<TypeACL> acls;
}
