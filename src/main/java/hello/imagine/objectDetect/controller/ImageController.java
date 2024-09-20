package hello.imagine.objectDetect.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImageController {

    @Value("${flask.server.url}") // Flask 서버 URL을 설정 파일에서 읽어옵니다.
    private String flaskServerUrl;

    @PostMapping("/upload")
    public ResponseEntity<?> handleImageUpload(@RequestParam("image") MultipartFile image) {
        try {
            // Flask 서버로 이미지를 전달하는 메서드 호출
            Map<String, Object> flaskResponse = sendImageToFlaskServer(image);

            // Flask 서버 응답을 클라이언트에게 전달
            return ResponseEntity.ok(flaskResponse);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the image.");
        }
    }

    private Map<String, Object> sendImageToFlaskServer(MultipartFile image) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Flask 서버로 보낼 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Flask 서버로 보낼 이미지 파일 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename(); // 파일 이름을 설정
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Flask 서버로 POST 요청 보내기
        ResponseEntity<Map> response = restTemplate.exchange(
                flaskServerUrl + "/detect", // Flask 서버의 엔드포인트 (예: /detect)
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        // Flask 서버로부터 받은 응답 반환
        return response.getBody();
    }
}