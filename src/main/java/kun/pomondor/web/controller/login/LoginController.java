package kun.pomondor.web.controller.login;

import kun.pomondor.domain.util.KakaoAPI;
import kun.pomondor.repository.member.Member;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class LoginController {

    private final MemberService memberService;
    private final KakaoAPI kakaoAPI;

    @Value("${kakao.redirectURI}")
    String redirectURI;
    @Value("${kakao.restApiCode}")
    String restApiCode;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login-form";
    }

    @ResponseBody
    @GetMapping("/kakao-login")
    public String kakaoLogin(@RequestParam String code, @RequestParam(required = false) String error) {

//        String restApiCode = "798f3d347345f730f1e9e0f6a6ce6ac0";
//        String redirectURI = "http://localhost:8080/member/kakao-login";

        String token = kakaoAPI.getToken(restApiCode, redirectURI, code);

        System.out.println(token);
        kakaoAPI.getUserInfo(token);

        return "ok";
    }

    @PostMapping("/login")
    public String loginMember(
            @Valid @ModelAttribute LoginForm loginForm,
            BindingResult bindingResult, HttpServletRequest request,
            @RequestParam(defaultValue = "/") String redirectURL) {

        log.info("로그인 시도 : {}", loginForm);

        Member loginMember = memberService.login(loginForm.getEmail(), loginForm.getPassword());

        if (bindingResult.hasErrors()) {
            return "login-form";
        }

        if (loginMember == null) {
            log.info("로그인 실패");
            bindingResult.reject("","아이디 또는 비밀번호를 확인해주세요.");
//            return "redirect:/home";
        }

        if (bindingResult.hasErrors()) {
            return "login-form";
        }

        HttpSession session = request.getSession(true);
        Integer memberLevel;
        log.info("로그인 진행 로그");

        if (loginMember != null && loginMember.getEmail().equals("admin@naver.com")) {
            log.info("관리자 로그인 성공");
            memberLevel = SessionConst.ADMIN_LOGIN;
        } else {
            log.info("일반 사용자 로그인 성공 email = {}", loginForm.getEmail());
            memberLevel = SessionConst.COMMON_LOGIN;
        }

        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getId());
        session.setAttribute("currentMember", loginMember);
        session.setAttribute(SessionConst.LOGIN_LEVEL, memberLevel);

        return "redirect:" + redirectURL;
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        session.getAttributeNames()
                .asIterator()
                .forEachRemaining(name -> log.info("session expire : name = {}, value = {}", name, session.getAttribute(name)) );
        session.invalidate();

        model.addAttribute("member", new Member());
        return "redirect:/home";
    }
}
