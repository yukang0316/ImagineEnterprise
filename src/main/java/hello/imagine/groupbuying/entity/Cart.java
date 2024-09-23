package hello.imagine.groupbuying.entity;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Table(
        name = "Cart"
)
@Entity
public class Cart {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long memberId;
    private @NonNull String nickname;
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private boolean stockStatus;

    public Cart() {}

    public Cart(Long memberId, String nickname, String productId, String productName, int quantity, double price, boolean stockStatus) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.stockStatus = stockStatus;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(boolean stockStatus) {
        this.stockStatus = stockStatus;
    }

}
