package ztpai.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Date;

@Entity
@Table (name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDroute;

    @ManyToOne
    @JoinColumn(name = "IDstation")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "IDtrain")
    private Train train;

    @NonNull
    @Temporal(TemporalType.TIME)
    private Date hour;
}