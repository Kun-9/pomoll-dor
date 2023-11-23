package kun.pomondor.service;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ThumbnailatorTest {

	@Test
	void thumbnailsTest() {
		String filePath = "static/picture/1.jpeg";

		Resource resource = new ClassPathResource(filePath);


	}


}
