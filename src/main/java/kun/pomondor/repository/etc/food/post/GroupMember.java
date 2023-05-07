package kun.pomondor.repository.etc.food.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor
public class GroupMember {
	private long id;
	private String username;
	private String picture;
	private String regDate;
}
