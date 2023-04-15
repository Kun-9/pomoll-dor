package kun.pomondor.web.controller.login;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class JoinForm {
    @NotEmpty
    @Length(min = 3, max = 5)
    private String username;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Length(min = 6, max = 15)
    private String password;

}
