package hello.imagine.login.controller;

import hello.imagine.login.dto.LoginResponse;
import hello.imagine.login.model.Member;
import hello.imagine.login.service.MemberService;
import hello.imagine.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            return "Registration successful";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestParam String id, @RequestParam String pw) {
        // 로그인 확인
        if (memberService.login(id, pw)) {
            // 로그인 성공 시 토큰 생성
            String token = jwtUtil.createToken(id);  // 'id'를 주제로 토큰 생성
            // userId로 memberId 조회
            Long memberId = memberService.findMemberIdByUserId(id);

            // token과 memberId를 프론트엔드에 반환
            return ResponseEntity.ok(new LoginResponse(token, memberId));
        } else {
            // 로그인 실패 시 HTTP 401 Unauthorized 상태 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }
    }





    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.findById(id);
    }

}