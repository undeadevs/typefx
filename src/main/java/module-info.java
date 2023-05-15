module com.undeadevs {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.undeadevs to javafx.fxml;
    exports com.undeadevs;
}
