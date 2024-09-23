package hello.imagine.groupbuying.service;

import hello.imagine.groupbuying.entity.Cart;

public interface CartService {
    Cart addCart(Long memberId, String nickname, String productId, String productName, int quantity, double price, boolean stockStatus);
    void removeCart(Long memberId, String productId);
}
