package kun.pomondor.web.member;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import kun.pomondor.web.login.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

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

        if (bindingResult.hasErrors()) {
            return "join-form";
        }

        Member member = new Member(joinForm.getEmail(), joinForm.getUsername(), joinForm.getPassword());

        memberRepository.save(member);

        log.info("id = {} email = {} password = {}",
                member.getId(), member.getEmail(), member.getPassword());

        model.addAttribute("member", new Member());
        return "redirect:/home";
    }
}