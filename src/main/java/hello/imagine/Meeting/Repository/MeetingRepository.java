package hello.imagine.Group.Repository;

import hello.imagine.Group.Entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByTitleContainingIgnoreCase(String title); // 검색 쿼리 IgnoreCase(대, 소문자 구분 X)
}