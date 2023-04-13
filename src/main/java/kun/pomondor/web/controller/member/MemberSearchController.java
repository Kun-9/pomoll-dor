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

	@GetMapping("/info/{targetId}")
	public String searchUser(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
			@PathVariable Long targetId,
			Model model) {

		String status = friendService.getStatus(memberId, targetId);

		Member member = memberService.findById(memberId);
		Member targetMember = memberService.findById(targetId);

		LocalDate currentDate = LocalDate.now();

		// 오늘 누적 시간
		int todayTime = timeService.findAccumTimeByDate(targetId, currentDate);

		// 초 -> 시 분 초 문자열
		String timeStr = getTimeStr(todayTime);

		// 현재 날짜가 속한 주의 일요일
		LocalDate sundayDate = getSunday(currentDate);

		Double[] week_time = getWeekAccumTime(sundayDate, timeService, targetId);

		String[] week_time_str = getWeekTimeStr(timeService, targetId, sundayDate);

		List<Time> times = timeService.findAllTimes(targetId);

		model.addAttribute("member", member);
		model.addAttribute("targetMember", targetMember);
		// 해당 아이디의 모든 타임 객체
		model.addAttribute("times", times);
		// 오늘 누적 시간 문자열
		model.addAttribute("timeStr", timeStr);
		// 최근 일주일의 요일별 시간
		model.addAttribute("week_time", week_time);
		// 최근 일주일의 요일별 시간 문자열
		model.addAttribute("week_time_str", week_time_str);
		// 현재 조회하는 회원과의 상태
		model.addAttribute("status", status);

		return "user/user-info";
	}

	@GetMapping("members/")
	@ResponseBody
	public List<Member> searchUsername(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginId,
			@RequestParam("query") String username,
			Model model
	) {
		log.info(username);
		List<Member> members = memberService.findByUsername(username);
		model.addAttribute("members", members);
		return members;
	}
}
