package kun.pomondor.web.controller.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

//@Component
@Component
@Slf4j
@RequiredArgsConstructor
public class S3Handler {

	private String S3Bucket = "kun-buket-test";
	private String filePath = "pomondor/post-img";

	private final AmazonS3Client amazonS3Client;

	// 업로드
	public List<String> upload(MultipartFile[] multipartFiles, String name) throws Exception {
		List<String> imagePathList = new ArrayList<>();

		for (MultipartFile multipartFile : multipartFiles) {
			long size = multipartFile.getSize();

			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(multipartFile.getContentType());
			objectMetadata.setContentLength(size);

			String fullPath = S3Bucket + "/" + filePath;

			amazonS3Client.putObject(
					new PutObjectRequest(fullPath, name, multipartFile.getInputStream(), objectMetadata)
							.withCannedAcl(CannedAccessControlList.PublicRead)
			);

			String imagePath = amazonS3Client.getUrl(fullPath, name).toString();
			imagePathList.add(imagePath);
		}

		return imagePathList;
//		return new ResponseEntity<Object>(imagePathList, HttpStatus.OK);
	}

	//파일 삭제
	public void fileDelete(String fileName) {
		String id = "arn:aws:iam::784296835500:user/test-user";
		String objectKey = filePath.endsWith("/") ? filePath + fileName : filePath + "/" + fileName;
//		log.info(objectKey);

		try {
			amazonS3Client.deleteObject(
					new DeleteObjectRequest(S3Bucket, objectKey)
			);

		} catch (RuntimeException e) {
			throw e;
		}
	}
}
