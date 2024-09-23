package hello.imagine.groupbuying.entity;

import java.util.Objects;

public class WishlistId {
    private Long memberId;

    public WishlistId() {}

    public WishlistId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishlistId wishlistId = (WishlistId) o;
        return Objects.equals(memberId, wishlistId.memberId);
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
