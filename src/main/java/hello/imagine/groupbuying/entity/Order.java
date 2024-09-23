package hello.imagine.groupbuying.entity;

import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.Mypage_Orderlist;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Table(
        name = "Orders"
)
@Entity
public class Order {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long memberId;
    private @NonNull String nickname;
    private Long orderId;
    private Long productId;
    private int quantity;
    private double price;
    private LocalDateTime orderDate;
    private String status;
    private String shippingAddress;

    @OneToMany(mappedBy = "order")
    private List<Mypage_Orderlist> mypage_orderlists;

    public Order() {}

    public Order(Long memberId, String nickname, Long orderId, Long productId, int quantity, double price,
                 LocalDateTime orderDate, String status, String shippingAddress) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.orderDate = orderDate;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }


}
