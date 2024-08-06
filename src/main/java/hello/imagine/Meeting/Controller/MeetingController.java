package hello.imagine.Meeting.Controller;

import hello.imagine.Meeting.model.Meeting;
import hello.imagine.Meeting.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    // 모든 모임 조회
    @GetMapping
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.findAllMeetings());
    }

    // 모임 생성
    @PostMapping
    public ResponseEntity<Meeting> createMeeting(@RequestBody Meeting meeting) {
        return ResponseEntity.ok(meetingService.createMeeting(meeting));
    }

    // 특정 모임 조회
    @GetMapping("/{id}")
    public ResponseEntity<Meeting> getMeetingById(@PathVariable Long id) {
        return meetingService.findMeetingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 제목으로 모임 검색
    @GetMapping("/search")
    public ResponseEntity<List<Meeting>> searchMeetingsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(meetingService.searchMeetingByTitle(title));
    }

    // 주변 모임 추천
    @GetMapping("/recommend")
    public ResponseEntity<List<Meeting>> recommendNearbyMeetings(@RequestParam double latitude, @RequestParam double longitude) {
        double distance = 5.0; // 5km 이내의 모임 검색
        return ResponseEntity.ok(meetingService.findNearbyMeetings(latitude, longitude, distance));
    }
}
