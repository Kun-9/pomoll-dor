//package kun.pomondor.service;
//
//import kun.pomondor.repository.member.Member;
//import kun.pomondor.repository.member.MemberRepository;
//import kun.pomondor.service.member.MemberService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//class MemberServiceImplTest {
//
//	@Autowired
//	MemberRepository memberRepository;
//
//	@Autowired
//	MemberService memberService;
//
//
//	@Test
//	void login() {
//		// given
//		String email = "email@naver";
//		String password = "password";
//
//		// when
//		Member login1 = memberService.login(email, password);
//		System.out.println(login1);
//
//		Member login2 = memberService.login(email, "aa");
//		System.out.println(login2);
//
//		//then
//		assertThat(login1).isNotNull();
//		assertThat(login2).isNull();
//	}
//}