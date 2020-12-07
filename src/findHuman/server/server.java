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
    private int numberOfPlayers = 0; // 2 human players 1 bot // Initializing to 0, incrementing to keep track of playe rnumbers
    private int  threadCount = -1; 
    private static final int maxPlayers = 3; // final int to keep track of how many players can be in the game (might be 2 instead of 3)
    private boolean[] threadsToBeHandled = new boolean[maxPlayers]; 
    private boolean[] threadsHandled = new boolean [maxPlayers];  
    // private int threadsHandled = 0; // might not need an array of Booleans 
    // private int threadsToBeHandled = 0; 
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
	while (numberOfPlayers < maxPlayers) {
	
        	try {
            		Socket socket = serverSocket.accept();
            		player newPlayer = new player(socket, "fill");
            		//Thread newPlayerThread = new Thread(newPlayer);
	    		numberOfPlayers++; 
            		//newPlayerThread.start();
        	} catch (IOException e) {
            		e.printStackTrace();
        	}
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

    class PlayerMultiThread {
	    int currentThread;
	    Socket client;  
	public PlayerMultiThread(Socket client) {
		currentThread = getThread(); 
		threadsToBeHandled[currentThread] = true;
	        threadsHandled[currentThread] = false; // not sure if two arrays are needed? Wondering if you could just use this array by itself
		this.client = client; 
	}
	private synchronized int getThread() {
		threadCount++; 
		return threadCount; 
	}
    }


}
