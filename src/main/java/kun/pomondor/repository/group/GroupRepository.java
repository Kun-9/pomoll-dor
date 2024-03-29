package kun.pomondor.repository.group;

import kun.pomondor.repository.etc.food.post.GroupMember;
import kun.pomondor.repository.member.MemberMin;

import java.util.List;

public interface GroupRepository {
	Long createGroup(Group group);

	Group findGroupById(Long groupId);

	List<GroupMember> findGroupMembers(Long groupId);

	List<Group> findManagingGroups(Long memberId);

	int getManagingGroupCnt(Long memberId);

	int getMemberCnt(Long groupId);

	List<Group> joinedGroup(Long memberId);

	void sendJoinRequest(Long memberId, Long groupId);

	void rejectRequest(Long rejectingMemberId, Long groupId);

	void acceptRequest(Long acceptMemberId, Long groupId);

	void deleteGroup(Long memberId, Long groupId);

	List<Group> findAllGroup();

	Boolean isAdmin(Long adminId, Long groupId);

	Boolean getGroupStatus(Long groupId);

	void setGroupPicture(Long groupId, String URI);
}
