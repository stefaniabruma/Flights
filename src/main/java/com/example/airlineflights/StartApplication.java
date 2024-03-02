package com.example.airlineflights;

import com.example.airlineflights.Domain.Client;
import com.example.airlineflights.Domain.Flight;
import com.example.airlineflights.Domain.Ticket;
import com.example.airlineflights.Repository.ClientDBRepository;
import com.example.airlineflights.Repository.FlightDBRepository;
import com.example.airlineflights.Repository.Repository;
import com.example.airlineflights.Repository.TicketDBRepository;
import com.example.airlineflights.Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    Service serv;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        String url = "jdbc:postgresql://localhost:5432/zboruri";
        String username = "postgres";
        String password = "postgres";

        Repository<Long, Client> repo_clients = new ClientDBRepository(url, username, password);
        Repository<Long, Flight> repo_flights = new FlightDBRepository(url, username, password);
        Repository<Long, Ticket> repo_tickets = new TicketDBRepository(url, username, password);

        serv = new Service(repo_clients, repo_flights, repo_tickets);

        initView(stage);
        stage.show();
    }

    private void initView(Stage stage) throws IOException {

        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("login-view.fxml"));
        AnchorPane userLayout = loginLoader.load();
        stage.setScene(new Scene(userLayout));
        stage.setTitle("LogIn");

        LoginController loginController = loginLoader.getController();
        loginController.setServ(serv);
    }
}