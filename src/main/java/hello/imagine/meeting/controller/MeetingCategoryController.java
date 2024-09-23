package hello.imagine.meeting.controller;

import hello.imagine.meeting.service.MeetingCategoryService;
import hello.imagine.meeting.model.MeetingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MeetingCategory")
public class MeetingCategoryController {

    @Autowired
    private MeetingCategoryService meetingCategoryService;

    // 모든 meeting_category 조회
    @GetMapping
    public ResponseEntity<List<MeetingCategory>> getAllCategories() {
        return ResponseEntity.ok(meetingCategoryService.getAllCategories());
    }

    // meeting_category 만들기
    @PostMapping("/create")
    public ResponseEntity<MeetingCategory> createMeetingCategory(@RequestBody MeetingCategory meetingCategory) {
        return ResponseEntity.ok(meetingCategoryService.createMeetingCategory(meetingCategory));
    }

}
