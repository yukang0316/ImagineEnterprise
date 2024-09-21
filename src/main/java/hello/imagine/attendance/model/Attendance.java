package hello.imagine.attendance.model;

import hello.imagine.login.model.Member;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private boolean checked;
    @Column(nullable = false)
    private int points;

    public Attendance() {}

    public Attendance(Member member, LocalDate date, boolean checked, int points) {
        this.member = member;
        this.date = date;
        this.checked = checked;
        this.points = points;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
