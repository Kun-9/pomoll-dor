package kun.pomondor.service.group;

import kun.pomondor.repository.etc.food.post.GroupMember;
import kun.pomondor.repository.group.Group;
import kun.pomondor.repository.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepository;

	@Override
	public Long createGroup(Group group) {
		return groupRepository.createGroup(group);
	}

	@Override
	public List<GroupMember> findGroupMembers(Long groupId) {
		return groupRepository.findGroupMembers(groupId);
	}

	@Override
	public List<Group> findManagingGroups(Long memberId) {
		return groupRepository.findManagingGroups(memberId);
	}

	@Override
	public int getManagingGroupCnt(Long memberId) {
		return groupRepository.getManagingGroupCnt(memberId);
	}

	@Override
	public int getMemberCnt(Long groupId) {
		return groupRepository.getMemberCnt(groupId);
	}

	@Override
	public void sendJoinRequest(Long memberId, Long groupId) {
		groupRepository.sendJoinRequest(memberId, groupId);

		if (groupRepository.getGroupStatus(groupId)) {
			groupRepository.acceptRequest(memberId, groupId);
		}
	}

	@Override
	public void rejectRequest(Long rejectingMemberId, Long groupId) {
		groupRepository.rejectRequest(rejectingMemberId, groupId);
	}

	@Override
	public void acceptRequest(Long acceptMemberId, Long groupId) {
		groupRepository.acceptRequest(acceptMemberId, groupId);
	}

	@Override
	public void deleteGroup(Long memberId, Long groupId) {
		groupRepository.deleteGroup(memberId, groupId);
	}

	@Override
	public List<Group> findAllGroup() {
		return groupRepository.findAllGroup();
	}

	@Override
	public Boolean isAdmin(Long adminId, Long groupId) {
		return groupRepository.isAdmin(adminId, groupId);
	}

	@Override
	public List<Group> joinedGroup(Long memberId) {
		return groupRepository.joinedGroup(memberId);
	}

	@Override
	public void setGroupPicture(Long groupId, String URI) {
		groupRepository.setGroupPicture(groupId, URI);
	}

	@Override
	public Group findGroupById(Long groupId) {
		return groupRepository.findGroupById(groupId);
	}
}
