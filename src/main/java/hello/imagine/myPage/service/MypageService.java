package hello.imagine.myPage.service;

import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.model.Post;
import hello.imagine.login.model.Member;
import hello.imagine.meeting.model.Meeting;
import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.MypageId;
import hello.imagine.myPage.entity.Mypage_Meetinglist;

import java.util.List;

public interface MypageService {
    Mypage getMypageByMemberId(Long memberId); // 회원의 Mypage 정보 조회
    Mypage findById(MypageId mypageId);
    Mypage findByNickname(String nickname);
    Mypage findByPoints(int points);
    Mypage findByEmail(String email);

    // 닉네임을 통한 구매내역 조회

    // 소모임 내역 조회
    List<Mypage_Meetinglist> getMyMeetings(Long memberId);

    // 소모임 탈퇴
    void leaveMeeting(Long meetingId, Long memberId);

    // 소모임 개설자에 의한 모집 공고 수정
    Meeting updateMeeting(Long meetingId, Meeting updatedMeeting, Long memberId);

    // 소모임 개설자에 의한 소모임 삭제
    void deleteMeeting(Long meetingId, Long memberId);


    // 커뮤니티 내역 조회
    List<Post> getWrittenPosts(Member member);
    List<Post> getLikedPosts(Member member);
    List<ChatRoom> getParticipatingChatRooms(Member member);

    // 마이페이지 저장 또는 업데이트
    Mypage save(Mypage mypage);

    // Member 객체를 통해 마이페이지 생성 또는 업데이트
    Mypage createOrUpdateMypageFromMember(Member member);

    // 계정 설정
    void updateNickname(Long memberId, String newNickname);
    void updateEmail(Long memberId, String newEmail);
    void updateEmergencyContact(Long memberId, String newContact);

    // 좋아요 알림 설정
    void updateLikeNotificationSettings(Long memberId, boolean likeNotification);

}
