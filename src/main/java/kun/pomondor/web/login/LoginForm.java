package kun.pomondor.web.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter @ToString
public class LoginForm {

    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

}
