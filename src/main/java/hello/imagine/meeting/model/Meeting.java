    package hello.imagine.meeting.model;

    import hello.imagine.login.model.Member;
    import jakarta.persistence.*;

    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    @Entity
    public class Meeting {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        // 소모임 만들기
        private Long id;
        private String title; // 소모임 제목
        private String description; // 소모임 설명
        private String place;
        private int memberCount = 0; // 현재 멤버 수
        private int maxMembers;// 최대 멤버 수

        // 위치 서비스
        private double latitude; // 위도
        private double longitude; // 경도

        // 카테고리 연결
        @ManyToOne
        @JoinColumn(name = "meeting_category_id")
        private MeetingCategory meetingCategory;


        @ManyToMany
        @JoinTable(
                name = "meeting_members",
                joinColumns = @JoinColumn(name = "meeting_id"),
                inverseJoinColumns = @JoinColumn(name = "member_id")
        )
        private Set<Member> member = new HashSet<>();


        // getters and setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getMemberCount() {
            return memberCount;
        }

        public void setMemberCount(int memberCount) {
            this.memberCount = memberCount;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }


        public MeetingCategory getMeetingCategory() {
            return meetingCategory;
        }

        public void setMeetingCategory(MeetingCategory meetingCategory) {
            this.meetingCategory = meetingCategory;
        }


        public int getMaxMembers() {
            return maxMembers;
        }

        public void setMaxMembers(int maxMembers) {
            this.maxMembers = maxMembers;
        }

        public Set<Member> getMember() {
            return member;
        }

        public void setMember(Set<Member> member) {
            this.member = member;
        }
    }