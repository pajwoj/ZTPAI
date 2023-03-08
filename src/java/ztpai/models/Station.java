package ztpai.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

@Entity
@Table (name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDstation;

    @Column(unique = true)
    @NonNull
    private String name;

    @OneToMany(mappedBy = "IDstation")
    private ArrayList<Station> stations;

    public Station(Long IDstation, @NonNull String name) {
        this.IDstation = IDstation;
        this.name = name;
    }

    public Station() {}

    public Long getIDstation() {
        return IDstation;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}