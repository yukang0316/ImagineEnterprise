package hello.imagine.community.dto;

import hello.imagine.community.model.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomDTO {
    private Long id;
    private String name;
    private String description;
    private int maxParticipants;
    private String creatorId;
    private Long categoryId;

    // ChatRoom 엔티티를 기반으로 하는 생성자
    public ChatRoomDTO(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.name = chatRoom.getName();
        this.description = chatRoom.getDescription();
        this.maxParticipants = chatRoom.getMaxParticipants();
        this.creatorId = chatRoom.getCreator().getId(); // 필요시 ChatRoom 엔티티의 creator 필드의 ID로 설정
        this.categoryId = chatRoom.getCategory().getId(); // 카테고리 ID
    }
}