package hello.imagine.meeting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class MeetingCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy = "meetingCategory")
    @JsonIgnore
    private List<Meeting> meetings;



    // 하위 카테고리와 연결 시키기
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private MeetingCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingCategory> subcategories = new ArrayList<>();

    public MeetingCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(MeetingCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<MeetingCategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<MeetingCategory> subcategories) {
        this.subcategories = subcategories;
    }

    // 하위 카테고리 추가
    public void addSubcategory(MeetingCategory subcategory) {
        subcategories.add(subcategory);
        subcategory.setParentCategory(this);
    }

    // 이름만 있는 카테고리 생성자
    public MeetingCategory(String name) {
        this.name = name;
    }

    // 이름과 상위 카테고리를 지정한 카테고리 생성자
    public MeetingCategory(String name, MeetingCategory parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }






    // 기본 생성자
    public MeetingCategory() {}

    public MeetingCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }



}
