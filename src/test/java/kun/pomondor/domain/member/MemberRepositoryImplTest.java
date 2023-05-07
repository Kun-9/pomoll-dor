package kun.pomondor.domain.member;

import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class MemberRepositoryImplTest {


	@Autowired
	MemberRepository memberRepository;


//	@Test
//	void save() {
//		Member member = new Member("email@naver.codm4", "ku2", "pwd2");
//		memberRepository.join(member);
//	}
//
//	@Test
//	void findById() {
//		Member member = memberRepository.findById(18L);
//		System.out.println(member);
//	}
//
//	@Test
//	void findByEmail() {
//		String email = "email@naver.codm4";
//		Member member = memberRepository.findByEmail(email);
//		Member member2 = memberRepository.findByEmail("sas.com");
//
//		assertThat(member.getEmail()).isEqualTo(email);
//		assertThat(member2).isNull();
//	}
//
//	@Test
//	void findByUsername() {
//		String username = "k";
//
//		List<Member> list = memberRepository.findByUsername(username);
//		for (Member member : list) {
//			System.out.println(member);
//		}
//	}
//
//	@Test
//	void renameCnt() {
//		int renameCnt = memberRepository.getRenameCnt(19L);
//		assertThat(renameCnt).isEqualTo(1);
//	}
//
//	@Test
//	void validName() {
//		boolean exist = memberRepository.validUsernameExist("동근");
//		assertThat(exist).isTrue();
//	}
}