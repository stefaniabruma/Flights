module com.example.mancare {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.airlineflights to javafx.fxml;
    opens com.example.airlineflights.Domain to javafx.base;
    exports com.example.airlineflights;
}