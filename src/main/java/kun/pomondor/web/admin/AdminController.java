package kun.pomondor.web.admin;


import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberRepository1;
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
@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository1 memberRepository1;

    @GetMapping("/index")
    public String adminPageForm(Model model) {

        List<Member> members = memberRepository1.findAll();

        model.addAttribute("members", members);

        return "admin-page";
    }

    @GetMapping("/member")
    public String joinForm(@ModelAttribute("adminJoinForm") AdminJoinForm joinForm) {
        return "admin/create-member";
    }

    @PostMapping("/member")
    public String addMember(@Valid @ModelAttribute("adminJoinForm") AdminJoinForm joinForm,
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
            return "admin/create-member";
        }

        Member member = new Member(joinForm.getEmail(), joinForm.getUsername(), joinForm.getPassword());
//        member.setAccumTime(joinForm.getAccumTime());

        memberRepository1.save(member);

        log.info("id = {} email = {} password = {}",
                member.getId(), member.getEmail(), member.getPassword());

        List<Member> members = memberRepository1.findAll();
        members.forEach(m -> log.info("repository member = {}", m));

        model.addAttribute("member", new Member());
        return "redirect:/admin/index";
    }

    // 존재하면 true 반환
    private Boolean checkEmailExist(String email) {
        return memberRepository1.findByEmail(email) != null;
    }

    // 존재하면 true 반환
    private Boolean checkUsernameExist(String username) {
        Member member = memberRepository1.findAll().stream().filter(
                m -> m.getUsername().equals(username)
        ).findFirst().orElse(null);
        return member != null;
    }
}

