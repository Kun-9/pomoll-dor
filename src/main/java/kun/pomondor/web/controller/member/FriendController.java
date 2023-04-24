package kun.pomondor.web.controller.member;

import kun.pomondor.repository.friend.FriendRequest;
import kun.pomondor.repository.member.Member;
import kun.pomondor.service.friend.FriendMemberForm;
import kun.pomondor.service.friend.FriendService;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

	private final FriendService friendService;
	private final MemberService memberService;

	@GetMapping("/view")
	public String viewFriends(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
			Model model) {

		Member member = memberService.findById(memberId);

		List<FriendMemberForm> receiveList = friendRequestToMemberObject(friendService.findReceiveRequest(memberId));
		List<FriendMemberForm> friendList = friendRequestToMemberObject(friendService.findFriends(memberId));
		List<FriendMemberForm> sentList = friendRequestToMemberObject(friendService.findSentRequestById(memberId));

		model.addAttribute("sentList", sentList);
		model.addAttribute("receiveRequests", receiveList);
		model.addAttribute("friendList", friendList);
		model.addAttribute("member", member);

		return "user/friend-page";
	}

	private List<FriendMemberForm> friendRequestToMemberObject(List<FriendRequest> receiveRequests) {
		List<FriendMemberForm> receive = new ArrayList<>();

		for (FriendRequest receiveRequest : receiveRequests) {
			Member sendMember = memberService.findById(receiveRequest.getSenderId());
			Member receiveMember = memberService.findById(receiveRequest.getReceiverId());
			String status = receiveRequest.getStatus();
			receive.add(new FriendMemberForm(sendMember, receiveMember, status));
		}
		return receive;
	}

	@ResponseBody
	@PostMapping("/add-friend")
	public String requestRequest(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginMember,
			@RequestParam("targetMemberId") Long targetMemberId
	) {
		friendService.sendFriendRequest(loginMember, targetMemberId);
		log.info("친구 요청 {} -> {}",loginMember ,targetMemberId);
		return "ok";
	}

	@ResponseBody
	@PostMapping("/cancel-friend")
	public String cancelRequest(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginMember,
			@RequestParam("targetMemberId") Long targetMemberId
	) {
		friendService.cancelRequest(loginMember, targetMemberId);
		log.info("친구 요청 취소 {} -> {}",loginMember ,targetMemberId);
		return "ok";
	}

	@ResponseBody
	@PostMapping("/accept-friend")
	public String acceptRequest(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginMember,
			@RequestParam("targetMemberId") Long targetMemberId
	) {
		log.info("친구 요청 수락 {} -> {}",loginMember ,targetMemberId);

		friendService.acceptFriendRequest(loginMember, targetMemberId);
		return "ok";
	}

	@ResponseBody
	@PostMapping("/delete-friend")
	public String deleteFriend(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginMember,
			@RequestParam("targetMemberId") Long targetMemberId
	) {
		log.info("친구 삭제 {} -> {}",loginMember ,targetMemberId);

		friendService.deleteFriend(loginMember, targetMemberId);
		return "ok";
	}

	@ResponseBody
	@PostMapping("/reject-friend")
	public String rejectFriend(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginMember,
			@RequestParam("targetMemberId") Long targetMemberId
	) {
		log.info("친구 요청 거절 {} -> {}",loginMember ,targetMemberId);

		friendService.rejectFriendRequest(loginMember, targetMemberId);
		return "ok";
	}
}
