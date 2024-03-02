package com.example.airlineflights;

import com.example.airlineflights.Domain.Client;
import com.example.airlineflights.Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Service serv;
    @FXML
    private TextField usernameTextField;

    void setServ(Service serv) {
        this.serv = serv;
    }

    @FXML
    void handleLogin(ActionEvent ev) {

        String username = usernameTextField.getText();
        usernameTextField.setText("");

        var op_client = serv.findByUsername(username);

        if(op_client.isPresent()){
            showClientWindow(op_client.get());
        }
    }

    private void showClientWindow(Client client) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("client-view.fxml"));

            AnchorPane root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Client " + client.getName());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ClientController clientController = loader.getController();
            clientController.setServ(serv, client);

            dialogStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}