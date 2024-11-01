package hello.imagine.meeting.controller;

import hello.imagine.meeting.DTO.MeetingDTO;
import hello.imagine.meeting.model.MeetingCategory;
import hello.imagine.meeting.model.Subcategory;
import hello.imagine.meeting.service.MeetingService;
import hello.imagine.meeting.service.SubcategoryService;
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

    @Autowired
    private SubcategoryService subcategoryService;


    // 하위 카테고리에 속한 소모임 불러오기
    @GetMapping("/meetings/subcategory/{subcategoryId}")
    public ResponseEntity<List<MeetingDTO>> getMeetingsBySubcategoryId(@PathVariable Long subcategoryId) {
        List<MeetingDTO> meetings = meetingService.getMeetingsBySubcategoryId(subcategoryId);
        return ResponseEntity.ok(meetings);
    }

    // 하위 카테고리 전체
    // ID:1 SubCategory:1-6
    // ID:2 SubCategory:8-14
    // ID:3 SubCategory:15-21
    // ID:4 SubCategory:22-27

    // 하위 카테고리 전체 조회
    @GetMapping("/meetings/subcategory")
    public ResponseEntity<List<Subcategory>> getAllSubCategories() {
        return ResponseEntity.ok(subcategoryService.findAll());
    }

    @GetMapping("/meetings/categoryId/{categoryId}")
    public List<Subcategory> getSubcategoriesByParentId(@PathVariable Long categoryId) {
        return subcategoryService.findByParentCategoryId(categoryId);
    }
    
}
