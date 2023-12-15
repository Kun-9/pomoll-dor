package kun.pomondor.domain.util;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoAPI {

	public String getToken(String restApiCode, String redirectUri, String code) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 파라미터 세팅
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", restApiCode);
		body.add("redirect_uri", redirectUri);
		body.add("code", code);

		// 엔티티 생성
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

		// POST 요청 전송
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token", requestEntity, String.class);

		// 응답
		JSONObject jsonObject = new JSONObject(responseEntity.getBody());

		System.out.println(jsonObject);

		return jsonObject.getString("access_token");
	}

	public String getUserInfo(String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

//		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("Authorization", "Bearer " + token);

		// 엔티티 생성
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);

		// POST 요청 전송
		ResponseEntity<String> responseEntity = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, requestEntity, String.class);
//		System.out.println(responseEntity);

		// 응답
//		System.out.println(responseEntity.getBody());
		JSONObject jsonObject = new JSONObject(responseEntity.getBody());
		String email = jsonObject.getJSONObject("kakao_account").getString("email");
		String nickname = jsonObject.getJSONObject("properties").getString("nickname");

		System.out.println(email + " " + nickname);

//		return jsonObject.getString("access_token");
		return "OK";
	}
}
