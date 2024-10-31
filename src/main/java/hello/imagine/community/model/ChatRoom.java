package hello.imagine.community.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.imagine.login.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int maxParticipants;
    private int participantCount;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Member creator; // 생성자 필드 추가

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // 카테고리 필드 추가

    @ManyToMany
    @JoinTable(
            name = "chatroom_participants",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )

    @JsonIgnore
    private List<Member> participants = new ArrayList<>();
}