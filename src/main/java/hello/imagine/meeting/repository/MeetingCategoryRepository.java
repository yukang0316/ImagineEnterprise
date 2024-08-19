package hello.imagine.meeting.repository;

import hello.imagine.meeting.model.MeetingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingCategoryRepository extends JpaRepository<MeetingCategory, Long> {
    Optional<MeetingCategory> findByName(String name);
}
