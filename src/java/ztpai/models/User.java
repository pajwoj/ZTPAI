package ztpai.models;

import jakarta.persistence.*;

@Entity
@Table (name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDuser;

    @Column(unique = true)
    private String email;

    private String password;

    public User(Long IDuser, String email, String password) {
        this.IDuser = IDuser;
        this.email = email;
        this.password = password;
    }

    public User() {}

    public Long getIDuser() {
        return IDuser;
    }

    public void setIDuser(Long IDuser) {
        this.IDuser = IDuser;
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