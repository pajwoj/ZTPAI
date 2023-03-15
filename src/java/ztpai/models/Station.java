package ztpai.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDstation;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "IDstation")
    private List<Station> stations;

    public Station(String name) {
        this.name = name;
    }

    public Station() {}

    public Long getIDstation() {
        return IDstation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}