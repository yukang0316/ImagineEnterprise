package hello.imagine.groupbuying.controller;

import hello.imagine.groupbuying.entity.Product;
import hello.imagine.groupbuying.entity.Wishlist;
import hello.imagine.groupbuying.repository.WishlistRepository;
import hello.imagine.groupbuying.service.WishlistService;
import hello.imagine.login.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add")
    public ResponseEntity<Wishlist> addWishlist(@RequestParam Long memberId, @RequestParam Long productId) {
        // 회원과 상품을 서비스에서 가져오는 로직을 추가해야 합니다.
        // 여기서는 임시로 null을 반환합니다.
        Member member = new Member(); // 실제로는 데이터베이스에서 회원 정보를 가져와야 합니다.
        member.setMemberId(memberId);

        Product product = new Product(); // 실제로는 데이터베이스에서 상품 정보를 가져와야 합니다.
        product.setProductId(productId);

        Wishlist wishlist = wishlistService.addWishlist(member, product);
        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeWishlist(@RequestParam Long memberId, @RequestParam Long productId) {
        // 회원과 상품을 서비스에서 가져오는 로직을 추가해야 합니다.
        // 여기서는 임시로 null을 반환합니다.
        Member member = new Member(); // 실제로는 데이터베이스에서 회원 정보를 가져와야 합니다.
        member.setMemberId(memberId);

        Product product = new Product(); // 실제로는 데이터베이스에서 상품 정보를 가져와야 합니다.
        product.setProductId(productId);

        wishlistService.removeWishlist(member, product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
