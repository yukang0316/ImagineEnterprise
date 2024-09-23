package hello.imagine.groupbuying.service;

import hello.imagine.groupbuying.entity.Product;
import hello.imagine.groupbuying.entity.Wishlist;
import hello.imagine.groupbuying.repository.WishlistRepository;
import hello.imagine.login.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistServiceImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public Wishlist addWishlist(Member member, Product product) {
        Wishlist wishlist = new Wishlist();
        wishlist.setMember(member);
        wishlist.setProduct(product);
        return wishlistRepository.save(wishlist);
    }

    @Override
    public void removeWishlist(Member member, Product product) {
        wishlistRepository.deleteByMemberAndProduct(member, product);
    }

}
