package mypackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/loginForm.fxml"));
        primaryStage.setTitle("Облік картриджів");
        primaryStage.setScene(new Scene(root, 290, 320));
        primaryStage.getIcons().add(new Image("file:resources/images/printer2.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
