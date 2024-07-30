package hello.imagine.Group.Repository;

import hello.imagine.Group.Entity.Category;
import hello.imagine.Group.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
