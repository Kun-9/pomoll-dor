package kun.pomondor.web.controller.member;

import kun.pomondor.service.friend.FriendService;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

	private final FriendService friendService;

	@ResponseBody
	@PostMapping("/add-friend")
	public String requestFollow(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginMember,
			@RequestParam("targetMemberId") Long targetMemberId
	) {
		friendService.sendFriendRequest(loginMember, targetMemberId);
		log.info("친구 요청 {} -> {}",loginMember ,targetMemberId);
		return "ok";
	}
}
