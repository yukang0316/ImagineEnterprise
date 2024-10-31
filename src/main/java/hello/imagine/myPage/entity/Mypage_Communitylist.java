package hello.imagine.myPage.entity;

import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.model.Post;
import hello.imagine.login.model.Member;
import jakarta.persistence.*;

import java.util.List;

@Table(
        name = "Mypage_Communitylist"
)

@Entity
public class Mypage_Communitylist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 사용자가 작성한 게시글 목록
    @OneToMany
    @JoinColumn(name = "author_id")
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

    // 기본 생성자
    public Mypage_Communitylist() {}

    // Getter and Setter methods

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

    public List<Post> getWrittenPosts() {
        return writtenPosts;
    }

    public void setWrittenPosts(List<Post> writtenPosts) {
        this.writtenPosts = writtenPosts;
    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }
}
