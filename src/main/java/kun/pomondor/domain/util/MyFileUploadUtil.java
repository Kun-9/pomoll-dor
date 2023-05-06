package kun.pomondor.domain.util;

import kun.pomondor.web.controller.s3.S3Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MyFileUploadUtil {

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

	public String saveImgToS3(MultipartFile[] files, String saveFileName) throws Exception {
		List<String> imgPathList = s3Handler.upload(files, saveFileName);
		String path = imgPathList.get(0);
		return path;
	}
}
