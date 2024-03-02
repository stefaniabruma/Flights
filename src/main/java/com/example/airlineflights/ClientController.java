package com.example.airlineflights;

import Utils.Observer;
import com.example.airlineflights.Domain.Client;
import com.example.airlineflights.Domain.Flight;
import com.example.airlineflights.Domain.Ticket;
import com.example.airlineflights.Service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientController implements Observer {

    private Service serv;
    private Client client;
    ObservableList<Ticket> model = FXCollections.observableArrayList();
    ObservableList<Ticket> model2 = FXCollections.observableArrayList();
    ObservableList<Flight> model3 = FXCollections.observableArrayList();
    ObservableList<String> model4 = FXCollections.observableArrayList();
    ObservableList<String> model5 = FXCollections.observableArrayList();
    @FXML
    private TableView<Ticket> ticketsTableView;
    @FXML
    private TableColumn<Ticket, Long> flightIdColumn;
    @FXML
    private TableColumn<Ticket, LocalDateTime> purchaseTimeColumn;

    @FXML
    private TableView<Ticket> adTableView;
    @FXML
    private TableColumn<Ticket, Long> flightIdAdColumn;
    @FXML
    private TableColumn<Ticket, Long> purchaseTimeAdColumn;

    @FXML
    private TableView<Flight> flightsTableView;
    @FXML
    private TableColumn<Flight, String> fromColumn;
    @FXML
    private TableColumn<Flight, String> toColumn;
    @FXML
    private TableColumn<Flight, LocalDateTime> departureColumn;
    @FXML
    private TableColumn<Flight, LocalDateTime> landingColumn;
    @FXML
    private TableColumn<Flight, Integer> seatsColumn;
    @FXML
    private TableColumn<Flight, String> avColumn;
    @FXML
    private TableColumn<Flight, Long> idFlightsColumn;

    @FXML
    private ComboBox<String> fromComboBox;
    @FXML
    private ComboBox<String> toComboBox;
    @FXML
    private DatePicker departureDayDatePicker;

    void setServ(Service serv, Client client){
        this.serv = serv;
        this.client = client;

        serv.addObserver(this);

        initModel();
    }
    private void initModel() {

        model.setAll(serv.ticketsByClientUsername(client.getUsername()));
        model2.setAll(serv.ticketsSoldFor24thJan());
        handleChange();
        model4.setAll(serv.getAllFrom());
        model5.setAll(serv.getAllTo());
    }

    @FXML
    void initialize(){
        flightIdColumn.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        purchaseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseTime"));

        ticketsTableView.setItems(model);

        flightIdAdColumn.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        purchaseTimeAdColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseTime"));

        adTableView.setItems(model2);

        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departure"));
        landingColumn.setCellValueFactory(new PropertyValueFactory<>("landingtime"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        avColumn.setCellValueFactory(f -> new SimpleStringProperty(String.valueOf(f.getValue().getSeats() - serv.nrBileteCumparate(f.getValue().getId()))));
        idFlightsColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        flightsTableView.setItems(model3);

        fromComboBox.setItems(model4);
        fromComboBox.itemsProperty().addListener(o -> handleChange());

        toComboBox.setItems(model5);
        toComboBox.valueProperty().addListener(o -> handleChange());

        departureDayDatePicker.valueProperty().addListener(o -> handleChange());

    }

    private void handleChange() {

        String from = fromComboBox.getValue();
        String to = toComboBox.getValue();
        LocalDate date = departureDayDatePicker.getValue();

        if(from != null && to != null && date != null)
            model3.setAll(serv.filtered(from, to, date));
        else
            model3.setAll(serv.getAllFlights());
    }

    @FXML
    void handleBuy(){

        Flight f = flightsTableView.getSelectionModel().getSelectedItem();

        if(f !=  null){
            serv.adaugaBilet(f.getId(), client.getUsername());
        }
    }

    @Override
    public void update() {
        initModel();
    }
}
