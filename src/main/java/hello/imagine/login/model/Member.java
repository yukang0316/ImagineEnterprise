package hello.imagine.login.model;

import hello.imagine.attendance.model.Attendance;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String name;
    @Column(unique = true)
    private String id;
    private String birthDate;
    private String pw;
    private String email;
    private String nickname;

    private int points;

    @OneToMany(mappedBy = "member")
    private List<Attendance> attendances;

    public Member() {}

    public Member(String name, String id, String birthDate, String pw, String email, String nickname) {
        this.name = name;
        this.id = id;
        this.birthDate = birthDate;
        this.pw = pw;
        this.email = email;
        this.nickname = nickname;
    }

    // getters and setters


    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}