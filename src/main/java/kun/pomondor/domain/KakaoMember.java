package kun.pomondor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class KakaoMember {
	private Long id;
	private String email;
	private String name;
	private String image;
}
