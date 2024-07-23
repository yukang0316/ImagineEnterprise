package hello.imagine.community.service;

import hello.imagine.community.model.Category;
import hello.imagine.community.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category findByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        return category.orElse(null);
    }
}