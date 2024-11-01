package hello.imagine.meeting.controller;

import hello.imagine.meeting.DTO.MeetingDTO;
import hello.imagine.meeting.service.MeetingCategoryService;
import hello.imagine.meeting.model.MeetingCategory;
import hello.imagine.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MeetingCategory")
public class MeetingCategoryController {

    @Autowired
    private MeetingCategoryService meetingCategoryService;

    @Autowired
    private MeetingService meetingService;

    // 모든 meeting_category 조회
    @GetMapping
    public ResponseEntity<List<MeetingCategory>> getAllCategories() {
        return ResponseEntity.ok(meetingCategoryService.getAllCategories());
    }

    /*
    // meeting_category 만들기
    @PostMapping("/create")
    public ResponseEntity<MeetingCategory> createMeetingCategory(@RequestBody MeetingCategory meetingCategory) {
        return ResponseEntity.ok(meetingCategoryService.createMeetingCategory(meetingCategory));
    }

    */

    // 카테고리로 소모임 불러오기
    @GetMapping("/meetings/category/{categoryId}")
    public ResponseEntity<List<MeetingDTO>> getMeetingsByCategory(@PathVariable Long categoryId) {
        List<MeetingDTO> meetings = meetingService.getMeetingsByCategoryId(categoryId);
        if (meetings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(meetings);
    }

}
