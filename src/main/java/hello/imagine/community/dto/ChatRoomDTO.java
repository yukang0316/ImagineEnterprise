package hello.imagine.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomDTO {
    private String name;
    private String description;
    private int maxParticipants;
    private String creatorId;
    private String category;
}