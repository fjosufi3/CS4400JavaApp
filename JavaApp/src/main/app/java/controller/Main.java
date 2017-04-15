package app.java.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/main/app/java/view/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //Parent root = FXMLLoader.load(getClass().getResource("/main.app/res/pending_data_points.fxml"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
//        ConnectionConfiguration con = new ConnectionConfiguration();
//        con.getConnection();
    }


}
