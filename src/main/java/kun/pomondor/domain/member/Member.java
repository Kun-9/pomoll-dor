package kun.pomondor.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {
    private long id;
    private String email;
    private String username;
    private String password;
    private int accumTime = 0;

    public Member() {
    }

    public Member(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
