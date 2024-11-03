package hello.imagine.attendance.controller;

import hello.imagine.attendance.model.Attendance;
import hello.imagine.attendance.service.AttendanceServiceImpl;
import hello.imagine.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceServiceImpl attendanceServiceImpl;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AttendanceController(AttendanceServiceImpl attendanceServiceImpl, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.attendanceServiceImpl = attendanceServiceImpl;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // 출석 체크
    @PostMapping("/check")
    public ResponseEntity<String> checkAttendance(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // "Bearer " 제거
        String memberId = jwtUtil.extractUserId(token); // user_id를 반환

        try {
            LocalDate today = LocalDate.now();
            attendanceServiceImpl.checkAttendance(memberId, today);

            // 출석 체크가 성공적으로 완료된 경우
            return ResponseEntity.ok("출석이 확인되었습니다.");

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("확인되지 않은 memberId 입니다");
        } catch (Exception e) {
            // 이미 출석 체크가 완료된 경우, 200 OK와 함께 메시지를 반환
            return ResponseEntity.ok(e.getMessage()); // "출석 체크가 이미 확인되었습니다."
        }
    }

    // 월별 출석 기록 조회
    @GetMapping("/monthly")
    public ResponseEntity<List<Attendance>> getMonthlyAttendance(HttpServletRequest request,
                                                                 @RequestParam int year,
                                                                 @RequestParam int month) {
        String token = request.getHeader("Authorization").substring(7); // "Bearer " 제거
        String memberId = jwtUtil.extractUserId(token); // user_id를 반환


        try {
            List<Attendance> attendanceList = attendanceServiceImpl.getMonthlyAttendance(memberId, year, month);
            return ResponseEntity.ok(attendanceList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 포인트 조회
    @GetMapping("/points")
    public ResponseEntity<Integer> getPoints(@RequestHeader("Authorization") String token) {
        String userId = jwtUtil.extractUserId(token.substring(7)); // "Bearer " 제거
        String memberId = userId; //

        try {
            int points = attendanceServiceImpl.getPoints(memberId);
            return ResponseEntity.ok(points);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
