module com.example.lebo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.lebo to javafx.fxml;
    exports com.example.lebo;
}