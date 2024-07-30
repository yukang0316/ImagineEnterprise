package hello.imagine.Group.Service;

import hello.imagine.Group.Entity.Category;
import hello.imagine.Group.Entity.Group;
import hello.imagine.Group.Repository.CategoryRepository;
import hello.imagine.Group.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}

