package hello.imagine.meeting.controller;

import hello.imagine.meeting.DTO.MeetingDTO;
import hello.imagine.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class SubcategoryController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/meetings/subcategory/{subcategoryId}")
    public ResponseEntity<List<MeetingDTO>> getMeetingsBySubcategoryId(@PathVariable Long subcategoryId) {
        List<MeetingDTO> meetings = meetingService.getMeetingsBySubcategoryId(subcategoryId);
        return ResponseEntity.ok(meetings);
    }
}
