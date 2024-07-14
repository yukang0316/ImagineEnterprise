package hello.imagine.login;

import hello.imagine.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);
        Member member = new Member("김유강", "yukang0316", "000316", "1111",
                "yuakng0316@naver.com", "aaaa");
        memberService.join(member);

        boolean loginResult = memberService.loginCheck(member);
        System.out.println(loginResult);
    }

}
