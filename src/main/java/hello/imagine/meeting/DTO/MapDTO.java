package hello.imagine.meeting.DTO;

// 지도상에 모든 소모임 좌표를 송출하는 DTO

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapDTO {
    private long id;
    private String title;
    private double latitude;
    private double longitude;
    private Long meetingCategoryId;

    public MapDTO(long id,String title, double latitude, double longitude, long meetingCategoryId) {
        this.id = id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.meetingCategoryId = meetingCategoryId;
    }
}
