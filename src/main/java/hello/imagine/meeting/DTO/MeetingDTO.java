package hello.imagine.meeting.DTO;


import hello.imagine.meeting.model.Meeting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MeetingDTO {
    private String title; // 모임 이름
    private String introduction; // 한줄 소개
    private String content; // 본문 내용
    private int memberCount; // 현재 인원
    private Long meetingCategoryId;
    private Long subcategoryId;


    public MeetingDTO(String title, String introduction, String content, int memberCount, Long meetingCategoryId, Long subcategoryId) {
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.memberCount = memberCount;
        this.meetingCategoryId = meetingCategoryId;
        this.subcategoryId = subcategoryId;
    }


}