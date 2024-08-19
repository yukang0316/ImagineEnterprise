package hello.imagine.meeting.service;

import hello.imagine.login.model.Member;
import hello.imagine.login.repository.MemberRepository;
import hello.imagine.meeting.model.Meeting;

import hello.imagine.meeting.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

@Autowired
private MeetingRepository meetingRepository;

@Autowired
private MemberRepository memberRepository;


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

    // 주변 모임 추천
    public List<Meeting> findNearbyMeetings(double latitude, double longitude, double radius) {
        double radiusInDegrees = radius / 111.0; // Rough conversion, valid for small distances

        return meetingRepository.findByLatitudeBetweenAndLongitudeBetween(
                latitude - radiusInDegrees, latitude + radiusInDegrees,
                longitude - radiusInDegrees, longitude + radiusInDegrees
        );
    }

    // Meeting join
    public void joinMeeting(Long meetingId, Long memberId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        // 현재 인원이 최대 인원보다 작은지 확인
        if (meeting.getMemberCount() >= meeting.getMaxMembers()) {
            throw new RuntimeException("The meeting is already full");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (meeting.getMember().contains(member)) {
            throw new RuntimeException("Member is already part of the meeting");
        }

        // 멤버를 추가하고 현재 인원을 증가시킴
        meeting.getMember().add(member);
        meeting.setMemberCount(meeting.getMemberCount() + 1);

        meetingRepository.save(meeting);
    }

}