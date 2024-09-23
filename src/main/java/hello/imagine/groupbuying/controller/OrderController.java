package hello.imagine.groupbuying.controller;

import hello.imagine.groupbuying.entity.Order;
import hello.imagine.groupbuying.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class OrderController {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping({"/get2"})
    public Iterable<Order> GetOrder() {
        return this.orderRepository.findAll();
    }

    @PostMapping({"/add2"})
    public Order put(@RequestParam Long memberId, @RequestParam String nickname, @RequestParam Long orderId, @RequestParam Long productId, @RequestParam int quantity, @RequestParam double price, @RequestParam LocalDateTime orderDate, @RequestParam String status, @RequestParam String shippingAddress) {
        return (Order) this.orderRepository.save(new Order(memberId, nickname, orderId, productId, quantity, price, orderDate, status, shippingAddress));
    }

    @PostMapping({"/addd2"})
    public String ADD(@RequestBody Order order) {
        this.orderRepository.save(order);
        return "add";
    }

    @DeleteMapping({"delete2/{orderId}"})
    public String Delete(@PathVariable Long orderId){
        this.orderRepository.deleteById(orderId);
        return "success";
    }

}
