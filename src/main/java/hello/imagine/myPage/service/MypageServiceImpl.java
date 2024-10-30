package hello.imagine.myPage.service;

import hello.imagine.login.model.Member;
import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.MypageId;
import hello.imagine.myPage.entity.Mypage_Communitylist;
import hello.imagine.myPage.entity.Mypage_Meetinglist;
import hello.imagine.myPage.repository.MyPageRepository;
import hello.imagine.myPage.repository.Mypage_CommunitylistRepository;
import hello.imagine.myPage.repository.Mypage_MeetinglistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MypageServiceImpl implements MypageService{
    private final MyPageRepository myPageRepository;
    private final OrderRepository orderRepository;
    private final MeetingRepository meetingRepository;
    private final PostRepository postRepository;
    private final Mypage_MeetinglistRepository mypage_meetinglistRepository;
    private final Mypage_CommunitylistRepository mypage_communitylistRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public MypageServiceImpl(MyPageRepository myPageRepository, OrderRepository orderRepository, MeetingRepository meetingRepository, PostRepository postRepository, Mypage_MeetinglistRepository mypage_meetinglistRepository, Mypage_CommunitylistRepository mypage_communitylistRepository, MemberRepository memberRepository) {
        this.myPageRepository = myPageRepository;
        this.orderRepository = orderRepository;
        this.meetingRepository = meetingRepository;
        this.postRepository = postRepository;
        this.mypage_meetinglistRepository = mypage_meetinglistRepository;
        this.mypage_communitylistRepository = mypage_communitylistRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Mypage getMypageByMemberId(Long memberId) {
        Optional<Mypage> mypageOptional = myPageRepository.findByMemberId(memberId);
        if (mypageOptional.isPresent()) {
            Mypage mypage = mypageOptional.get();
            Member member = mypage.getMember(); // Mypage에서 Member 객체 가져오기
            if (member != null) {
                // Member의 email을 Mypage에 설정
                mypage.setNickname(member.getNickname());
                mypage.setPoints(member.getPoints());
                mypage.setEmail(member.getEmail());
            }
            return mypage; // nickname, points, email이 포함된 Mypage 반환
        } else {
            throw new IllegalArgumentException("Mypage not found for memberId: " + memberId);
        }
    }


    @Override
    public Mypage findById(MypageId mypageId) {
        return myPageRepository.findById(mypageId).orElse(null);
    }

    @Override
    public Mypage findByNickname(String nickname) {
        return myPageRepository.findByNickname(nickname).orElse(null);
    }

    @Override
    public Mypage findByPoints(int points) {
        return myPageRepository.findByPoints(points).orElse(null);
    }

    @Override
    public Mypage findByEmail(String email) {
        return myPageRepository.findByEmail(email).orElse(null);
    }

    // 구매내역 조회

    // 소모임 내역 조회
    @Override
    public List<Mypage_Meetinglist> getMyMeetings(Long memberId) {
        Member member = new Member(); // Member 객체를 가져오는 로직 추가 필요
        member.setMemberId(memberId);
        return mypage_meetinglistRepository.findAllMeetingsByMember(member);
    }

    // 소모임 탈퇴

    @Override
    public void leaveMeeting(Long meetingId, Long memberId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다"));

        // 여기서 멤버가 모임에 속해 있는지 확인 후 탈퇴 처리
        if (meeting.getMember().stream().noneMatch(m -> m.getId().equals(memberId))) {
            throw new RuntimeException("회원이 모임에 속해 있지 않습니다");
        }

        // 멤버를 모임에서 제거하고 현재 인원을 감소시킴
        meeting.getMember().removeIf(m -> m.getId().equals(memberId));
        meeting.setMemberCount(meeting.getMember().size());
        meetingRepository.save(meeting);
    }

    // 소모임 개설자에 의한 모집 공고 수정
    @Override
    public Meeting updateMeeting(Long meetingId, Meeting updatedMeeting, Long memberId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다"));

        if (!meeting.getLeader().getId().equals(memberId)) {
            throw new RuntimeException("회원이 소모임 개설자가 아닙니다");
        }

        meeting.setTitle(updatedMeeting.getTitle());
        meeting.setContent(updatedMeeting.getContent());
        meeting.setMaxMembers(updatedMeeting.getMaxMembers());

        return meetingRepository.save(meeting);
    }

    // 소모임 개설자에 의한 소모임 삭제
    @Override
    public void deleteMeeting(Long meetingId, Long memberId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다"));

        if (!meeting.getLeader().getId().equals(memberId)) {
            throw new RuntimeException("회원이 소모임 개설자가 아닙니다");
        }

        meetingRepository.delete(meeting);
    }

    // 커뮤니티 내역 조회
    @Override
    public List<Post> getWrittenPosts(Member member) {
        return mypage_communitylistRepository.findByMember(member)
                .map(Mypage_Communitylist::getWrittenPosts)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @Override
    public List<Post> getLikedPosts(Member member) {
        return mypage_communitylistRepository.findByMember(member)
                .map(Mypage_Communitylist::getLikedPosts)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @Override
    public List<ChatRoom> getParticipatingChatRooms(Member member) {
        return mypage_communitylistRepository.findByMember(member)
                .map(Mypage_Communitylist::getChatRooms)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    // 마이페이지 저장 또는 업데이트
    @Override
    public Mypage save(Mypage mypage) {
        return myPageRepository.save(mypage);
    }

    // Member 객체를 통해 마이페이지 생성 또는 업데이트
    @Override
    @Transactional
    public Mypage createOrUpdateMypageFromMember(Member member) {
        MypageId mypageId = new MypageId(member.getMemberId());

        // Mypage 조회 또는 새로 생성
        Mypage mypage = myPageRepository.findById(mypageId).orElse(new Mypage(member));

        // 기존 Mypage 값 업데이트 (닉네임, 포인트, 이메일)
        if (mypage.getMember() != null) {
            mypage.setNickname(member.getNickname());
            mypage.setPoints(member.getPoints());
            mypage.setEmail(member.getEmail());
        }

        // 엔티티 저장
        return myPageRepository.save(mypage);
    }


    // 계정 설정
    @Override
    public void updateNickname(Long memberId, String newNickname) {
        Mypage mypage = getMypageByMemberId(memberId);
        mypage.updateNickname(newNickname);
        save(mypage);

        // Member 테이블에서 닉네임 업데이트
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        member.setNickname(newNickname);
        memberRepository.save(member);

    }

    @Override
    public void updateEmail(Long memberId, String newEmail) {
        Mypage mypage = getMypageByMemberId(memberId);
        mypage.updateEmail(newEmail);
        save(mypage);

        // Member 테이블에서 이메일 업데이트
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        member.setEmail(newEmail);
        memberRepository.save(member);
    }

    @Override
    public void updateEmergencyContact(Long memberId, String newContact) {
        Mypage mypage = getMypageByMemberId(memberId);
        mypage.updateEmergencyContact(newContact);  // 등록 또는 변경을 처리
        save(mypage);
    }

    // 좋아요 알림 설정
    @Override
    public void updateLikeNotificationSettings(Long memberId, boolean likeNotification) {
        Mypage mypage = getMypageByMemberId(memberId);
        mypage.setLikeNotificationEnabled(likeNotification);
        myPageRepository.save(mypage);

        // 사용자가 작성한 Post의 알림 설정을 반영
        List<Post> userPosts = postRepository.findByAuthor(mypage.getMember());
        for (Post post : userPosts) {
            post.setNotificationEnabled(likeNotification); // 좋아요 알림 설정 반영
            postRepository.save(post);
        }
    }

}
