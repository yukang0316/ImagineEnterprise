package hello.imagine.login.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.imagine.attendance.model.Attendance;
import hello.imagine.community.model.ChatMessage;
import hello.imagine.meeting.model.Meeting;
import hello.imagine.myPage.entity.Mypage;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String name;
    @Column(unique = true)
    private String id;
    private String birthDate;
    private String pw;
    private String email;
    private String nickname;

    private int points;

    @Setter
    @Getter
    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Mypage> mypages;

    @Setter
    @Getter
    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<ChatMessage> sentMessages;

    @Setter
    @Getter
    @ManyToMany
    @JoinTable(
            name = "meeting_members",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "meeting_id")
    )
    private Set<Meeting> meetings = new HashSet<>();

    public Member(String name, String id, String birthDate, String pw, String email, String nickname) {
        this.name = name;
        this.id = id;
        this.birthDate = birthDate;
        this.pw = pw;
        this.email = email;
        this.nickname = nickname;
    }

}