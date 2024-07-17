package hello.imagine.login.controller;

import hello.imagine.login.model.Member;
import hello.imagine.login.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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
    public boolean login(@RequestParam String id, @RequestParam String pw) {
        return memberService.login(id, pw);
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.findById(id);
    }
}