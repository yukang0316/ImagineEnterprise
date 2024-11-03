package hello.imagine.myPage.entity;

import java.io.Serializable;
import java.util.Objects;

public class MypageId implements Serializable {
    private String id;  // id를 String 타입으로 변경

    public MypageId() {}

    public MypageId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MypageId mypageId = (MypageId) o;
        return Objects.equals(id, mypageId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
