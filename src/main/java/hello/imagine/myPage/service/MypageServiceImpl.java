package hello.imagine.myPage.service;

import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.model.Post;
import hello.imagine.community.repository.PostRepository;
import hello.imagine.login.model.Member;
import hello.imagine.login.repository.MemberRepository;
import hello.imagine.meeting.model.Meeting;
import hello.imagine.meeting.repository.MeetingRepository;
import hello.imagine.myPage.entity.Mypage;
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
public class MypageServiceImpl implements MypageService {
    private final MyPageRepository myPageRepository;
    private final MeetingRepository meetingRepository;
    private final PostRepository postRepository;
    private final Mypage_MeetinglistRepository mypage_meetinglistRepository;
    private final Mypage_CommunitylistRepository mypage_communitylistRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public MypageServiceImpl(MyPageRepository myPageRepository, MeetingRepository meetingRepository,
                             PostRepository postRepository, Mypage_MeetinglistRepository mypage_meetinglistRepository,
                             Mypage_CommunitylistRepository mypage_communitylistRepository, MemberRepository memberRepository) {
        this.myPageRepository = myPageRepository;
        this.meetingRepository = meetingRepository;
        this.postRepository = postRepository;
        this.mypage_meetinglistRepository = mypage_meetinglistRepository;
        this.mypage_communitylistRepository = mypage_communitylistRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Optional<Mypage> getMypageById(String id) {
        // Member 조회
        Optional<Member> memberOptional = memberRepository.findById(id);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // Mypage 조회 (id를 사용하여)
            Optional<Mypage> mypageOptional = myPageRepository.findById(id);
            if (mypageOptional.isPresent()) {
                Mypage mypage = mypageOptional.get();
                // Member의 값으로 Mypage 필드 업데이트
                mypage.setMember(member);
                mypage.setNickname(member.getNickname());
                mypage.setPoints(member.getPoints());
                mypage.setEmail(member.getEmail());

                // Mypage 저장 (업데이트 반영)
                myPageRepository.save(mypage);

                return Optional.of(mypage); // 업데이트된 Mypage 반환
            }
        }
        return Optional.empty(); // Member 또는 Mypage가 존재하지 않음
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

    @Override
    public List<Mypage_Meetinglist> getMyMeetings(String id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return mypage_meetinglistRepository.findAllMeetingsByMember(member);
    }

    @Override
    public void leaveMeeting(Long meetingId, String id) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        if (meeting.getMembers().stream().noneMatch(m -> m.getId().equals(id))) {
            throw new RuntimeException("Member not part of the meeting");
        }

        meeting.getMembers().removeIf(m -> m.getId().equals(id));
        meeting.setMemberCount(meeting.getMembers().size());
        meetingRepository.save(meeting);
    }

    @Override
    public Meeting updateMeeting(Long meetingId, Meeting updatedMeeting, String id) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        if (!meeting.getLeader().getId().equals(id)) {
            throw new RuntimeException("Member is not the leader of the meeting");
        }

        meeting.setTitle(updatedMeeting.getTitle());
        meeting.setContent(updatedMeeting.getContent());
        meeting.setMaxMembers(updatedMeeting.getMaxMembers());

        return meetingRepository.save(meeting);
    }

    @Override
    public void deleteMeeting(Long meetingId, String id) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        if (!meeting.getLeader().getId().equals(id)) {
            throw new RuntimeException("Member is not the leader of the meeting");
        }

        meetingRepository.delete(meeting);
    }

    @Override
    public List<Post> getWrittenPosts(String id) {
        return memberRepository.findById(id)
                .map(Member::getWrittenPosts)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @Override
    public List<Post> getLikedPosts(String id) {
        return memberRepository.findById(id)
                .map(Member::getLikedPosts)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @Override
    public List<ChatRoom> getParticipatingChatRooms(String id) {
        return memberRepository.findById(id)
                .map(Member::getChatRooms)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @Override
    public Mypage save(Mypage mypage) {
        return myPageRepository.save(mypage);
    }

    @Override
    @Transactional
    public Mypage createOrUpdateMypageFromMember(Member member) {
        Optional<Mypage> mypageOptional = myPageRepository.findById(member.getId());
        Mypage mypage = mypageOptional.orElse(new Mypage(member));

        mypage.setNickname(member.getNickname());
        mypage.setPoints(member.getPoints());
        mypage.setEmail(member.getEmail());

        return myPageRepository.save(mypage);
    }

    @Override
    public void updateNickname(String id, String newNickname) {
        Mypage mypage = getMypageById(id)
                .orElseThrow(() -> new RuntimeException("Mypage not found")); // Optional에서 Mypage 가져오기
        mypage.setNickname(newNickname);
        myPageRepository.save(mypage); // myPageRepository를 사용하여 Mypage 저장

        // Member 업데이트
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        member.setNickname(newNickname);
        memberRepository.save(member);
    }


    @Override
    public void updateEmail(String id, String newEmail) {
        Mypage mypage = getMypageById(id)
                .orElseThrow(() -> new RuntimeException("Mypage not found")); // Optional에서 Mypage 가져오기
        mypage.setEmail(newEmail);
        myPageRepository.save(mypage); // myPageRepository를 사용하여 Mypage 저장

        // Member 업데이트
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        member.setEmail(newEmail);
        memberRepository.save(member);
    }


    @Override
    public void updateEmergencyContact(String id, String newContact) {
        Mypage mypage = getMypageById(id)
                .orElseThrow(() -> new RuntimeException("Mypage not found")); // Optional에서 Mypage 가져오기
        mypage.updateEmergencyContact(newContact); // 긴급 연락처 업데이트
        myPageRepository.save(mypage); // myPageRepository를 사용하여 Mypage 저장
    }


    @Override
    public void updateLikeNotificationSettings(String id, boolean likeNotification) {
        Mypage mypage = getMypageById(id)
                .orElseThrow(() -> new RuntimeException("Mypage not found")); // Optional에서 Mypage 가져오기
        mypage.setLikeNotificationEnabled(likeNotification); // 알림 설정 업데이트
        myPageRepository.save(mypage); // 변경된 Mypage 저장

        // 사용자의 모든 게시물 가져오기
        List<Post> userPosts = postRepository.findAll();
        for (Post post : userPosts) {
            // 게시물의 작성자가 현재 사용자인 경우
            if (post.getAuthor().getId().equals(mypage.getId())) {
                post.setNotificationEnabled(likeNotification); // 알림 설정 적용
                postRepository.save(post); // 변경된 게시물 저장
            }
        }
    }

}
