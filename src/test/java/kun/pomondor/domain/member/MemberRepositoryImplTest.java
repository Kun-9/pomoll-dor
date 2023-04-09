package kun.pomondor.domain.member;

import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class MemberRepositoryImplTest {


	@Autowired
	MemberRepository memberRepository;


	@Test
	void save() {
		Member member = new Member("email@naver.codm4", "ku2", "pwd2");
		memberRepository.join(member);
	}

	@Test
	void findById() {
		Member member = memberRepository.findById(18L);
		System.out.println(member);
	}

	@Test
	void findByEmail() {
		String email = "email@naver.codm4";
		Member member = memberRepository.findByEmail(email);
		Member member2 = memberRepository.findByEmail("sas.com");

		assertThat(member.getEmail()).isEqualTo(email);
		assertThat(member2).isNull();
	}
}