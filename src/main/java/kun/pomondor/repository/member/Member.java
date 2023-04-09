package kun.pomondor.repository.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Member {
    private long id;
    private String email;
    private String username;
    private String nickname;
    private String password;

    public Member() {
    }

    public Member(long id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
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
