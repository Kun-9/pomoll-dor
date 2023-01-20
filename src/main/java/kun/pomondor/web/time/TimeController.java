package kun.pomondor.web.time;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("time")
public class TimeController {

    private final MemberRepository memberRepository;

    @ResponseBody
    @PostMapping("/save-time")
    public String saveTime(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
            @RequestParam int time) {

        if (memberId == null) {
            return "fail";
        }

        Member member = memberRepository.findById(memberId);
        if (member == null) {
            return "fail";
        }
        int accumTime = memberRepository.saveUserTime(memberId, time);
        log.info("time = {}, userId = {}, accumTime = {}", time, memberId, accumTime);
        return "ok";
    }
}
