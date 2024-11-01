package hello.imagine.login.dto;

public class LoginResponse {
    private String token;
    private Long memberId;

    public LoginResponse(String token, Long memberId) {
        this.token = token;
        this.memberId = memberId;
    }

    public String getToken() {
        return token;
    }

    public Long getMemberId() {
        return memberId;
    }
}
