package kun.pomondor.repository.group;

import kun.pomondor.repository.etc.food.post.GroupMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupRepositoryImplTest {

	@Autowired
	GroupRepository groupRepository;

//	@Test
//	void createGroup() {
//		Group group = new Group(19L, null, "동근 그룹", "안녕하세요. 동근그룹입니다.", false, null);
//		groupRepository.createGroup(group);
//	}
//
//	@Test
//	void getMembers() {
//		Long groupId = 2L;
//
//		List<GroupMember> groupMembers = groupRepository.findGroupMembers(groupId);
//		for (GroupMember groupMember : groupMembers) {
//			System.out.println(groupMember);
//		}
//	}
//
//	@Test
//	void findAllGroup() {
//		List<Group> allGroup = groupRepository.findAllGroup();
//		for (Group group : allGroup) {
//			System.out.println(group);
//		}
//	}

}