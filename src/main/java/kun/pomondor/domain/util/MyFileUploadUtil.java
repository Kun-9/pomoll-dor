package kun.pomondor.domain.util;

import kun.pomondor.web.controller.s3.S3Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MyFileUploadUtil {

	@Value("${localPath}")
	private String localPath;

	private final S3Handler s3Handler;

	// multipartFile에 파일이 포함되어 있는지 확인하고 이미지 파일인지 검증, 이미지 파일이라면 타입 리턴
	public String validateImg(MultipartRequest multipartRequest, MultipartFile[] files) {
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String s : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(s);
			files[0] = multipartFile;

			String[] contentType = multipartFile.getContentType().split("/");
			// 이미지 파일이 아니라면 리턴
			if (!contentType[0].equals("image")) {
				return null;
			}
			// 이미지 타입 리턴
			return contentType[1];
		}
		return null;
	}

	public String saveRestaurantPostImgToS3(MultipartFile[] files, String saveFileName) throws Exception {
		String filePath = "pomondor/post-img";
		return s3Handler.uploadNew(files[0], saveFileName, filePath, 700, 800);
	}

	public String saveRestaurantPostImgToS3(File file, String saveFileName) throws Exception {
		String filePath = "pomondor/post-img";

		return s3Handler.uploadNew(file, saveFileName, filePath, 700, 800);
	}

	public String saveProfileImgToS3(MultipartFile[] files, String saveFileName) throws Exception {
		String filePath = "pomondor/profile-img";
		return s3Handler.uploadNew(files[0], saveFileName, filePath, 100, 200);
	}

	public String saveProfileImgToS3(File file, String saveFileName) throws Exception {
		String filePath = "pomondor/profile-img";
		return s3Handler.uploadNew(file, saveFileName, filePath, 100, 200);
	}

	public String saveProfileImgToS3(String url, String saveFileName) throws Exception {
		File file = convertUrlToFile(url, saveFileName);
		if (file == null) return null;
		return saveProfileImgToS3(file, saveFileName);
	}

//	public String saveRestaurantPostImgToS3(MultipartFile[] files, String saveFileName) throws Exception {
//		String filePath = "pomondor/post-img";
//
//		s3Handler.uploadThumbnail(files, saveFileName, filePath, 400, 800);
//		List<String> imgPathList = s3Handler.upload(files, saveFileName, filePath);
//		return imgPathList.get(0);
//	}
//
//	public String saveProfileImgToS3(MultipartFile[] files, String saveFileName) throws Exception {
//		String filePath = "pomondor/profile-img";
//
//		s3Handler.uploadThumbnail(files, saveFileName, filePath, 100, 200);
//		List<String> imgPathList = s3Handler.upload(files, saveFileName, filePath);
//		return imgPathList.get(0);
//	}

	public void deleteRestaurantImg(String fileName) {
		String filePath = "pomondor/post-img";

		s3Handler.fileDelete(fileName, filePath);
	}

	public void deleteProfileImg(String fileName) {
		String filePath = "pomondor/profile-img";

		s3Handler.fileDelete(fileName, filePath);
	}

	public File convertUrlToFile(String imageUrl, String saveFileName) {


		File imageFile;
		try {
			imageFile = downloadImage(imageUrl, localPath + "/" + saveFileName);
			System.out.println("File path: " + imageFile.getAbsolutePath());
			return imageFile;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static File downloadImage(String imageUrl, String destinationPath) throws IOException {
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


}
