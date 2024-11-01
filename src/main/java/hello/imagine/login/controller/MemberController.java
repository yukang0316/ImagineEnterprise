package hello.imagine.login.controller;

import hello.imagine.login.dto.LoginRequestDTO;
import hello.imagine.login.model.Member;
import hello.imagine.login.service.MemberService;
import hello.imagine.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService; // UserDetailsService 주입

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
            this.memberService = memberService;
            this.jwtUtil = jwtUtil;
            this.userDetailsService = userDetailsService;
        }

    @PostMapping("/register")
    public String register(@RequestBody Member member) {
        try {
            memberService.register(member);
            return "Registration successful";
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
            // UserDetails 객체를 가져옴
            UserDetails userDetails = userDetailsService.loadUserByUsername(id);
            // UserDetails 객체를 사용하여 JWT 토큰 생성
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(token); // JWT 토큰을 클라이언트에 반환
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable String id, @RequestHeader("Authorization") String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        // 토큰 유효성 검증: token과 userDetails를 전달
        if (jwtUtil.validateToken(token, userDetails)) {
            return ResponseEntity.ok(memberService.findById(id));
        } else {
            return ResponseEntity.status(401).build();
        }
    }


}