package kun.pomondor.web.controller.member;


import kun.pomondor.repository.member.Member;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberSettingController {

	private final MemberService memberService;

	@GetMapping("setting")
	public String memberSettingPage(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginId,
			Model model
	) {

		Member member = memberService.findById(loginId);
		model.addAttribute("member", member);
		return "user/user-setting";
	}
}
