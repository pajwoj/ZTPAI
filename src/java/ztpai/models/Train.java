package ztpai.models;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table (name = "trains")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDtrain;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "IDtrain")
    private ArrayList<Train> trains;

    public Train(String name) {
        this.name = name;
    }

    public Train() {}

    public Long getIDtrain() {
        return IDtrain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}