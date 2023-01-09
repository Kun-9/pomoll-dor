package kun.pomondor.domain.member;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemberRepository {

    private static long sequence = 0L;
    private static Map<Long, Member> store = new HashMap<>();

    public Member save(Member member) {
        member.setId(++sequence);
        long id = member.getId();
        store.put(id, member);
        return member;
    }
    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    public Member findByEmail(String email) {
        Iterator<Member> iterator = findAll().iterator();
        if (iterator.hasNext()) {
            Member member = iterator.next();
            String extracedEmail = member.getEmail();
            if (extracedEmail.equals(email)) return member;
        }
        return null;
    }

    public boolean login(String email, String password) {
        Member byEmail = findByEmail(email);
        if (byEmail != null && byEmail.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
