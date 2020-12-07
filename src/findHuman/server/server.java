package findHuman.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Class not nearly complete just set up for testing GUI etc //
public class server {

    private static final int DEFAULT_PORT = 44444;
    private gameLogic gameLogic;
    private int serverPort;
    private int numberOfRounds = 7;
    private int numberOfPlayers = 5; // 2 human players 1 bot


    public server(int serverPort) {
        this.serverPort = serverPort;
    }

    public void startConnection() {
        System.out.println("Starting Find-the-Human server\nServer port: " + serverPort);
        ServerSocket serverSocket = null;

        try {
            System.out.println("Creating server socket");
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.err.println("Could not start Find-the-Human server on port " + serverPort);
            System.exit(1);
        }

        try {
            Socket socket = serverSocket.accept();
            player newPlayer = new player(socket, "fill");
            Thread newPlayerThread = new Thread(newPlayer);
            newPlayerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        int serverPort = DEFAULT_PORT;

        for (int i = 0; i < args.length; i += 2) {
            String option = args[i];
            String argument = null;

            try {
                serverPort = Integer.parseInt(argument);
            } catch (NumberFormatException e) {
                System.err.println("Server port must be an integer");
                System.exit(1);
            }
        }

        server server = new server(serverPort);
        server.startConnection();
    }


}
