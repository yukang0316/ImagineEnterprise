package hello.imagine.myPage.entity;
import hello.imagine.groupbuying.entity.Order;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(
        name = "Mypage_Orderlist"
)
@Entity
public class Mypage_Orderlist {
    @Id
    @Column(name = "orderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Mypage mypage;

    @MapsId
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId", insertable = false, updatable = false, nullable = false)
    private Order order;

    private Long productId;
    private int quantity;
    private double price;
    private LocalDateTime orderDate;

    public Mypage_Orderlist() {
    }

    public Mypage_Orderlist(Order order) {
        this.order = order;
        this.orderId = order.getOrderId();
        this.productId = order.getProductId();
        this.quantity = order.getQuantity();
        this.price = order.getPrice();
        this.orderDate = order.getOrderDate();
    }

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
        this.orderId = order.getOrderId();
        this.productId = order.getProductId();
        this.quantity = order.getQuantity();
        this.price = order.getPrice();
        this.orderDate = order.getOrderDate();
    }
    public Long getProductId() {
        return order != null ? order.getProductId() : productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return order != null ? order.getQuantity() : quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return order != null ? order.getPrice() : price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public LocalDateTime getOrderDate() {
        return order != null ? order.getOrderDate() : orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
