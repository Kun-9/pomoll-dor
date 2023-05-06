package kun.pomondor.repository.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString
public class Group {
	private Long groupId;
	private String groupName;
	private String info;
	private Long adminId;
}
