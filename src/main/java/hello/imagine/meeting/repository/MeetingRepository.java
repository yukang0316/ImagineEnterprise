package hello.imagine.meeting.repository;

import hello.imagine.meeting.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query("SELECT p FROM Meeting p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Meeting> searchByTitleOrContentContainingIgnoreCase(String keyword); // 검색 쿼리 IgnoreCase(대, 소문자 구분 X)
}