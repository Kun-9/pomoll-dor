package kun.pomondor.service.friend;

import kun.pomondor.repository.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor
public class FriendMemberForm {
	Member sendMember;
	Member receiveMember;
	String status;
}
