package hello.imagine.attendance.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Attendance {

    @Id
    private String Id; // 사용자의 아이디 저장
    private int Point; // 포인트 저장
    private Date Date;

    public Attendance() {}

    public Attendance(String Id, int Point, Date Date) {
        this.Id = Id;
        this.Point = Point;
        this.Date = Date;
    }

    //getter setter

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }



}
