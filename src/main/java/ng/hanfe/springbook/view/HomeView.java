package ng.hanfe.springbook.view;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ng.hanfe.springbook.model.Book;
import ng.hanfe.springbook.model.CartItem;
import ng.hanfe.springbook.model.User;
import ng.hanfe.springbook.repository.BookRepository;
import ng.hanfe.springbook.repository.CartItemRepository;
import ng.hanfe.springbook.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeView {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public HomeView(BookRepository bookRepository, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping
    public ModelAndView home(HttpSession session) {
        User user = userRepository.findByUsername((String) session.getAttribute("username"));
        // System.out.println(session);
        ModelAndView m = new ModelAndView("index");
        m.addObject("books", bookRepository.findAll());
        m.addObject("user", user);
        m.addObject("title", "书城");
        m.addObject("cart", false);
        return m;
    }

    @GetMapping("/search")
    public ModelAndView search(HttpSession session, @RequestParam(value = "q") @Valid String name) {
        User user = userRepository.findByUsername((String) session.getAttribute("username"));
        // System.out.println(session);
        ModelAndView m = new ModelAndView("index");
        System.out.println(bookRepository.findBooksWithPartOfName(name));
        m.addObject("books", bookRepository.findBooksWithPartOfName(name));
        m.addObject("user", user);
        m.addObject("title", "搜索“" + name + "”的结果");
        m.addObject("cart", false);
        return m;
    }

    @GetMapping("/cart")
    public ModelAndView cart(HttpSession session) {
        String username = session.getAttribute("username").toString();
        User user = userRepository.findByUsername(username);
        ModelAndView m = new ModelAndView("index");
        List<CartItem> cartItems = cartItemRepository.findAllByUsername(username);
        HashMap<Book, Double> priceMap = new HashMap<>();
        HashMap<Book, CartItem> cartItemMap = new HashMap<>();
        for (CartItem c: cartItems) {
            Book b = bookRepository.getReferenceById(c.getBookId());
            priceMap.put(
                    b,
                    c.getQuantity() * b.getPrice()
            );
            cartItemMap.put(
                    b,
                    c
            );
        }
        m.addObject("books", priceMap.keySet());
        m.addObject("priceMap", priceMap);
        m.addObject("cartItemMap", cartItemMap);
        m.addObject("cart", true);
        m.addObject("user", user);
        m.addObject("title", "购物车");
        return m;
    }
}
