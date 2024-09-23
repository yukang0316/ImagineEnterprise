package hello.imagine.myPage.controller;

import hello.imagine.login.model.Member;
import hello.imagine.login.repository.MemberRepository;
import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final MemberRepository memberRepository;

    @Autowired
    public MypageController(MypageService mypageService, MemberRepository memberRepository) {
        this.mypageService = mypageService;
        this.memberRepository = memberRepository;
    }

    // GET 요청으로 Member ID를 받아 Mypage를 업데이트
    @PostMapping("/updateFromMember")
    public ResponseEntity<?> updateMypageFromMember(@RequestParam Long memberId) {
        // Member ID로 Member 엔티티를 찾기
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member == null) {
            // Member가 존재하지 않을 경우 오류 응답 반환
            return ResponseEntity.notFound().build();
        }

        // Mypage 엔티티 생성 또는 업데이트
        Mypage updatedMypage = mypageService.createOrUpdateMypageFromMember(member);

        // 성공적인 응답 반환
        return ResponseEntity.ok(updatedMypage);
    }

    // GET 요청으로 Nickname을 받아 Mypage를 업데이트
    @PostMapping("/updateFromNickname")
    public ResponseEntity<?> updateMypageFromNickname(@RequestParam String nickname) {
        // Nickname으로 Member 엔티티를 찾기
        Member member = memberRepository.findByNickname(nickname).orElse(null);

        if (member == null) {
            // Member가 존재하지 않을 경우 오류 응답 반환
            return ResponseEntity.notFound().build();
        }

        // Mypage 엔티티 생성 또는 업데이트
        Mypage updatedMypage = mypageService.createOrUpdateMypageFromMember(member);

        // 성공적인 응답 반환
        return ResponseEntity.ok(updatedMypage);
    }

}