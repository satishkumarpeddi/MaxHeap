module com.example.maxheap {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.maxheap to javafx.fxml;
    exports com.example.maxheap;
}