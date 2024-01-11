package kun.pomondor.web.controller.s3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class S3HandlerTest {

	@Autowired
	S3Handler s3Handler;

	@Test
	void upload() {
		String filePath = "pomondor/profile-img";
		String imageUrl = "http://k.kakaocdn.net/dn/RdxBz/btsmR8vAW37/xpaAHrSTPvOi4OaBv2jbyk/img_110x110.jpg";

		try {
			File imageFile = downloadImage(imageUrl, "/tmp/test.jpg");

			System.out.println("Image downloaded successfully. File path: " + imageFile.getAbsolutePath());

			s3Handler.uploadNew(imageFile, "testImage.jpg", filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static File downloadImage(String imageUrl, String destinationPath) throws IOException {
		URL url = new URL(imageUrl);
		File destinationFile = new File(destinationPath);

		try (InputStream in = url.openStream();
		     FileOutputStream out = new FileOutputStream(destinationFile)) {

			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}

			return destinationFile;
		}
	}

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