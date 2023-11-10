module com.example.foodiesfavetask4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.foodiesfavetask4 to javafx.fxml;
    exports com.example.foodiesfavetask4;
}