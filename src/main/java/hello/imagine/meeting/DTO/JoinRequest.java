package hello.imagine.meeting.DTO;




public class JoinRequest {

    private Long id;
    private Long meetingCategoryId;
    private Long subcategoryId;



    public JoinRequest(Long id, Long meetingCategoryId, Long subcategoryId) {
        this.id = id;
        this.meetingCategoryId = meetingCategoryId;
        this.subcategoryId = subcategoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeetingCategoryId() {
        return meetingCategoryId;
    }

    public void setMeetingCategoryId(Long meetingCategoryId) {
        this.meetingCategoryId = meetingCategoryId;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }
}
