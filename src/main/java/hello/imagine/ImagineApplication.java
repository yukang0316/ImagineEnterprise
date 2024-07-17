package hello.imagine;

import hello.imagine.login.model.Member;
import hello.imagine.login.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ImagineApplication {

	@Autowired
	private MemberService memberService;

	public static void main(String[] args) {
		SpringApplication.run(ImagineApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			try {
				Member member = new Member("John Doe", "johndoe", "1990-01-01", "password", "johndoe@example.com", "johndoe123");
				memberService.register(member);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		};
	}
}