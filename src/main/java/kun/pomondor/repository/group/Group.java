package kun.pomondor.repository.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString
public class Group {
	private Long adminId;
	private Long groupId;
	private String groupName;
	private String info;
	private boolean joinStatus;
	private String picture;

	public Group(Long adminId, String groupName, String info, boolean joinStatus) {
		this.adminId = adminId;
		this.groupName = groupName;
		this.info = info;
		this.joinStatus = joinStatus;
	}
}
