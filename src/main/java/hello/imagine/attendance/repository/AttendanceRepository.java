package hello.imagine.attendance.repository;

import hello.imagine.attendance.model.Attendance;
import hello.imagine.login.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByMemberAndDate(Member member, LocalDate date);

    List<Attendance> findByMemberAndDateBetween(Member member, LocalDate startDate, LocalDate endDate);
}
