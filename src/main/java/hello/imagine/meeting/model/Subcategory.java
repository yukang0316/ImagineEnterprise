package hello.imagine.meeting.model;

import jakarta.persistence.*;


@Entity
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private MeetingCategory parentCategory;

    // 기본 생성자
    public Subcategory() {}

    // 생성자
    public Subcategory(String name, MeetingCategory parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    // Getter 및 Setter
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

    public MeetingCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(MeetingCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
}