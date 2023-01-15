package kun.pomondor.web;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/basic")
public class WebController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("member", new Member());
        return "index";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("member", new Member());
        return "user-join";
    }

    @PostMapping("/join")
    public String addMember(@ModelAttribute Member member, Model model) {
        log.info("post");
        memberRepository.save(member);

        log.info("id = {} email = {} password = {}",
                member.getId(), member.getEmail(), member.getPassword());

        model.addAttribute("member", new Member());
        return "redirect:/basic/home";
    }

    @GetMapping("/login")
    public String loginMember(@ModelAttribute Member member, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();

        String email = member.getEmail();
        String password = member.getPassword();

        Member loginMember = memberRepository.login(email, password);
        if (loginMember != null) {
            log.info("로그인 성공");
            session.setAttribute("userId", loginMember.getId());
            Object userId = session.getAttribute("userId");
            log.info("session : userId = {}", userId);
        } else {
            log.info("로그인 실패");
        }

        return "redirect:/basic/home";
    }

    @ResponseBody
    @PostMapping("/save-time")
    public String saveTime(@RequestParam int time, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userId");
        if (userId != null) {
            int accumTime = memberRepository.saveUserTime(Long.parseLong(String.valueOf(userId)), time);
            log.info("time = {}, userId = {}, accumTime = {}", time, userId, accumTime);
            return "ok";
        } else {
            return "fail";
        }
    }
}
