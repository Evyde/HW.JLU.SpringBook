package ng.hanfe.springbook.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false, length = 50)
    String username;
    String password;
    Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public LocalDateTime getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(LocalDateTime tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // String token;
    LocalDateTime registerTime;
    LocalDateTime tokenExpireTime;
    double balance;

    public User(String username, String password, Role role) {
        if (role.equals(Role.USER)) {
            this.balance = 1000.00;
        } else {
            this.balance = 999999999;
        }
        this.username = username;
        this.password = password;
        this.role = role;
        this.registerTime = LocalDateTime.now();
        this.tokenExpireTime = LocalDateTime.now();
    }

    public User() {

    }

    public boolean isAdmin() {
        return this.role.equals(Role.ADMIN);
    }
}
