package hello.imagine.attendance.service;

import hello.imagine.attendance.model.Attendance;

import java.time.LocalDate;
import java.util.List;

// AttendanceService.java
public interface AttendanceService {
    void checkAttendance(String memberId, LocalDate date) throws Exception;
    List<Attendance> getMonthlyAttendance(String memberId, int year, int month) throws Exception;
    int getPoints(String memberId) throws Exception;
}