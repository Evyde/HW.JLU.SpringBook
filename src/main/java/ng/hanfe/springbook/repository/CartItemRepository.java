package ng.hanfe.springbook.repository;

import ng.hanfe.springbook.model.Book;
import ng.hanfe.springbook.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByUsername(String username);

    CartItem findByBookId(Long id);
}
