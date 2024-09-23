package hello.imagine.groupbuying.service;

import hello.imagine.groupbuying.entity.Order;

public interface OrderService {
    Order createOrder(Order order);  //주문 생성
    Order getOrderById(Long orderId);  //주문 조회
    void cancelOrder(Long orderId);  //주문 취소
}
