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
    public Meeting createMeeting(Meeting meeting, Long memberId) {
        Member leader = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다"));
        meeting.setLeader(leader);
        meeting.getMember().add(leader);
        meeting.setMemberCount(1);
        return meetingRepository.save(meeting);
    }

    // 특정 모임 조회
    public Optional<Meeting> findMeetingById(Long meetingId) {
        return meetingRepository.findById(meetingId);
    }

    //제목 + 내용 검색
    public List<Meeting> searchMeetingByTitleOrContent(String keyword) {
        List<Meeting> meetings = meetingRepository.searchByTitleOrContentContainingIgnoreCase(keyword);
        if(meetings.isEmpty()) {
            throw new RuntimeException("검색결과가 없습니다");
        }
        return meetings;
    }

    // 소모임 입장
    public void joinMeeting(Long meetingId, Long memberId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다"));

        // 현재 인원이 최대 인원보다 작은지 확인
        if (meeting.getMemberCount() >= meeting.getMaxMembers()) {
            throw new RuntimeException("모임이 이미 꽉 찼습니다");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다"));

        if (meeting.getMember().contains(member)) {
            throw new RuntimeException("회원은 이미 모임에 참여하고 있습니다");
        }

        // 멤버를 추가하고 현재 인원을 증가시킴
        meeting.getMember().add(member);
        meeting.setMemberCount(meeting.getMemberCount() + 1);

        meetingRepository.save(meeting);
    }

    // 소모임 탈퇴
    public void leaveMeeting(Long meetingId, Long memberId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다"));

        // 멤버가 모임에 속해 있는지 확인
        if (meeting.getMember().contains(member)) {
            meeting.getMember().remove(member);
            member.getMeetings().remove(meeting);

            // 회원 수를 모임의 멤버 수로 설정
            meeting.setMemberCount(meeting.getMember().size());
            meetingRepository.save(meeting);
            memberRepository.save(member);
        } else {
            throw new RuntimeException("멤버가 모임에 속해 있지 않습니다");
        }
    }


    // 소모임 삭제
    public void deleteMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다"));

        meetingRepository.delete(meeting);
    }


    // 소모임 수정
    public void updateMeeting(Long id, Meeting updatedMeeting) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.setTitle(updatedMeeting.getTitle());
        meeting.setContent(updatedMeeting.getContent());
        meeting.setMaxMembers(updatedMeeting.getMaxMembers());
        meetingRepository.save(meeting);
    }
}
