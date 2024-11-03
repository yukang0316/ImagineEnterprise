package hello.imagine.meeting.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDTO {

    private long id;
    private String title;
    private String introduction;
    private String content;
    private int memberCount;
    private Double latitude;
    private Double longitude;
    private Long meetingCategoryId;;
    private Long subcategoryId;

    public LocationDTO() {}

    public LocationDTO(long id, String title, String introduction, String content, int memberCount, Double latitude, Double longitude, Long meetingCategoryId, Long subcategoryId) {
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.memberCount = memberCount;
        this.latitude = latitude;
        this.longitude = longitude;
        this.meetingCategoryId = meetingCategoryId;
        this.subcategoryId = subcategoryId;

    }


}
