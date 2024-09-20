package hello.imagine.login.controller;

import hello.imagine.login.dto.LoginRequestDTO;
import hello.imagine.login.model.Member;
import hello.imagine.login.service.MemberService;
import hello.imagine.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public String register(@RequestBody Member member) {
        try {
            memberService.register(member);
            return "회원가입이 완료되었습니다!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        String id = loginRequest.getId();
        String pw = loginRequest.getPw();

        boolean loginSuccess = memberService.login(id, pw);
        if (loginSuccess) {
            String token = jwtUtil.generateToken(id);
            return ResponseEntity.ok(token); // JWT 토큰을 클라이언트에 반환
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable String id, @RequestHeader("Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok(memberService.findById(id));
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}