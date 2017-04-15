package src.app.java.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/src/app/java/view/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //Parent root = FXMLLoader.load(getClass().getResource("/app/res/pending_data_points.fxml"));


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
//        ConnectionConfiguration con = new ConnectionConfiguration();
//        con.getConnection();
    }


}
