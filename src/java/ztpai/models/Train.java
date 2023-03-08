package ztpai.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

@Entity
@Table (name = "trains")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDtrain;

    @Column(unique = true)
    @NonNull
    private String name;

    @OneToMany(mappedBy = "IDtrain")
    private ArrayList<Train> trains;

    public Train(Long IDtrain, @NonNull String name) {
        this.IDtrain = IDtrain;
        this.name = name;
    }

    public Train() {}

    public Long getIDtrain() {
        return IDtrain;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}