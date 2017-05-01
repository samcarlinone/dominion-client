package com.dominion.prog2;

import com.dominion.prog2.modules.*;
import com.dominion.prog2.network.NodeCommunicator;
import com.dominion.prog2.ui.ImageCache;
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
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dominion");
        primaryStage.centerOnScreen();

        setCurrentModule(new ChooseName(this));
        primaryStage.show();

        ImageCache.readImages(this);

        comm = new NodeCommunicator();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), a -> pingServer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void pingServer() {
        ArrayList<HashMap<String, String>> server_msg;

        if(name != null && !name.equals("")) {
            //Ping server
            HashMap<String, String> msg = new HashMap<>();
            msg.put("type", "ping");
            msg.put("name", name);

            String json = comm.getMessage(comm.mapToJSON(msg));

            if(json.equals("Error")) {
                return;
            }

            if(json.equals("<html><body>Cogito ergo sum.</body></html>")) {
                System.err.println("You sent an invalid message!");
                System.exit(2);
            }

            server_msg = comm.JSONToMap(json);

            if(server_msg.size() > 0) {
                if (server_msg.get(0).get("type").equals("disconnected")) {
                    System.exit(1);
                }

                if (server_msg.get(0).get("type").equals("removed")) {
                    setCurrentModule(new ChooseLobby(this, server_msg.get(0).get("reason")));
                }

                currentModule.serverMsg(server_msg);
            }
        }
    }

    public HashMap<String, String> simpleCommand(String type, String... strings) {
        HashMap<String, String> join_msg = new HashMap<>();
        join_msg.put("type", type);
        join_msg.put("name", name);

        if(strings != null) {
            for(int i=0; i<strings.length; i+=2) {
                join_msg.put(strings[i], strings[i+1]);
            }
        }

        String json = comm.getMessage(comm.mapToJSON(join_msg));
        return comm.JSONToMap(json).get(0);
    }

    public void broadcast(HashMap<String, String> data) {
        String msg = comm.escapeJSON(comm.mapToJSON(data));
        if(simpleCommand("broadcast", "msg", msg).get("type").equals("rejected")) {
            System.err.println("Invalid Message");
        }
    }

    public void setCurrentModule(Module m) {
        currentModule = m;

        primaryStage.setScene(m.getScene());
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
