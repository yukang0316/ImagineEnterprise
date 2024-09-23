package hello.imagine.groupbuying.service;

import hello.imagine.groupbuying.entity.Product;
import hello.imagine.groupbuying.entity.Wishlist;
import hello.imagine.login.model.Member;

public interface WishlistService {
    Wishlist addWishlist(Member member, Product product);
    void removeWishlist(Member member, Product product);
}

