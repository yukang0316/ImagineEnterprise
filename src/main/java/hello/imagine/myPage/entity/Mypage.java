package hello.imagine.myPage.entity;

import hello.imagine.login.model.Member;
import jakarta.persistence.*;

@Table(
        name = "Mypage"
)
@Entity
@IdClass(MypageId.class)
public class Mypage {
    @Id
    @Column(name = "member_id")
    private Long memberId; // 기본 키와 외래 키 둘 다 사용 가능

    @MapsId
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", insertable = false, updatable = false, nullable = false)
    private Member member;

    @Column
    private String nickname;

    @Column
    private int points;

    public Mypage() {
    }

    public Mypage(Member member) {
        this.member = member;
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.points = member.getPoints();
    }

    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.points = member.getPoints();
    }

    public String getNickname() {
        return member != null ? member.getNickname() : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPoints() {
        return member != null ? member.getPoints() : points;
    }

    public void setPoints(int points) {
       this.points = points;
    }
}

