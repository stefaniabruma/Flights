package com.example.airlineflights.Service;

import Utils.Observable;
import Utils.Observer;
import com.example.airlineflights.Domain.Client;
import com.example.airlineflights.Domain.Flight;
import com.example.airlineflights.Domain.Ticket;
import com.example.airlineflights.Repository.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service implements Observable {
    List<Observer> obs = new ArrayList<>();
    private Repository<Long, Client> repo_clients;
    private Repository<Long, Flight> repo_flights;
    private Repository<Long, Ticket> repo_tickets;

    public Service(Repository<Long, Client> repo_clients, Repository<Long, Flight> repo_flights, Repository<Long, Ticket> repo_tickets) {
        this.repo_clients = repo_clients;
        this.repo_flights = repo_flights;
        this.repo_tickets = repo_tickets;
    }

    public Optional<Client> findByUsername(String username){

        return StreamSupport.stream(repo_clients.findAll().spliterator(), false)
                .filter(c -> Objects.equals(c.getUsername(), username))
                .findFirst();
    }

    public List<Ticket> ticketsByClientUsername(String username){
        return StreamSupport.stream(repo_tickets.findAll().spliterator(), false)
                .filter(t -> Objects.equals(t.getUsername(), username))
                .collect(Collectors.toList());
    }

    public List<Ticket> ticketsSoldFor24thJan(){

        return StreamSupport.stream(repo_tickets.findAll().spliterator(), false)
                .filter(t -> {
                    var op = repo_flights.findOne(t.getFlightId());
                    return op.isPresent() && op.get().getDeparture().getDayOfMonth() == 24 && op.get().getDeparture().getMonthValue() == 1;
                })
                .collect(Collectors.toList());

    }

    public List<Flight> filtered(String from, String to, LocalDate date){
        return StreamSupport.stream(repo_flights.findAll().spliterator(), false)
                .filter(f -> f.getFrom().startsWith(from) && f.getTo().startsWith(to)
                && f.getDeparture().getDayOfMonth() == date.getDayOfMonth() && f.getDeparture().getMonthValue() == date.getMonthValue() && f.getDeparture().getYear() == date.getYear())
                .collect(Collectors.toList());
    }

    public List<Flight> getAllFlights(){
        return StreamSupport.stream(repo_flights.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<String> getAllFrom(){
        return StreamSupport.stream(repo_flights.findAll().spliterator(), false)
                .map(Flight::getFrom)
                .distinct()
                .collect(Collectors.toList());
    }
    public List<String> getAllTo(){
        return StreamSupport.stream(repo_flights.findAll().spliterator(), false)
                .map(Flight::getTo)
                .distinct()
                .collect(Collectors.toList());
    }

    public void adaugaBilet(Long flight_id, String username){

        var op =repo_flights.findOne(flight_id);
        if(op.isPresent() && nrBileteCumparate(flight_id) < op.get().getSeats())
            repo_tickets.save(new Ticket(username, flight_id, LocalDateTime.now()));

        notifyObservers();
    }

    public int nrBileteCumparate(Long flight_id){

        return (int) StreamSupport.stream(repo_tickets.findAll().spliterator(), true)
                .filter(t -> Objects.equals(t.getFlightId(), flight_id))
                .count();
    }

    @Override
    public void addObserver(Observer o) {
        obs.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        obs.remove(o);
    }

    @Override
    public void notifyObservers() {
        obs.forEach(Observer::update);
    }
}
