package kun.pomondor.service.s3;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

class S3ServiceTestTest {

	@Test
	void uploadFile() {
		String path = "/Users/kun/Downloads/1.jpeg";
		File file = new File(path);

		try {
			// file to multiPartFile
			MultipartFile multipartFile = new MockMultipartFile("1.jpeg", new FileInputStream(file));


		} catch (IOException e) {
			throw new RuntimeException("파일 변환 오류입니다.");
		}
	}
}