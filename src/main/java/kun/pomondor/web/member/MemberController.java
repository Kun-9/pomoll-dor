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
import java.util.List;


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

        memberRepository.save(member);

        log.info("id = {} email = {} password = {}",
                member.getId(), member.getEmail(), member.getPassword());

        List<Member> members = memberRepository.findAll();
        members.forEach(m -> log.info("repository member = {}", m));

        model.addAttribute("member", new Member());
        return "redirect:/home";
    }

    // 존재하면 true 반환
    private Boolean checkEmailExist(String email) {
        return memberRepository.findByEmail(email) != null;
    }

    // 존재하면 true 반환
    private Boolean checkUsernameExist(String username) {
        Member member = memberRepository.findAll().stream().filter(
                m -> m.getUsername().equals(username)
        ).findFirst().orElse(null);
        return member != null;
    }
}
