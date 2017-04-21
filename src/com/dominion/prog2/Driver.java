package com.dominion.prog2;

import com.dominion.prog2.modules.ChooseName;
import com.dominion.prog2.modules.Module;
import com.dominion.prog2.network.NodeCommunicator;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CARLINSE1 on 4/20/2017.
 */
public class Driver extends Application {
    public String name;
    public NodeCommunicator comm;

    private Module currentModule;
    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dominion");
        primaryStage.show();

        setCurrentModule(new ChooseName(this));

        comm = new NodeCommunicator();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), a -> pingServer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void pingServer() {
        ArrayList<HashMap<String, String>> server_msg = null;

        if(name != null && !name.equals("")) {
            //Ping server
            HashMap<String, String> msg = new HashMap<>();
            msg.put("type", "ping");
            msg.put("name", name);

            String json = comm.getMessage(comm.mapToJSON(msg));

            if(!json.equals("Error")) {
                server_msg = comm.JSONToMap(json);
            }
        }

        currentModule.serverMsg(server_msg);
    }

    public void setCurrentModule(Module m) {
        currentModule = m;

        primaryStage.setScene(m.getScene());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
