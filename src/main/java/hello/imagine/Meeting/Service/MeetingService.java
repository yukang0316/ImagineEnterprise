package hello.imagine.Group.Service;

import hello.imagine.Group.Entity.Meeting;
import hello.imagine.Group.Repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    // 모든 모임 조회
    public List<Meeting> findAllMeetings() {
        return meetingRepository.findAll();
    }

    // 모임 생성
    public Meeting createMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    // 특정 모임 조회
    public Optional<Meeting> findMeetingById(Long id) {
        return meetingRepository.findById(id);
    }

    //제목 모임 검색
    public List<Meeting> searchMeetingByTitle(String title) {
        return meetingRepository.findByTitleContainingIgnoreCase(title);
    }
}