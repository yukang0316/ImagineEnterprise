package hello.imagine.community.repository;

import hello.imagine.community.model.ChatMessage;
import hello.imagine.community.model.ChatRoom;
import hello.imagine.login.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 특정 채팅방(roomId)에 속한 메시지를 시간순으로 조회하는 메서드
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
}
