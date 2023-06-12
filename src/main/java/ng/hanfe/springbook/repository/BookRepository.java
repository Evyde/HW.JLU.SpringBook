package ng.hanfe.springbook.repository;

import ng.hanfe.springbook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);
    @Query("SELECT b FROM Book b WHERE b.name LIKE CONCAT('%',:name,'%')")
    List<Book> findBooksWithPartOfName(@Param("name") String name);
}
