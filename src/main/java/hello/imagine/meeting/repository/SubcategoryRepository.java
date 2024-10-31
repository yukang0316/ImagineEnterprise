package hello.imagine.meeting.repository;

import hello.imagine.meeting.model.MeetingCategory;
import hello.imagine.meeting.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    boolean existsByNameAndParentCategoryId(String name, Long parentCategoryId);
}
