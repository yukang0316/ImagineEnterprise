package hello.imagine.groupbuying.controller;

import hello.imagine.groupbuying.entity.Cart;
import hello.imagine.groupbuying.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    private final CartRepository cartRepository;

    @Autowired
    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @GetMapping({"/get3"})
    public Iterable<Cart> GetCart() {
        return this.cartRepository.findAll();
    }

    @PostMapping({"/add3"})
    public Cart put(@RequestParam Long memberId, @RequestParam String nickname, @RequestParam String productId, @RequestParam String productName, @RequestParam int quantity, @RequestParam double price, @RequestParam boolean stockStatus) {
        return (Cart) this.cartRepository.save(new Cart(memberId, nickname,productId, productName, quantity, price, stockStatus));
    }

    @PostMapping({"/addd3"})
    public String ADD(@RequestBody Cart cart) {
        this.cartRepository.save(cart);
        return "add";
    }

    @DeleteMapping({"delete3/{memberId}"})
    public String Delete(@PathVariable Long memberId){
        this.cartRepository.deleteById(memberId);
        return "success";
    }
}
