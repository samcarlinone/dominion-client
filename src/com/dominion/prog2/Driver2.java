package com.dominion.prog2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Created by CARLINSE1 on 4/20/2017.
 */
public class Driver2 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Dominion");

        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(120, 120);
        root.getChildren().add(s1);

        FlowPane scroll = new FlowPane();
        scroll.setPrefWrapLength(100);
        s1.setContent(scroll);

        for(int i=0; i<10; i++) {
            Button btn = new Button("Sign in "+i);
            //btn.setTranslateY(i*40);
            scroll.getChildren().add(btn);
        }

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
