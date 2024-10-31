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

}