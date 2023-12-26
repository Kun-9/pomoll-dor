package kun.pomondor.domain.member;

import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberRepositoryImplTest {

	@Autowired
	private MemberRepository memberRepository;

	String testEmail = "test@example.com";
	String testUserName = "testuser";
	String testPassword = "testpassword";

//	@AfterEach
//	void cleanup() {
//		memberRepository.deleteMember(testEmail);
//		memberRepository.deleteMember(testEmail + 1);
//		memberRepository.deleteMember(testEmail + 2);
//		memberRepository.deleteMember(testEmail + 3);
//	}


	@Test
	void join() {
		// Given
		Member member = new Member();
		member.setEmail(testEmail);
		member.setUsername(testUserName);
		member.setPassword(testPassword);

		// When
		Member savedMember = memberRepository.join(member);

		// Then
		System.out.println("id = " + savedMember.getId());
		assertNotNull(savedMember.getId());
		assertEquals(member.getEmail(), savedMember.getEmail());
		assertEquals(member.getUsername(), savedMember.getUsername());
		assertEquals(member.getPassword(), savedMember.getPassword());
	}

	@Test
	void findById() {
		// Given
		Member member = new Member();
		member.setEmail(testEmail);
		member.setUsername(testUserName);
		member.setPassword(testPassword);
		Member savedMember = memberRepository.join(member);

		// When
		Member foundMember = memberRepository.findById(savedMember.getId());

		// Then
		assertNotNull(foundMember);
		assertEquals(savedMember.getId(), foundMember.getId());
		assertEquals(savedMember.getEmail(), foundMember.getEmail());
		assertEquals(savedMember.getUsername(), foundMember.getUsername());
		assertEquals(savedMember.getPassword(), foundMember.getPassword());
	}

	// Add other test methods for the remaining repository methods
	// ...

	@Test
	void deleteProfileImg() {
		// Given
		Member member = new Member();
		member.setEmail(testEmail);
		member.setUsername(testUserName);
		member.setPassword(testPassword);
		Member savedMember = memberRepository.join(member);
		memberRepository.setProfileImg(savedMember.getId(), "path/to/profile.jpg");

		// When
		memberRepository.deleteProfileImg(savedMember.getId());

		// Then
		assertNull(memberRepository.findById(savedMember.getId()).getPicture());
	}

	@Test
	void findAll() {
//		// Given
//		Member member1 = new Member();
//		member1.setEmail(testEmail + 1);
//		member1.setUsername("testuser1");
//		member1.setPassword("testpassword1");
//		memberRepository.join(member1);
//
//		Member member2 = new Member();
//		member2.setEmail(testEmail + 2);
//		member2.setUsername("testuser2");
//		member2.setPassword("testpassword2");
//		memberRepository.join(member2);
//
//		// When
//		List<Member> allMembers = memberRepository.findAll();
//
//		// Then
//		assertEquals(2, allMembers.size());
	}

	@Test
	void findByEmail() {
		// Given
		Member member = new Member();
		member.setEmail(testEmail);
		member.setUsername(testUserName);
		member.setPassword(testPassword);
		memberRepository.join(member);

		// When
		Member foundMember = memberRepository.findByEmail(testEmail);

		// Then
		assertNotNull(foundMember);
		assertEquals(member.getId(), foundMember.getId());
	}

	@Test
	void findByUsername() {
		// Given
		String uuid = UUID.randomUUID().toString().substring(0,19);
		String username1 = uuid + 1;
		String username2 = uuid + 2;
		String username3 = uuid + 3;

		Member member1 = new Member();
		member1.setEmail(testEmail + 1);
		member1.setUsername(username1);
		member1.setPassword(testPassword);
		memberRepository.join(member1);


		Member member2 = new Member();
		member2.setEmail(testEmail + 2);
		member2.setUsername(username2);
		member2.setPassword(testPassword);
		memberRepository.join(member2);


		Member member3 = new Member();
		member3.setEmail(testEmail + 3);
		member3.setUsername(username3);
		member3.setPassword(testPassword);
		memberRepository.join(member3);

		List<Member> expectedMembers = Arrays.asList(member1, member2, member3);

		// When
		List<Member> foundMembers = memberRepository.findByUsername(uuid);

		// Then
		foundMembers.forEach(m -> m.setPicture("/picture/default.png"));
		expectedMembers.forEach(m -> m.setPicture("/picture/default.png"));

		assertEquals(3, foundMembers.size());
		assertThat(foundMembers).containsExactlyInAnyOrderElementsOf(expectedMembers);

	}

	@Test
	void getRenameCnt() {
		// Given
		Member member = new Member();
		member.setEmail(testEmail);
		member.setUsername(testUserName);
		member.setPassword(testPassword);
		memberRepository.join(member);

		// When
		int renameCnt = memberRepository.getRenameCnt(member.getId());

		// Then
		assertEquals(1, renameCnt); // Assuming initial rename_cnt is 0
	}

	@Test
	void validUsernameExist() {
		// Given
		Member member = new Member();
		member.setEmail(testEmail);
		member.setUsername(testUserName);
		member.setPassword(testPassword);
		memberRepository.join(member);

		// When
		boolean exists = memberRepository.validUsernameExist(testUserName);

		// Then
		assertTrue(exists);
	}

	@Test
	void subtractRenameCnt() {
		// Given
		Member member = new Member();
		member.setEmail(testEmail);
		member.setUsername(testUserName);
		member.setPassword(testPassword);
		memberRepository.join(member);

		// When
		memberRepository.subtractRenameCnt(member.getId());

		// Then
		assertEquals(0, memberRepository.getRenameCnt(member.getId()));
	}

	@Test
	void changeName() {
		// Given
		Member member = new Member();
		member.setEmail(testEmail);
		member.setUsername(testUserName);
		member.setPassword(testPassword);
		memberRepository.join(member);

		// When
		memberRepository.changeName(member.getId(), "newusername");

		// Then
		assertEquals("newusername", memberRepository.findById(member.getId()).getUsername());
	}

}