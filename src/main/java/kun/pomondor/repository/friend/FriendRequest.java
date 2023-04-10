package kun.pomondor.repository.friend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor
public class FriendRequest {
	private Long senderId;
	private Long receiverId;
	private String status;
}
