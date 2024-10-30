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

    // 계정 설정 -> 이메일
    @Column
    private String email;

    // 계정 설정 -> 비상연락처
    @Column
    private String emergencyContact;

    // 알림 설정 필드 추가
    @Column(name = "comment_notification", nullable = false)
    private boolean commentNotificationEnabled = true; // 댓글 알림

    @Column(name = "chat_notification", nullable = false)
    private boolean chatNotificationEnabled = true; // 채팅 알림

    @Column(name = "like_notification", nullable = false)
    private boolean likeNotificationEnabled = true; // 좋아요 알림


    public Mypage() {
    }

    public Mypage(Member member) {
        this.member = member;
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.points = member.getPoints();
        this.email = member.getEmail();
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
        this.email = member.getEmail();
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

    public String getEmail() {return member != null ? member.getEmail() : email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public boolean isCommentNotificationEnabled() {
        return commentNotificationEnabled;
    }

    public void setCommentNotificationEnabled(boolean commentNotificationEnabled) {
        this.commentNotificationEnabled = commentNotificationEnabled;
    }

    public boolean isChatNotificationEnabled() {
        return chatNotificationEnabled;
    }

    public void setChatNotificationEnabled(boolean chatNotificationEnabled) {
        this.chatNotificationEnabled = chatNotificationEnabled;
    }

    public boolean isLikeNotificationEnabled() {
        return likeNotificationEnabled;
    }

    public void setLikeNotificationEnabled(boolean likeNotificationEnabled) {
        this.likeNotificationEnabled = likeNotificationEnabled;
    }

    // 계정 설정 업데이트 메서드 추가 (추후 서비스에서 활용)
    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    public void updateEmergencyContact(String newContact) {
        this.emergencyContact = newContact;
    }

    // 알림 설정 업데이트 메서드 추가
    public void updateCommentNotification(boolean isEnabled) {
        this.commentNotificationEnabled = isEnabled;
    }

    public void updateChatNotification(boolean isEnabled) {
        this.chatNotificationEnabled = isEnabled;
    }

    public void updateLikeNotification(boolean isEnabled) {
        this.likeNotificationEnabled = isEnabled;
    }
}

