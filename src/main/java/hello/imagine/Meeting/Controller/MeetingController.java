package hello.imagine.Group.Controller;

import hello.imagine.Group.Entity.Meeting;
import hello.imagine.Group.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
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

    // 제목으로 모임 검새
    @GetMapping("/search")
    public ResponseEntity<List<Meeting>> searchMeetingsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(meetingService.searchMeetingByTitle(title));
    }
}
