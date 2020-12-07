package findHuman.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

    private static final int DEFAULT_PORT = 44444;
    private gameLogic gameLogic;
    private int serverPort;
    private int numberOfRounds = 7;
    private int numberOfPlayers = 5; // 2 human players 1 bot


    public server(int serverPort){
        this.serverPort = serverPort;
    }

    public void startConnection(){
        System.out.println("Starting Find-the-Human server\nServer port: " + serverPort);
        ServerSocket serverSocket = null;

        try {
            System.out.println("Creating server socket");
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.err.println("Could not start Find-the-Human server on port " + serverPort);
            System.exit(1);
        }



    }



}
