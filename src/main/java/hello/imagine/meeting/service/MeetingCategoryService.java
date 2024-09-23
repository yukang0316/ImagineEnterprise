package hello.imagine.meeting.service;

import hello.imagine.meeting.repository.MeetingCategoryRepository;
import hello.imagine.meeting.model.MeetingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingCategoryService {

    @Autowired
    private MeetingCategoryRepository meetingCategoryRepository;

    // 모든 카테고리 조회
    public List<MeetingCategory> getAllCategories() {
        return meetingCategoryRepository.findAll();
    }

    // 카테고리 만들기
    public MeetingCategory createMeetingCategory(MeetingCategory meetingCategory) {
        return meetingCategoryRepository.save(meetingCategory);
    }

}