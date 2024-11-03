package hello.imagine.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String id;
    private String pw;

    // 기본 생성자
    public LoginRequestDTO() {}
}