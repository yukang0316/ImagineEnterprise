package hello.imagine.attendance.service;

import hello.imagine.attendance.model.Attendance;

import java.time.LocalDate;
import java.util.List;

// AttendanceService.java
public interface AttendanceService {
    void checkAttendance(Long memberId, LocalDate date) throws Exception;
    List<Attendance> getMonthlyAttendance(Long memberId, int year, int month) throws Exception;
    int getPoints(Long memberId) throws Exception;
}