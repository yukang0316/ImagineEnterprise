package hello.imagine.groupbuying.entity;

import hello.imagine.login.model.Member;
import jakarta.persistence.*;

@Table(
        name = "Wishlist"
)
@Entity
@IdClass(WishlistId.class)
public class Wishlist {
    @Id
    @Column(name = "member_id")
    private Long memberId; // 기본 키와 외래 키 둘 다 사용 가능

    @MapsId
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", insertable = false, updatable = false, nullable = false)
    private Member member;

    private String nickname;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String name;
    private String price;
    private String image;
    private String link;

    public Wishlist() {}

    public Wishlist(Member member, Product product) {
        this.member = member;
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.product = product;
        this.name = product.getName();
        this.price = product.getPrice();
        this.image = product.getImage();
        this.link = product.getLink();
    }

    // Getters and Setters
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
