package ng.hanfe.springbook.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ng.hanfe.springbook.model.Book;
import ng.hanfe.springbook.model.CartItem;
import ng.hanfe.springbook.repository.BookRepository;
import ng.hanfe.springbook.repository.CartItemRepository;
import ng.hanfe.springbook.repository.UserRepository;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public BookController(
            BookRepository bookRepository,
            UserRepository userRepository,
            CartItemRepository cartItemRepository
    ) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @PostMapping("/add")
    public Map<String, String> add(HttpSession session, @Valid @RequestBody Book param) {
        String username = session.getAttribute("username").toString();

        param.setPrice(Double.parseDouble(new DecimalFormat("#.00").format(param.getPrice())));

        bookRepository.save(param);
        return Map.of(
                "message", "成功！",
                "redirect", "/",
                "status", "success"
        );
    }

    @GetMapping("/delete/{id}")
    public Map<String, String> deleteCart(@PathVariable @Valid Long id, HttpSession session) {

        Optional<Book> book = bookRepository.findById(id);
        String username = session.getAttribute("username").toString();

        if (book.isPresent()) {
            bookRepository.delete(book.get());
            return Map.of(
                    "message", "成功！",
                    "redirect", "/",
                    "status", "success"
            );
        } else {
            return Map.of(
                    "message", "找不到要删除的书！",
                    "redirect", "/",
                    "status", "failed"
            );
        }
    }
}
