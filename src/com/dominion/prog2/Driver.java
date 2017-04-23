package com.dominion.prog2;

import com.dominion.prog2.modules.*;
import com.dominion.prog2.network.NodeCommunicator;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;


public class Driver extends Application {
    public String name;
    public NodeCommunicator comm;

    private Module currentModule;
    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dominion");

        setCurrentModule(new ChooseName(this));
        primaryStage.show();

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

        // Changes window height based off Module
        int w = (int)(primaryStage.getWidth());

        if((currentModule instanceof ChooseName || currentModule instanceof ChooseLobby) && w != 500)
            setWindow(500, 700);
        else if((currentModule instanceof WaitScreen || currentModule instanceof HostWaitScreen) && w != 745)
            setWindow(745, 700);
        else if(currentModule instanceof Game && w != 1800)
            setWindow(1800, 1100);


        primaryStage.setScene(m.getScene());
    }

    public void setWindow(int width, int height)
    {
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
