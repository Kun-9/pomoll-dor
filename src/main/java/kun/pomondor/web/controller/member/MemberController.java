package kun.pomondor.web.controller.member;

import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.time.Time;
import kun.pomondor.service.friend.FriendService;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.service.time.TimeService;
import kun.pomondor.web.SessionConst;
import kun.pomondor.web.controller.login.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;

import static kun.pomondor.web.controller.statistics.StatisticsUtils.*;
import static kun.pomondor.web.controller.statistics.StatisticsUtils.getWeekTimeStr;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final FriendService friendService;
    private final TimeService timeService;

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("joinForm") JoinForm joinForm) {
        return "join-form";
    }

    @PostMapping("/join")
    public String addMember(@Valid @ModelAttribute JoinForm joinForm,
                            BindingResult bindingResult, Model model) {

        bindingResult.getAllErrors().forEach(
                e -> log.info("error = {}", e)
        );

        if (checkEmailExist(joinForm.getEmail())) {
            bindingResult.rejectValue("email", "","이미 등록된 이메일입니다.");
        }

        if (checkUsernameExist(joinForm.getUsername())) {
            bindingResult.rejectValue("username", "","이미 등록된 닉네임입니다.");
        }

        if (bindingResult.hasErrors()) {
            return "join-form";
        }

        Member member = new Member(joinForm.getEmail(), joinForm.getUsername(), joinForm.getPassword());

        memberService.join(member);

        log.info("id = {} email = {} password = {}",
                member.getId(), member.getEmail(), member.getPassword());

//        List<Member> members = memberRepository1.findAll();
//        members.forEach(m -> log.info("repository member = {}", m));

        model.addAttribute("member", new Member());
        return "redirect:/home";
    }

    // 존재하면 true 반환
    private Boolean checkEmailExist(String email) {
        return memberService.findByEmail(email) != null;
    }

    // 존재하면 true 반환
    private Boolean checkUsernameExist(String username) {
        Member member = memberService.findAll().stream().filter(
                m -> m.getUsername().equals(username)
        ).findFirst().orElse(null);
        return member != null;
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

    @ResponseBody
    @GetMapping("/validate-username/{username}")
    public boolean validateUserName(@PathVariable String username) {
        return memberService.validUsernameExist(username);
    }
}
