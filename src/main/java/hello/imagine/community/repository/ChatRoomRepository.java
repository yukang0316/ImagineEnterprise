package hello.imagine.community.repository;

import hello.imagine.community.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByCategoryOrderByParticipantsDesc(String category);

    // 참여자가 5명 이상인 채팅방을 찾는 메서드
    List<ChatRoom> findByParticipantCountGreaterThanEqual(int participantCount);
}