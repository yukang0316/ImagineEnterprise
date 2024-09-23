package hello.imagine.groupbuying.repository;

import hello.imagine.groupbuying.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMemberIdAndProductId(Long memberId, String productId);
}
