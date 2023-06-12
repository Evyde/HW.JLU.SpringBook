package ng.hanfe.springbook.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ng.hanfe.springbook.model.Book;
import ng.hanfe.springbook.model.CartItem;
import ng.hanfe.springbook.model.User;
import ng.hanfe.springbook.repository.BookRepository;
import ng.hanfe.springbook.repository.CartItemRepository;
import ng.hanfe.springbook.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public CartController(
            BookRepository bookRepository,
            UserRepository userRepository,
            CartItemRepository cartItemRepository
    ) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping("/add/{id}")
    public Map<String, String> addCart(@PathVariable @Valid Long id, HttpSession session) {

        Optional<Book> tempBook = bookRepository.findById(id);
        String username = session.getAttribute("username").toString();

        if (tempBook.isPresent()) {
            CartItem tempCartItem = cartItemRepository.findByBookId(id);
            if (tempCartItem != null) {
                cartItemRepository.save(new CartItem(username, id, tempCartItem.getQuantity() + 1));
            } else {
                cartItemRepository.save(new CartItem(username, id, 1));
            }
            return Map.of(
                    "message", "成功！",
                    "redirect", "/",
                    "status", "success"
            );
        } else {
            return Map.of(
                    "message", "无法找到对应书籍！",
                    "redirect", "/",
                    "status", "failed"
            );
        }
    }

    @GetMapping("/delete/{id}")
    public Map<String, String> deleteCart(@PathVariable @Valid Long id, HttpSession session) {

        CartItem cartItem = cartItemRepository.findByBookId(id);
        String username = session.getAttribute("username").toString();

        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return Map.of(
                    "message", "成功！",
                    "redirect", "/cart",
                    "status", "success"
            );
        } else {
            return Map.of(
                    "message", "可能已经被删除？",
                    "redirect", "/",
                    "status", "failed"
            );
        }
    }

    @GetMapping("/checkout")
    public Map<String, String> checkout(HttpSession session) {
        String username = session.getAttribute("username").toString();
        User user = userRepository.findByUsername(username);
        List<CartItem> cartItems = cartItemRepository.findAllByUsername(username);
        Double nowBalance = user.getBalance();

        if (!cartItems.isEmpty()) {
            for (CartItem c: cartItems) {
                Optional<Book> b = bookRepository.findById(c.getBookId());
                if (b.isPresent()) {
                    if (b.get().getQuantity() >= c.getQuantity()) {
                        if (nowBalance >= c.getQuantity() * b.get().getPrice()) {
                            nowBalance -= c.getQuantity() * b.get().getPrice();
                            continue;
                        } else {
                            return Map.of(
                                    "message", "余额不足！当前余额："+ user.getBalance() +"元！",
                                    "redirect", "/cart",
                                    "status", "failed"
                            );
                        }
                    } else {
                        return Map.of(
                                "message", "[" + c.getBookId() + "]《" + b.get().getName() + "》数量不足！",
                                "redirect", "/cart",
                                "status", "failed"
                        );
                    }
                } else {
                    return Map.of(
                            "message", "找不到书籍！",
                            "redirect", "/",
                            "status", "failed"
                    );
                }
            }

            for (CartItem c: cartItems) {
                Optional<Book> b = bookRepository.findById(c.getBookId());
                if (b.isPresent()) {
                    b.get().setQuantity(b.get().getQuantity() - c.getQuantity());
                    bookRepository.save(b.get());
                } else {
                    return Map.of(
                            "message", "未知错误！",
                            "redirect", "/",
                            "status", "failed"
                    );
                }
                cartItemRepository.delete(c);
            }

            nowBalance = Double.parseDouble(new DecimalFormat("#.00").format(nowBalance));
            user.setBalance(nowBalance);
            userRepository.save(user);
            return Map.of(
                    "message", "消费成功！余额" + user.getBalance() + "元！",
                    "redirect", "/",
                    "status", "success"
            );
        } else {
            return Map.of(
                    "message", "空购物车！",
                    "redirect", "/",
                    "status", "failed"
            );
        }
    }
}
