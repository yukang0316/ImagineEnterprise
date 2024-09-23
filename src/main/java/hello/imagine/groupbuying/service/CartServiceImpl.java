package hello.imagine.groupbuying.service;

import hello.imagine.groupbuying.entity.Cart;
import hello.imagine.groupbuying.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addCart(Long memberId, String nickname, String productId, String productName, int quantity, double price, boolean stockStatus) {
        // 먼저 장바구니에 같은 상품이 이미 존재하는지 확인
        Cart existingCart = cartRepository.findByMemberIdAndProductId(memberId, productId);
        if (existingCart != null) {
            // 이미 장바구니에 상품이 있으면 수량만 업데이트
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            return cartRepository.save(existingCart);
        } else {
            // 새로운 장바구니 항목 생성
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setNickname(nickname);
            newCart.setProductId(productId);
            newCart.setProductName(productName);
            newCart.setQuantity(quantity);
            newCart.setPrice(price);
            newCart.setStockStatus(stockStatus);
            return cartRepository.save(newCart);
        }
    }

    @Override
    public void removeCart(Long memberId, String productId) {
        // 장바구니 항목을 찾고 삭제
        Cart cart = cartRepository.findByMemberIdAndProductId(memberId, productId);
        if (cart != null) {
            cartRepository.delete(cart);
        } else {
            throw new RuntimeException("Cart item not found: memberId=" + memberId + ", productId=" + productId);
        }
    }
}
