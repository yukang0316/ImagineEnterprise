package hello.imagine;

import hello.imagine.login.model.Member;
import hello.imagine.login.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ImagineApplication {

	@Autowired
	private MemberService memberService;

	public static void main(String[] args) {
		SpringApplication.run(ImagineApplication.class, args);
		System.out.println("애플리케이션 실행됨");
	}

//	@PostConstruct
//	public void init() {
//		System.out.println("Init 메소드 실행됨");
//		if (memberService == null) {
//			System.out.println("memberService 주입 실패!");
//			return;
//		}
//		try {
//			Member member = new Member("hennry", "henn123", "1990-01-03", "password12345", "hennry@example.com", "henn123");
//			memberService.register(member);
//			System.out.println("회원등록 성공!");
//		} catch (Exception e) {
//			System.out.println("Error: " + e.getMessage());
//			e.printStackTrace();
//		}
//	}


}