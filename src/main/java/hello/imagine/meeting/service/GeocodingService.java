package hello.imagine.meeting.service;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.json.JSONObject;

@Service
public class GeocodingService {

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public double[] getCoordinates(String address) throws JSONException {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + address;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {
            JSONObject json = new JSONObject(response.getBody());
            JSONObject addressInfo = json.getJSONArray("addresses").getJSONObject(0);
            double latitude = addressInfo.getDouble("y");
            double longitude = addressInfo.getDouble("x");
            return new double[]{latitude, longitude};
        }

        throw new RuntimeException("Failed to retrieve coordinates");
    }
}
