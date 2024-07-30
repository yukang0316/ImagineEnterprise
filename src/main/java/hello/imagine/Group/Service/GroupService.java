package hello.imagine.Group.Service;

import hello.imagine.Group.Entity.Category;
import hello.imagine.Group.Entity.Group;
import hello.imagine.Group.Repository.CategoryRepository;
import hello.imagine.Group.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    // 소모임 만들기
    public Group createGroup(String categoryName, int members, String location, String description) {
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            category = new Category();
            category.setName(categoryName);
            category = categoryRepository.save(category);
        }

        Group group = new Group();
        group.setCategory(category);
        group.setMembers(members);
        group.setLocation(location);
        group.setDescription(description);

        return groupRepository.save(group);
    }

    public List<Group> findGroupsByName(String name) {
        return groupRepository.findByNameContaining(name);
    }
}