package kun.pomondor.web.controller.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Handler {

	private final String S3Bucket = "kun-buket-test";

	private final AmazonS3Client amazonS3Client;

	@Value("${localPath}")
	private String localPath;


	// 업로드
//	public List<String> upload(MultipartFile[] multipartFiles, String name, String filePath) throws Exception {
//		List<String> imagePathList = new ArrayList<>();
//
//		for (MultipartFile multipartFile : multipartFiles) {
//			long size = multipartFile.getSize();
//
//			ObjectMetadata objectMetadata = new ObjectMetadata();
//			objectMetadata.setContentType(multipartFile.getContentType());
//			objectMetadata.setContentLength(size);
//
//			String fullPath = S3Bucket + "/" + filePath;
//
//			amazonS3Client.putObject(
//					new PutObjectRequest(fullPath, name, multipartFile.getInputStream(), objectMetadata)
//							.withCannedAcl(CannedAccessControlList.PublicRead)
//			);
//
//			String imagePath = amazonS3Client.getUrl(fullPath, name).toString();
//			imagePathList.add(imagePath);
//		}
//
//		return imagePathList;
////		return new ResponseEntity<Object>(imagePathList, HttpStatus.OK);
//	}
//
//
//
//	public List<String> uploadThumbnail(MultipartFile[] multipartFiles, String name, String filePath, int width, int height) throws Exception {
//
//		List<String> imagePathList = new ArrayList<>();
//
//		for (MultipartFile multipartFile : multipartFiles) {
//
//			name = "thumbnail_" + name;
//
//			// Thumbnails를 사용하여 썸네일 생성
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			Thumbnails.of(multipartFile.getInputStream())
//					.size(width, height)
//					.toOutputStream(os);
//
//			byte[] thumbnailBytes = os.toByteArray();
//
//			// ObjectMetaData 설정
//			ObjectMetadata objectMetadata = new ObjectMetadata();
//			objectMetadata.setContentType(multipartFile.getContentType());
//			objectMetadata.setContentLength(thumbnailBytes.length);
//
//
//			String fullPath = S3Bucket + "/" + filePath;
//
//			amazonS3Client.putObject(
//					new PutObjectRequest(fullPath, name, new ByteArrayInputStream(thumbnailBytes), objectMetadata)
//							.withCannedAcl(CannedAccessControlList.PublicRead)
//			);
//
//			String imagePath = amazonS3Client.getUrl(fullPath, name).toString();
//			imagePathList.add(imagePath);
//		}
//
//		return imagePathList;
//	}

	//파일 삭제
	public void fileDelete(String fileName, String filePath) {
		String id = "arn:aws:iam::784296835500:user/test-user";
		String objectKey = filePath + "/" + fileName;
//		log.info(objectKey);

		try {
			amazonS3Client.deleteObject(
					new DeleteObjectRequest(S3Bucket, objectKey)
			);

		} catch (RuntimeException e) {
			throw e;
		}
	}


	public String uploadNew(MultipartFile multipartFile, String name, String filePath, int width, int height) throws IOException {
		File uploadFile = convert(multipartFile)
				.orElseThrow(() -> new IllegalArgumentException("MultipartFile 변환 실패"));
		return uploadNew(uploadFile, name, filePath, width, height);
	}

	public String uploadNew(MultipartFile multipartFile, String name, String filePath) throws IOException {
		File uploadFile = convert(multipartFile)
				.orElseThrow(() -> new IllegalArgumentException("MultipartFile 변환 실패"));
		return uploadNew(uploadFile, name, filePath);
	}

	public String uploadNew(File uploadFile, String name, String filePath) throws IOException {
		String fileName = filePath + "/" + name;
		return putS3(uploadFile, fileName);
	}

	public String uploadNew(File uploadFile, String name, String filePath, int width, int height) throws IOException {
		String fileName = filePath + "/" + name;
		String thumbnailFileName = filePath + "/" + "thumbnail_" + name;

		File thumbnailFile = new File(localPath + "/" + "thumbnail_" + name);

		generateThumbnail(uploadFile, thumbnailFile, width, height);
		putS3(thumbnailFile, thumbnailFileName);
		String uploadUrl = putS3(uploadFile, fileName);

		return uploadUrl;
	}

	private void generateThumbnail(File inputFile, File outputFile, int width, int height) throws IOException {
		Thumbnails.of(inputFile)
				.size(width, height)
				.toFile(outputFile);
	}

	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(
				new PutObjectRequest(S3Bucket, fileName, uploadFile)
						.withCannedAcl(CannedAccessControlList.PublicRead)
		);

		removeDownloadedFile(uploadFile);

		return amazonS3Client.getUrl(S3Bucket, fileName).toString();
	}

	private void removeDownloadedFile(File targetFile) {
		targetFile.delete();
	}

	private Optional<File> convert(MultipartFile file) throws IOException {
		File convertFile = new File(file.getOriginalFilename());

		System.out.println("컨버트 파일 위치 : " + convertFile.getAbsolutePath());

		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}
			return Optional.of(convertFile);
		}

		return Optional.empty();
	}
}
