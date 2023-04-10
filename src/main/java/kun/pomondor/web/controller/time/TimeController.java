package kun.pomondor.web.controller.time;

import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.time.Time;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.service.time.TimeService;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("time")
public class TimeController {

	private final TimeService timeService;
	private final MemberService memberService;

	@ResponseBody
	@PostMapping("/save-time")
	public String saveTime(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
			@RequestParam("time") int time, @RequestParam("start") String start) {

        if (memberId == null) {
            return "fail";
        }

        Member member = memberService.findById(memberId);
        if (member == null) {
            return "fail";
        }

		log.info("start = {}", start);
		Instant instant = Instant.parse(start);

		log.info("instant = {}", instant);


		LocalDateTime startTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));
		log.info("date = {}", startTime);

		Time accum = new Time(startTime, time);

		timeService.saveTime(memberId, accum);
		log.info("time = {}, userId = {}", time, memberId);

		return "ok";
	}
}
