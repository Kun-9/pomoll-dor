package kun.pomondor.repository.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor
public class MemberMin {
	private long id;
	private String username;
	private String picture;
	private String email;

	public MemberMin(long id, String username, String picture) {
		this.id = id;
		this.username = username;
		this.picture = picture;
	}
}
