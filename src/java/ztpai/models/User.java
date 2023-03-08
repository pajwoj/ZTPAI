package ztpai.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDuser;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {}

    public Long getIDuser() {
        return IDuser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}