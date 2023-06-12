package ng.hanfe.springbook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="cart")
public class CartItem {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    String username;
    @Id
    Long bookId;
    Integer quantity;
    LocalDateTime addTime;

    public CartItem() {}
    public CartItem(String username, Long bookId, Integer quantity) {
        this.username = username;
        this.bookId = bookId;
        this.quantity = quantity;
        this.addTime = LocalDateTime.now();
    }
}
