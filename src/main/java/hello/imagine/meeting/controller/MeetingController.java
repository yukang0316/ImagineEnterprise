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

    // 모든 모임 조회 GET 방식
    @GetMapping
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.findAllMeetings());
    }

    // 모임 생성 POST 방식
    @PostMapping("/create")
    public ResponseEntity<Meeting> createMeeting(@RequestBody Meeting meeting) {
        return ResponseEntity.ok(meetingService.createMeeting(meeting));
    }

    // 특정 모임 조회 GET 방식
    @GetMapping("/{id}")
    public ResponseEntity<Meeting> getMeetingById(@PathVariable Long id) {
        return meetingService.findMeetingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 제목으로 모임 검색 GET 방식
    @GetMapping("/search")
    public ResponseEntity<List<Meeting>> searchMeetingsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(meetingService.searchMeetingByTitle(title));
    }

    // 근처 모임 추천(5km) GET 방식
    @GetMapping("/nearby")
    public ResponseEntity<List<Meeting>> getNearbyMeetings(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") double radius) {
        List<Meeting> meetings = meetingService.findNearbyMeetings(latitude, longitude, radius);
        return ResponseEntity.ok(meetings);
    }

    // 소모임 join POST 방식
    @PostMapping("/{meetingId}/join")
    public ResponseEntity<String> joinMeeting(@PathVariable Long meetingId,  @RequestParam Long memberId) {
        try {
            meetingService.joinMeeting(meetingId, memberId);
            return ResponseEntity.ok("Member joined successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
