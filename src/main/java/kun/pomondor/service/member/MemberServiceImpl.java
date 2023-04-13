package kun.pomondor.service.member;

import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

	MemberRepository memberRepository;

	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public Member join(Member member) {

		return memberRepository.join(member);
	}

	@Override
	public Member findById(Long id) {
		return memberRepository.findById(id);
	}

	@Override
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	@Override
	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	@Override
	public Member login(String email, String password) {
		Member findByEmailMember = memberRepository.findByEmail(email);
		if (findByEmailMember == null) return null;

		if (findByEmailMember.getPassword().equals(password)) {
			return findByEmailMember;
		}
		return null;
	}

	@Override
	public List<Member> findByUsername(String keyword) {
		return memberRepository.findByUsername(keyword);
	}
}
