package hello.imagine.myPage.repository;

import hello.imagine.login.model.Member;
import hello.imagine.myPage.entity.Mypage_Meetinglist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Mypage_MeetinglistRepository extends JpaRepository<Mypage_Meetinglist, Long> {

    // 특정 회원의 Mypage_Meetinglist 정보를 조회하는 메서드
    List<Mypage_Meetinglist> findAllMeetingsByMember(Member member);
}
