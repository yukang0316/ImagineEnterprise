package hello.imagine.myPage.entity;

import java.io.Serializable;
import java.util.Objects;

public class MypageId implements Serializable {
    private Long memberId;

    public MypageId() {}

    public MypageId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MypageId mypageId = (MypageId) o;
        return Objects.equals(memberId, mypageId.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

    // getters and setters
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
