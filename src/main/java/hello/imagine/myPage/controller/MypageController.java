package hello.imagine.myPage.controller;

import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.model.Post;
import hello.imagine.login.model.Member;
import hello.imagine.login.repository.MemberRepository;
import hello.imagine.meeting.model.Meeting;
import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.Mypage_Meetinglist;
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

    @Autowired
    public MypageController(MypageService mypageService, MemberRepository memberRepository) {
        this.mypageService = mypageService;
        this.memberRepository = memberRepository;
    }

    // ID로 Mypage에서 닉네임 조회
    @GetMapping("/nickname/{id}")
    public ResponseEntity<String> getNicknameById(@PathVariable String id) {
        Mypage mypage = mypageService.getMypageById(id)
                .orElseThrow(() -> new RuntimeException("Mypage not found")); // Optional에서 Mypage 가져오기
        return ResponseEntity.ok(mypage.getNickname()); // 닉네임 반환
    }

    // ID로 포인트 조회
    @GetMapping("/points/{id}")
    public ResponseEntity<Integer> getPointsById(@PathVariable String id) {
        Mypage mypage = mypageService.getMypageById(id)
                .orElseThrow(() -> new RuntimeException("Mypage not found")); // Optional에서 Mypage 가져오기
        return ResponseEntity.ok(mypage.getPoints()); // 포인트 반환
    }


    // GET 요청으로 ID를 받아 Mypage를 업데이트
    @PostMapping("/updateFromId")
    public ResponseEntity<?> updateMypageFromId(@RequestParam String id) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        Mypage updatedMypage = mypageService.createOrUpdateMypageFromMember(member);
        return ResponseEntity.ok(updatedMypage);
    }

    // 소모임 내역 조회
    @GetMapping("/meetings/{id}")
    public ResponseEntity<List<Mypage_Meetinglist>> getMyMeetings(@PathVariable String id) {
        List<Mypage_Meetinglist> meetings = mypageService.getMyMeetings(id);
        return ResponseEntity.ok(meetings);
    }

    // 소모임 탈퇴
    @DeleteMapping("/meetings/leave/{meetingId}")
    public ResponseEntity<String> leaveMeeting(@PathVariable Long meetingId, @RequestParam String id) {
        mypageService.leaveMeeting(meetingId, id);
        return ResponseEntity.ok("모임에서 탈퇴하였습니다.");
    }

    // 소모임 개설자에 의한 모집 공고 수정
    @PutMapping("/meetings/update/{meetingId}")
    public ResponseEntity<Meeting> updateMeeting(@PathVariable Long meetingId,
                                                 @RequestBody Meeting updatedMeeting,
                                                 @RequestParam String id) {
        Meeting meeting = mypageService.updateMeeting(meetingId, updatedMeeting, id);
        return ResponseEntity.ok(meeting);
    }

    // 소모임 개설자에 의한 소모임 삭제
    @DeleteMapping("/meetings/delete/{meetingId}")
    public ResponseEntity<String> deleteMeeting(@PathVariable Long meetingId, @RequestParam String id) {
        mypageService.deleteMeeting(meetingId, id);
        return ResponseEntity.ok("모임이 삭제되었습니다.");
    }

    // 작성한 게시글 확인
    @GetMapping("/community/writtenposts/{id}")
    public ResponseEntity<List<Post>> getWrittenPosts(@PathVariable String id) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
        List<Post> posts = mypageService.getWrittenPosts(id);
        return ResponseEntity.ok(posts);
    }

    // 좋아요 표시한 게시글 확인
    @GetMapping("/community/likedposts/{id}")
    public ResponseEntity<List<Post>> getLikedPosts(@PathVariable String id) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
        List<Post> posts = mypageService.getLikedPosts(id);
        return ResponseEntity.ok(posts);
    }

    // 참여하고 있는 채팅방 확인
    @GetMapping("/community/chatrooms/{id}")
    public ResponseEntity<List<ChatRoom>> getParticipatingChatRooms(@PathVariable String id) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
        List<ChatRoom> chatRooms = mypageService.getParticipatingChatRooms(id);
        return ResponseEntity.ok(chatRooms);
    }

    // 닉네임 변경
    @PostMapping("/setting/nickname/{id}")
    public ResponseEntity<String> updateNickname(@RequestBody Map<String, Object> request) {
        String id = (String) request.get("id");
        String newNickname = (String) request.get("newNickname");

        try {
            mypageService.updateNickname(id, newNickname);
            return ResponseEntity.ok("Nickname updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update nickname: " + e.getMessage());
        }
    }

    // 이메일 조회
    @GetMapping("/email/{id}")
    public ResponseEntity<String> getEmail(@PathVariable String id) {
        Mypage mypage = mypageService.getMypageById(id)
                .orElseThrow(() -> new RuntimeException("Mypage not found")); // Optional에서 Mypage 가져오기
        return ResponseEntity.ok(mypage.getEmail()); // 이메일 반환
    }


    // 이메일 변경
    @PostMapping("/setting/email/{id}")
    public ResponseEntity<String> updateEmail(@PathVariable String id, @RequestBody Map<String, String> requestBody) {
        String newEmail = requestBody.get("newEmail"); // 요청 본문에서 newEmail 추출
        try {
            mypageService.updateEmail(id, newEmail);
            return ResponseEntity.ok("Email updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update email: " + e.getMessage());
        }
    }


    // 비상연락처 등록 또는 변경
    @PostMapping("/setting/emergencyContact/{id}")
    public ResponseEntity<String> updateEmergencyContact(@PathVariable String id, @RequestBody Map<String, String> request) {
        String newContact = request.get("newContact");
        mypageService.updateEmergencyContact(id, newContact);
        return ResponseEntity.ok("Emergency contact updated successfully.");
    }

    // 좋아요 알림 설정 업데이트
    @PutMapping("/notifications/like/{id}")
    public ResponseEntity<?> updateLikeNotificationSettings(
            @PathVariable String id,
            @RequestBody Map<String, Boolean> request) {

        boolean likeNotification = request.get("likeNotification");
        mypageService.updateLikeNotificationSettings(id, likeNotification);
        return ResponseEntity.ok().body("Like notification settings updated successfully.");
    }
}
