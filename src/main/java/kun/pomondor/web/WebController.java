package kun.pomondor.web;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class WebController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("member", new Member());
        return "index";
    }

    @PostMapping("/save")
    public String addMember(@ModelAttribute Member member, Model model) {
        log.info("post");
        memberRepository.save(member);
        long id = member.getId();

        log.info("id = {} email = {} password = {}",
                member.getId(), member.getEmail(), member.getPassword());

        model.addAttribute("member", new Member());
        return "redirect:/";
    }

    @GetMapping("login")
    public String loginMember(@ModelAttribute Member member, Model model) {
        String email = member.getEmail();
        String password = member.getPassword();

        boolean login = memberRepository.login(email, password);
        if (login) {
            log.info("로그인 성공");
        } else {
            log.info("로그인 실패");
        }

        model.addAttribute("member", new Member());

        return "redirect:/";
    }
}
