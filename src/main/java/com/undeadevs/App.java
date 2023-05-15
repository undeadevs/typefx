package com.undeadevs;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    final private String shiftString ="aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ`~1!2@3#4$5%6^7&8*9(0)-_=+[{]}\\|;:'\",<.>/?";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        scene = new Scene(fxmlLoader.load(), 640, 480);
        MainController controller = fxmlLoader.getController();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                String keyPressedString = event.getText();
                if((keyPressedString.equals("") || event.getCode()==KeyCode.ENTER) && event.getCode()!=KeyCode.BACK_SPACE) return;
                if(event.isShiftDown() && event.getCode()!=KeyCode.BACK_SPACE){
                    int shiftIndex = shiftString.indexOf(keyPressedString, 0);
                    if(shiftIndex>=0){
                        keyPressedString=shiftString.charAt(shiftIndex+1)+"";
                    }
                }
                controller.checkKey(keyPressedString);
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}