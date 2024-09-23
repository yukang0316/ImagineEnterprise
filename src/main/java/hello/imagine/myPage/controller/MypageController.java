package hello.imagine.myPage.controller;

import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.model.Post;
import hello.imagine.login.model.Member;
import hello.imagine.login.repository.MemberRepository;
import hello.imagine.meeting.model.Meeting;
import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.MypageId;
import hello.imagine.myPage.entity.Mypage_Meetinglist;
import hello.imagine.myPage.repository.MyPageRepository;
import hello.imagine.myPage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;
    private final MemberRepository memberRepository;
    private final MyPageRepository myPageRepository;

    @Autowired
    public MypageController(MypageService mypageService, MemberRepository memberRepository, MyPageRepository myPageRepository) {
        this.mypageService = mypageService;
        this.memberRepository = memberRepository;
        this.myPageRepository = myPageRepository;
    }

    // Member ID로 Mypage에서 닉네임 조회
    @GetMapping("/nickname/{memberId}")
    public ResponseEntity<String> getNicknameByMemberId(@PathVariable Long memberId) {
        // memberId로 Mypage 엔티티를 조회
        Mypage mypage = mypageService.findById(new MypageId(memberId));
        if (mypage == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mypage.getNickname());
    }

    // Member ID로 포인트 조회
    @GetMapping("/points/{memberId}")
    public ResponseEntity<Integer> getPointsByMemberId(@PathVariable Long memberId) {
        // Member ID로 Member 엔티티를 조회
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }

        // Member의 닉네임으로 Mypage 조회 후 포인트 반환
        Mypage mypage = mypageService.findByNickname(member.getNickname());
        return ResponseEntity.ok(mypage.getPoints());
    }

    // 닉네임으로 구매내역 조회

    // 소모임 내역 조회
    @GetMapping("/meetings/{memberId}")
    public ResponseEntity<List<Mypage_Meetinglist>> getMyMeetings(@PathVariable Long memberId) {
        List<Mypage_Meetinglist> meetings = mypageService.getMyMeetings(memberId);
        return ResponseEntity.ok(meetings);
    }

    // 소모임 탈퇴
    @DeleteMapping("/meetings/leave/{meetingId}")
    public ResponseEntity<String> leaveMeeting(@PathVariable Long meetingId, @RequestParam Long memberId) {
        mypageService.leaveMeeting(meetingId, memberId);
        return ResponseEntity.ok("모임에서 탈퇴하였습니다.");
    }

    // 소모임 개설자에 의한 모집 공고 수정
    @PutMapping("/meetings/update/{meetingId}")
    public ResponseEntity<Meeting> updateMeeting(@PathVariable Long meetingId,
                                                 @RequestBody Meeting updatedMeeting,
                                                 @RequestParam Long memberId) {
        Meeting meeting = mypageService.updateMeeting(meetingId, updatedMeeting, memberId);
        return ResponseEntity.ok(meeting);
    }

    // 소모임 개설자에 의한 소모임 삭제
    @DeleteMapping("/meetings/delete/{meetingId}")
    public ResponseEntity<String> deleteMeeting(@PathVariable Long meetingId, @RequestParam Long memberId) {
        mypageService.deleteMeeting(meetingId, memberId);
        return ResponseEntity.ok("모임이 삭제되었습니다.");
    }


    // 작성한 게시글 확인
    @GetMapping("/community/writtenposts/{memberId}")
    public List<Post> getWrittenPosts(@PathVariable Long memberId) {
        // memberId로 Member 객체를 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 작성한 게시글 목록 반환
        return mypageService.getWrittenPosts(member);
    }

    // 좋아요 표시한 게시글 확인
    @GetMapping("/community/likedposts/{memberId}")
    public List<Post> getLikedPosts(@PathVariable Long memberId) {
        // memberId로 Member 객체를 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 좋아요 표시한 게시글 목록 반환
        return mypageService.getLikedPosts(member);
    }

    // 참여하고 있는 채팅방 확인
    @GetMapping("/community/chatrooms/{memberId}")
    public List<ChatRoom> getParticipatingChatRooms(@PathVariable Long memberId) {
        // memberId로 Member 객체를 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 참여하고 있는 채팅방 목록 반환
        return mypageService.getParticipatingChatRooms(member);
    }


    // 닉네임 변경
    @PostMapping("/setting/nickname/{memberId}")
    public ResponseEntity<String> updateNickname(@RequestBody Map<String, Object> request) {
        Long memberId = ((Number) request.get("memberId")).longValue();
        String newNickname = (String) request.get("newNickname");

        try {
            mypageService.updateNickname(memberId, newNickname);
            return ResponseEntity.ok("Nickname updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update nickname: " + e.getMessage());
        }
    }

    // 이메일 조회
    @GetMapping("/email/{memberId}")
    public ResponseEntity<String> getEmail(@PathVariable Long memberId) {
        Mypage mypage = mypageService.getMypageByMemberId(memberId);
        String email = mypage.getEmail();
        return ResponseEntity.ok(email);
    }

    // 이메일 변경
    @PostMapping("/setting/email/{memberId}")
    public ResponseEntity<String> updateEmail(@RequestParam Long memberId, @RequestParam String newEmail) {
        try {
            // 이메일을 변경하는 서비스 호출
            mypageService.updateEmail(memberId, newEmail);
            return ResponseEntity.ok("Email updated successfully.");
        } catch (Exception e) {
            // 예외 처리: 이메일 변경에 실패했을 때
            return ResponseEntity.status(500).body("Failed to update email: " + e.getMessage());
        }
    }

    // 비상연락처 등록 또는 변경
    @PostMapping("/setting/emergencyContact/{memberId}")
    public ResponseEntity<String> updateEmergencyContact(@PathVariable Long memberId, @RequestBody Map<String, String> request) {
        String newContact = request.get("newContact");

        mypageService.updateEmergencyContact(memberId, newContact);
        return ResponseEntity.ok("Emergency contact updated successfully.");
    }

    // 좋아요 알림 설정 업데이트
    @PutMapping("/notifications/like/{memberId}")
    public ResponseEntity<?> updateLikeNotificationSettings(
            @PathVariable Long memberId,
            @RequestBody Map<String, Boolean> request) {

        boolean likeNotification = request.get("likeNotification");
        mypageService.updateLikeNotificationSettings(memberId, likeNotification);
        return ResponseEntity.ok().body("Like notification settings updated successfully.");
    }
}
