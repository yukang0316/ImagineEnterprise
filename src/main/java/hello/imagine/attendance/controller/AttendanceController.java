package hello.imagine.attendance.controller;

import hello.imagine.attendance.model.Attendance;
import hello.imagine.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check")
    public String checkAttendance(@RequestParam String memberId) {
        try {
            Long id = Long.parseLong(memberId);
            attendanceService.checkAttendance(id, LocalDate.now());
            return "Attendance checked";
        } catch (NumberFormatException e) {
            return "Invalid member ID";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/{memberId}/monthly")
    public List<Attendance> getMonthlyAttendance(@PathVariable String memberId, @RequestParam YearMonth ym) {
        try {
            Long id = Long.parseLong(memberId);
            return attendanceService.getMonthlyAttendance(id, ym.getYear(), ym.getMonthValue());
        } catch (NumberFormatException e) {
            return null;
        } catch (Exception e) {
            // 로그를 남기거나 다른 방식으로 예외 처리할 수 있습니다.
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/points")
    public String getPoints(@RequestParam String memberId) {
        try {
            Long id = Long.parseLong(memberId);
            int points = attendanceService.getPoints(id);
            return "Points: " + points;
        } catch (NumberFormatException e) {
            return "Invalid member ID";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


}