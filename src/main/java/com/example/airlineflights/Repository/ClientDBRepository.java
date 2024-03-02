package com.example.airlineflights.Repository;

import com.example.airlineflights.Domain.Client;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ClientDBRepository implements Repository<Long, Client> {

    protected String url;
    protected String username;
    protected  String password;

    public ClientDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Client> findAll() {
        Set<Client> clients = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from clients")) {

            ResultSet result = statement.executeQuery();

            while(result.next()){

                Long id = result.getLong("id");
                String user = result.getString("username");
                String nume = result.getString("name");

                Client client = new Client(user, nume);
                client.setId(id);

                clients.add(client);
            }

            return clients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> save(Client entity) {
        return Optional.empty();
    }
}
