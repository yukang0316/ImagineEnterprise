package hello.imagine.myPage.entity;

import hello.imagine.login.model.Member;
import jakarta.persistence.*;

@Table(
        name = "Mypage_Meetinglist"
)
@Entity
public class Mypage_Meetinglist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 회원 참조

    // 소속된 소모임 목록
    @ManyToOne
    @JoinColumn(name = "meeting_list_id") // Join column을 통해 Mypage_Meetinglist를 참조
    private Mypage_Meetinglist mypageMeetingList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Mypage_Meetinglist getMypageMeetingList() {
        return mypageMeetingList;
    }

    public void setMypageMeetingList(Mypage_Meetinglist mypageMeetingList) {
        this.mypageMeetingList = mypageMeetingList;
    }
}
