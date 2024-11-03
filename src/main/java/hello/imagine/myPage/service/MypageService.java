package hello.imagine.myPage.service;

import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.model.Post;
import hello.imagine.login.model.Member;
import hello.imagine.meeting.model.Meeting;
import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.Mypage_Meetinglist;

import java.util.List;
import java.util.Optional;

public interface MypageService {
    // Mypage를 id를 통해 조회
    Optional<Mypage> getMypageById(String id); // 회원의 Mypage 정보 조회

    // Mypage를 회원의 이메일, 닉네임, 포인트로 조회
    Mypage findByNickname(String nickname);
    Mypage findByPoints(int points);
    Mypage findByEmail(String email);

    // 소모임 내역 조회
    List<Mypage_Meetinglist> getMyMeetings(String id);

    // 소모임 탈퇴
    void leaveMeeting(Long meetingId, String id);

    // 소모임 개설자에 의한 모집 공고 수정
    Meeting updateMeeting(Long meetingId, Meeting updatedMeeting, String id);

    // 소모임 개설자에 의한 소모임 삭제
    void deleteMeeting(Long meetingId, String id);

    // 커뮤니티 내역 조회
    List<Post> getWrittenPosts(String id);
    List<Post> getLikedPosts(String id);
    List<ChatRoom> getParticipatingChatRooms(String id);

    // 마이페이지 저장 또는 업데이트
    Mypage save(Mypage mypage);

    // Member 객체를 통해 마이페이지 생성 또는 업데이트
    Mypage createOrUpdateMypageFromMember(Member member);

    // 계정 설정
    void updateNickname(String id, String newNickname);
    void updateEmail(String id, String newEmail);
    void updateEmergencyContact(String id, String newContact);

    // 좋아요 알림 설정
    void updateLikeNotificationSettings(String id, boolean likeNotification);
}


