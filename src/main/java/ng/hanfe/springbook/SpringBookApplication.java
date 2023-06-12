package ng.hanfe.springbook;

import ng.hanfe.springbook.model.Book;
import ng.hanfe.springbook.model.Role;
import ng.hanfe.springbook.model.User;
import ng.hanfe.springbook.repository.BookRepository;
import ng.hanfe.springbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBookApplication{

    public static void main(String[] args) {
        SpringApplication.run(SpringBookApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(BookRepository bookRepository, UserRepository userRepository) {
        return args -> {
            bookRepository.save(new Book("哼哼哼啊啊啊", 114514, 19.18));
            bookRepository.save(new Book("我的奋斗", 1919810, 12.18));

            userRepository.save(new User("asd", "0cc175b9c0f1b6a831c399e269772661", Role.USER));
            userRepository.save(new User("admin", "0cc175b9c0f1b6a831c399e269772661", Role.ADMIN));
        };
    }

}
