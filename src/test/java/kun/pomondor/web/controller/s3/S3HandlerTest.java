package kun.pomondor.web.controller.s3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class S3HandlerTest {

	@Autowired
	S3Handler s3Handler;

	@Test
	void delete() {
//		s3Handler.fileDelete("/pomondor/post-img/1.jpeg");
//		System.out.println("2");
	}

	@Test
	void delete2() {
//		s3Handler.fileDelete("1.jpeg");
	}

	@Test
	void delete3() {
//		s3Handler.fileDelete("https://kun-buket-test.s3.ap-northeast-2.amazonaws.com/pomondor/post-img/1.jpeg");
//		System.out.println("4");
	}


}