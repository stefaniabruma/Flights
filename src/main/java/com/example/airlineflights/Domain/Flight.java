package com.example.airlineflights.Domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight extends Entity<Long>{

    private String from;
    private String to;
    private LocalDateTime departure;
    private LocalDateTime landingtime;

    private int seats;

    public Flight(String from, String to, LocalDateTime departure, LocalDateTime landingtime, int seats) {
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.landingtime = landingtime;
        this.seats = seats;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public LocalDateTime getLandingtime() {
        return landingtime;
    }

    public void setLandingtime(LocalDateTime landingtime) {
        this.landingtime = landingtime;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Flight flight = (Flight) o;
        return seats == flight.seats && Objects.equals(from, flight.from) && Objects.equals(to, flight.to) && Objects.equals(departure, flight.departure) && Objects.equals(landingtime, flight.landingtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from, to, departure, landingtime, seats);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departure=" + departure +
                ", landingtime=" + landingtime +
                ", seats=" + seats +
                ", id=" + id +
                '}';
    }
}
