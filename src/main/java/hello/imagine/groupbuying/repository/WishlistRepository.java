package hello.imagine.groupbuying.repository;

import hello.imagine.groupbuying.entity.Product;
import hello.imagine.groupbuying.entity.Wishlist;
import hello.imagine.login.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    void deleteByMemberAndProduct(Member member, Product product);
}
