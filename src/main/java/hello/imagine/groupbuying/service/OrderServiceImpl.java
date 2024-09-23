package hello.imagine.groupbuying.service;

import hello.imagine.groupbuying.entity.Order;
import hello.imagine.groupbuying.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        orderRepository.save(order);
        System.out.println("Order created : " + order.getOrderId());
        return order;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        System.out.println("Order cancelled : " + orderId);
    }
}
