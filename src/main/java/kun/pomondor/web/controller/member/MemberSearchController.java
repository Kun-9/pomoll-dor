package kun.pomondor.web.controller.member;

import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.time.Time;
import kun.pomondor.service.friend.FriendService;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.service.time.TimeService;
import kun.pomondor.web.SessionConst;
import kun.pomondor.web.controller.statistics.StatisticsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static kun.pomondor.web.controller.statistics.StatisticsUtils.*;
import static kun.pomondor.web.controller.statistics.StatisticsUtils.getTimeStr;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("search")
public class MemberSearchController {
	private final MemberService memberService;
	private final TimeService timeService;
	private final FriendService friendService;

	@GetMapping("/all")
	public String searchAllUsers(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
			Model model) {
		List<Member> members = memberService.findAll();
		Member member = memberService.findById(memberId);
		model.addAttribute("members", members);
		model.addAttribute("member", member);
		return "user/search-user";
	}

	@GetMapping("members/")
	@ResponseBody
	public List<Member> searchUsername(@RequestParam("query") String username) {
		return memberService.findByUsername(username);
	}
}
