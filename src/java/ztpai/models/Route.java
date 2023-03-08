package ztpai.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table (name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDroute;

    @ManyToOne
    @JoinColumn(name = "IDstation", nullable = false)
    private Station station;

    @ManyToOne
    @JoinColumn(name = "IDtrain", nullable = false)
    private Train train;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date hour;

    public Route(Station station, Train train, Date hour) {
        this.station = station;
        this.train = train;
        this.hour = hour;
    }

    public Route() {}

    public Long getIDroute() {
        return IDroute;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }
}