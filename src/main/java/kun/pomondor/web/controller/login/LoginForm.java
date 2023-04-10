package kun.pomondor.web.controller.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter @ToString
public class LoginForm {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
