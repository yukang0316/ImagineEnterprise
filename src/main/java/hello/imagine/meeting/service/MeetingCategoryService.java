package hello.imagine.meeting.service;

import hello.imagine.meeting.model.MeetingCategory;
import hello.imagine.meeting.repository.MeetingCategoryRepository;
import hello.imagine.meeting.model.MeetingCategory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingCategoryService {

    @Autowired
    private MeetingCategoryRepository meetingCategoryRepository;


    private final List<MeetingCategory> fixedCategory = Arrays.asList(
            new MeetingCategory(1L, "운동"),
            new MeetingCategory(2L, "친목"),
            new MeetingCategory(3L, "동창회"),
            new MeetingCategory(4L, "음식"),
            new MeetingCategory(5L, "스터디"),
            new MeetingCategory(6L, "문화")
    );

    // 애플리케이션 시작 시 실행되는 메서드
    @PostConstruct
    public void init() {
        // 고정된 카테고리를 데이터베이스에 저장합니다.
        for (MeetingCategory mainCategory : fixedCategory) {
            if (!meetingCategoryRepository.existsById(mainCategory.getId())) {
                meetingCategoryRepository.save(mainCategory);
            }
        }
    }




    // 모든 카테고리 조회
    public List<MeetingCategory> getAllCategories() {
        return meetingCategoryRepository.findAll();
    }

    // 카테고리 만들기
    public MeetingCategory createMeetingCategory(MeetingCategory category) {
        return meetingCategoryRepository.save(category);
    }

}
