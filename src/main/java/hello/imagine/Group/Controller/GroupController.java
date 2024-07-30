package hello.imagine.Group.Controller;

import hello.imagine.Group.Entity.Group;
import hello.imagine.Group.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.saveGroup(group);
    }

    @PostMapping("/create")
    public Group createGroup(
            @RequestParam(value = "categoryName", required = true) String categoryName,
            @RequestParam(value = "members", required = true) int members,
            @RequestParam(value = "location", required = true) String location,
            @RequestParam(value = "description", required = true) String description) {
        return groupService.createGroup(categoryName, members, location, description);
    }

    @GetMapping("/search")
    public List<Group> findGroupsByName(@RequestParam String name) {
        return groupService.findGroupsByName(name);
    }
}