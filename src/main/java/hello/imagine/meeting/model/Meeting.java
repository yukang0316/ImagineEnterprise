package hello.imagine.meeting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hello.imagine.login.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title; // 소모임 제목
    private String introduction;
    private String content; // 소모임 설명
    private int memberCount = 0; // 현재 멤버 수
    private int maxMembers;// 최대 멤버 수

    private double latitude; // 위도
    private double longitude; // 경도


    // 카테고리 연결
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "meeting_category_id")
    @JsonBackReference
    private MeetingCategory meetingCategory;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    // 모임장
    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Member leader;


    // 멤버와 소모임 연결
    @ManyToMany(mappedBy = "meetings")
    private Set<Member> members = new HashSet<>();


    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public MeetingCategory getMeetingCategory() {
        return meetingCategory;
    }

    public void setMeetingCategory(MeetingCategory meetingCategory) {
        this.meetingCategory = meetingCategory;
    }


    public Member getLeader() {
        return leader;
    }

    public void setLeader(Member leader) {
        this.leader = leader;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }
}
