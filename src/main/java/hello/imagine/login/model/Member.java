package hello.imagine.login.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.imagine.attendance.model.Attendance;
import hello.imagine.community.model.ChatMessage;
import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.model.Post;
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
    private List<Mypage> mypages;

    // 사용자가 작성한 게시글 목록
    @OneToMany(mappedBy = "author") // Post 엔티티에서 author 필드로 매핑
    private List<Post> writtenPosts;

    // 사용자가 좋아요를 누른 게시글 목록
    @ManyToMany
    @JoinTable(
            name = "liked_posts",
            joinColumns = @JoinColumn(name = "mypage_community_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> likedPosts;

    // 사용자가 참여 중인 채팅방 목록
    @ManyToMany
    @JoinTable(
            name = "mypage_chatrooms",
            joinColumns = @JoinColumn(name = "mypage_community_id"),
            inverseJoinColumns = @JoinColumn(name = "chatroom_id")
    )
    private List<ChatRoom> chatRooms;


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