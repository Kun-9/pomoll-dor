package kun.pomondor.repository.group;

import kun.pomondor.repository.member.MemberMin;

import java.util.List;

public interface GroupRepository {
	void createGroup(Group group);

	List<MemberMin> findGroupMembers(Long groupId);

	List<Group> findManagingGroups(Long memberId);

	int getManagingGroupCnt(Long memberId);

	void sendJoinRequest(Long memberId, Long groupId);

	void rejectRequest(Long rejectingMemberId, Long groupId);

	void acceptRequest(Long acceptMemberId, Long groupId);

	void deleteGroup(Long memberId, Long groupId);

	List<Group> findAllGroup();

}
