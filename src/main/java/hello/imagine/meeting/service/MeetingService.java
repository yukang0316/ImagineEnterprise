package hello.imagine.meeting.service;

import hello.imagine.login.model.Member;
import hello.imagine.login.repository.MemberRepository;
import hello.imagine.meeting.DTO.MeetingDTO;
import hello.imagine.meeting.model.Meeting;

import hello.imagine.meeting.model.MeetingCategory;
import hello.imagine.meeting.model.Subcategory;
import hello.imagine.meeting.repository.MeetingCategoryRepository;
import hello.imagine.meeting.repository.MeetingRepository;
import hello.imagine.meeting.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MeetingService {


    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MeetingCategoryRepository meetingCategoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    // 모든 모임 조회
    public List<Meeting> findAllMeetings() {
        return meetingRepository.findAll();
    }



    // 모임 생성
    public Meeting createMeeting(Meeting meeting, Long memberId) {

        // 상위 카테고리 존재 확인
        MeetingCategory meetingCategory = meetingCategoryRepository.findById(meeting.getMeetingCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다: ID = " + meeting.getMeetingCategory().getId()));
        meeting.setMeetingCategory(meetingCategory); // 이미 존재하는 카테고리 설정

        // 하위 카테고리 존재 확인
        Subcategory subcategory = subcategoryRepository.findById(meeting.getSubcategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("하위 카테고리가 존재하지 않습니다: ID = " + meeting.getSubcategory().getId()));


        // 하위 카테고리의 상위 카테고리 ID 확인
        Long parentId = subcategory.getParentCategory().getId(); // Long 타입으로 저장
        if (!parentId.equals(meetingCategory.getId())) { // 비교
            throw new IllegalArgumentException("하위 카테고리가 상위 카테고리와 일치하지 않습니다.");
        }

        meeting.setSubcategory(subcategory); // 하위 카테고리 설정


        meeting.setSubcategory(subcategory); // 하위 카테고리 설정

        // 모임장 존재 확인
        Member leader = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다"));


        // 소모임 설정
        meeting.setLeader(leader);
        meeting.getMembers().add(leader);
        meeting.setMemberCount(1);
        return meetingRepository.save(meeting);
    }



    // 특정 소모임 조회
    public List<MeetingDTO> findMeetingById(Long meetingId) {
        Optional<Meeting> meetings = meetingRepository.findById(meetingId);
        return meetings.stream()
                .map(meeting -> new MeetingDTO (
                        meeting.getTitle(),
                        meeting.getIntroduction(),
                        meeting.getContent(),
                        meeting.getMemberCount(),
                        meeting.getMeetingCategory().getId(),
                        meeting.getSubcategory().getId()
                ))
                .collect(Collectors.toList());
    }



    // 카테고리ID로 소모임 불러오기
    public List<MeetingDTO> getMeetingsByCategoryId(Long categoryId) {
        List<Meeting> meetings = meetingRepository.findByMeetingCategoryId(categoryId);
        return meetings.stream()
                .map(meeting -> new MeetingDTO(
                        meeting.getTitle(),
                        meeting.getIntroduction(),
                        meeting.getContent(),
                        meeting.getMemberCount(),
                        meeting.getMeetingCategory().getId(),
                        meeting.getSubcategory().getId()
                ))
                .collect(Collectors.toList());
    }



    // 하위 카테고리ID로 소모임 불러오기
    public List<MeetingDTO> getMeetingsBySubcategoryId(Long subcategoryId) {
        List<Meeting> meetings = meetingRepository.findBySubcategoryId(subcategoryId);
        return meetings.stream()
                .map(meeting -> new MeetingDTO(
                        meeting.getTitle(),
                        meeting.getIntroduction(),
                        meeting.getContent(),
                        meeting.getMemberCount(),
                        meeting.getSubcategory().getId(),
                        meeting.getSubcategory().getId()// 하위 카테고리 ID
                ))
                .collect(Collectors.toList());
    }



    //제목 + 내용 검색
    public List<MeetingDTO> searchMeetingByTitleOrContent(String keyword) {
        List<Meeting> meetings = meetingRepository.searchByTitleOrContentContainingIgnoreCase(keyword);
        if(meetings.isEmpty()) {
            throw new RuntimeException("검색결과가 없습니다");
        }
        return meetings.stream()
                .map(meeting -> new MeetingDTO (
                        meeting.getTitle(),
                        meeting.getIntroduction(),
                        meeting.getContent(),
                        meeting.getMemberCount(),
                        meeting.getMeetingCategory().getId(),
                        meeting.getSubcategory().getId()
                ))
                .collect(Collectors.toList());
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

        if (meeting.getMembers().contains(member)) {
            throw new RuntimeException("회원은 이미 모임에 참여하고 있습니다");
        }

        // 멤버를 추가하고 현재 인원을 증가시킴
        meeting.getMembers().add(member);
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
        if (meeting.getMembers().contains(member)) {
            meeting.getMembers().remove(member);
            member.getMeetings().remove(meeting);

            // 회원 수를 모임의 멤버 수로 설정
            meeting.setMemberCount(meeting.getMembers().size());
            meetingRepository.save(meeting);
            memberRepository.save(member);
        } else {
            throw new RuntimeException("멤버가 모임에 속해 있지 않습니다");
        }
    }


    // 소모임 삭제
    public void deleteMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."));

        meetingRepository.delete(meeting);
    }



    // 소모임 수정
    public void updateMeeting(Long id, Meeting updatedMeeting) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."));
        meeting.setTitle(updatedMeeting.getTitle());
        meeting.setContent(updatedMeeting.getContent());
        meeting.setMaxMembers(updatedMeeting.getMaxMembers());
        meetingRepository.save(meeting);
    }




}
