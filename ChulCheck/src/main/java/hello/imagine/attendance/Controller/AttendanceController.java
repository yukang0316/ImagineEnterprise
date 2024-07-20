package hello.imagine.attendance.Controller;

import hello.imagine.attendance.Service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private final AttendService attendService;

    public AttendanceController(AttendService attendService) {
        this.attendService = attendService;
    }

    @PostMapping("/checkin")
    public ResponseEntity<String> checkIn(@RequestParam String Id) {

        boolean CheckInSuccessful = attendService.CheckIn(Id);
        if (CheckInSuccessful) {
            return ResponseEntity.ok("출석체크 성공");
        } else {
            return ResponseEntity.status(400).body("하루에 한번만 출석체크 할 수 있습니다.");
        }
    }

    @GetMapping("/point")
    public ResponseEntity<Integer> getPoint(@RequestParam String Id) {
        int points = attendService.getPoint(Id);
        return ResponseEntity.ok(points);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
}