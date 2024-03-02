package com.example.airlineflights.Repository;

import com.example.airlineflights.Domain.Ticket;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TicketDBRepository implements Repository<Long, Ticket> {


    protected String url;
    protected String username;
    protected  String password;

    public TicketDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Ticket> findAll() {
        Set<Ticket> tickets = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from tickets")) {

            ResultSet result = statement.executeQuery();

            while(result.next()){

                Long id = result.getLong("id");
                String usern = result.getString("username");
                Long flight_id = result.getLong("flight_id");
                LocalDateTime purchase_time = result.getTimestamp("purchase_time").toLocalDateTime();

                Ticket ticket = new Ticket(usern, flight_id, purchase_time);
                ticket.setId(id);

                tickets.add(ticket);
            }

            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ticket> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> save(Ticket entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity must not bee null!");

        try(

                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement("insert into " +
                        "tickets(flight_id, username, purchase_time) " +
                        "values(?, ?, ?)")

        ){

            statement.setLong(1, entity.getFlightId());
            statement.setString(2, entity.getUsername());
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            return statement.executeUpdate() == 0 ? Optional.empty() : Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
