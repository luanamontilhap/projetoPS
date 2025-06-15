module com.app.calingaertextend {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.app.calingaertextend to javafx.fxml;
    exports com.app.calingaertextend;
}