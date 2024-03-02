package com.example.airlineflights.Repository;

import com.example.airlineflights.Domain.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FlightDBRepository implements Repository<Long, Flight> {

    protected String url;
    protected String username;
    protected  String password;

    public FlightDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Flight> findAll() {
        Set<Flight> flights = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from flights")) {

            ResultSet result = statement.executeQuery();

            while(result.next()){

                Long id = result.getLong("id");
                String from = result.getString("from");
                String to = result.getString("to");
                LocalDateTime departureTime = result.getTimestamp("departure_time").toLocalDateTime();
                LocalDateTime landingTime = result.getTimestamp("landing_time").toLocalDateTime();
                int seats = result.getInt("seats");

                Flight flight = new Flight(from, to, departureTime, landingTime, seats);
                flight.setId(id);

                flights.add(flight);
            }

            return flights;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Flight> save(Flight entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Flight> findOne(Long id) {

        if(id == null)
            throw new IllegalArgumentException("Id cannot be null!");

        try(
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement("select * from flights where id = ?");
        ) {

            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if(result.next()){

                String from = result.getString("from");
                String to = result.getString("to");
                LocalDateTime departureTime = result.getTimestamp("departure_time").toLocalDateTime();
                LocalDateTime landingTime = result.getTimestamp("landing_time").toLocalDateTime();
                int seats = result.getInt("seats");

                Flight flight = new Flight(from, to, departureTime, landingTime, seats);
                flight.setId(id);

                return Optional.of(flight);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }
}
