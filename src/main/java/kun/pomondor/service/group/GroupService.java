package kun.pomondor.service.group;

import kun.pomondor.repository.etc.food.post.GroupMember;
import kun.pomondor.repository.group.Group;

import java.util.List;

public interface GroupService {
	Long createGroup(Group group);

	List<GroupMember> findGroupMembers(Long groupId);

	List<Group> findManagingGroups(Long memberId);

	int getManagingGroupCnt(Long memberId);

	int getMemberCnt(Long groupId);

	void sendJoinRequest(Long memberId, Long groupId);

	void rejectRequest(Long rejectingMemberId, Long groupId);

	void acceptRequest(Long acceptMemberId, Long groupId);

	void deleteGroup(Long memberId, Long groupId);

	List<Group> findAllGroup();

	Boolean isAdmin(Long adminId, Long groupId);

	List<Group> joinedGroup(Long memberId);

	void setGroupPicture(Long groupId, String URI);

	Group findGroupById(Long groupId);

}
