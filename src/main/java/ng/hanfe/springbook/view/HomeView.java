package ng.hanfe.springbook.view;

import jakarta.servlet.http.HttpSession;
import ng.hanfe.springbook.model.User;
import ng.hanfe.springbook.repository.BookRepository;
import ng.hanfe.springbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeView {
    private BookRepository bookRepository;
    private UserRepository userRepository;

    public HomeView(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView home(HttpSession session) {
        User user = userRepository.findByUsername((String) session.getAttribute("username"));
        // System.out.println(session);
        ModelAndView m = new ModelAndView("index");
        m.addObject("books", bookRepository.findAll());
        m.addObject("user", user);
        return m;
    }
}
