package hello.imagine.Group.Repository;

import hello.imagine.Group.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
