package hello.imagine.meeting.controller;

import hello.imagine.meeting.model.Meeting;
import hello.imagine.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    // 모든 소모임 조회
    @GetMapping
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.findAllMeetings());
    }

    // 소모임 생성
    @PostMapping("/create")
    public ResponseEntity<String> createMeeting(@RequestBody Meeting meeting, @RequestParam Long memberId) {
        try {
            meetingService.createMeeting(meeting, memberId);
            return ResponseEntity.ok("모임이 성공적으로 생성되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 특정 소모임 조회
    @GetMapping("/{meetingId}")
    public ResponseEntity<Meeting> getMeetingById(@PathVariable Long meetingId) {
        return meetingService.findMeetingById(meetingId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 제목 + 내용 소모임 검색
    @GetMapping("/search")
    public ResponseEntity<List<Meeting>> searchMeetings(@RequestParam String keyword) {
        List<Meeting> meetings = meetingService.searchMeetingByTitleOrContent(keyword);
        return new ResponseEntity<>(meetings, HttpStatus.OK); // 검색 결과가 있을 경우 200 OK 반환
    }


    // 소모임 입장
    @PostMapping("/{meetingId}/join")
    public ResponseEntity<String> joinMeeting(@PathVariable Long meetingId, @RequestBody Map<String, Long> request) {
        Long memberId = request.get("memberId");
        try {
            meetingService.joinMeeting(meetingId, memberId);
            return ResponseEntity.ok("회원이 성공적으로 가입했습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 소모임 탈퇴
    @DeleteMapping("/{meetingId}/leave")
    public ResponseEntity<String> leaveMeeting(@PathVariable Long meetingId, @RequestBody Map<String, Long> request) {
        Long memberId = request.get("memberId");
        try {
            meetingService.leaveMeeting(meetingId, memberId);
            return new ResponseEntity<>("회원이 성공적으로 탈퇴했습니다.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    // 소모임 삭제
    @DeleteMapping("/delete/{meetingId}")
    public ResponseEntity<String> deleteMeeting(@PathVariable Long meetingId) {

        try {
            meetingService.deleteMeeting(meetingId);
            return ResponseEntity.ok("소모임이 성공적으로 삭제되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMeeting(@PathVariable Long id, @RequestBody Meeting updatedMeeting) {
        meetingService.updateMeeting(id, updatedMeeting);
        return ResponseEntity.ok("Meeting updated successfully!");
    }

}

