package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class gameServer {
    private static ServerSocket server_Socket;
    private static final int PORT = 1234;
    private static final int MAX_NUM_PLAYERS = 2;
    private static final int MAX_ROUNDS = 3;
    private static final int MAX_SCORE = 2;

    private int playerTurn;
    private Boolean gameOver;
    private gameLogic logic;
    private DataInputStream[] clientInputs;
    private DataOutputStream[] clientOutputs;

    //shared objects
    private String[] playerNames;
    private String[] playerType;
    private int threadCount = -1;

    ///////////////////////////////////////////////////////////
    public static void main(String[] args) {
        try {
            gameServer game_Server = new gameServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////
    public gameServer() throws IOException {

        //initialize shared objects
        playerNames = new String[MAX_NUM_PLAYERS];
        playerType = new String[MAX_NUM_PLAYERS];
        playerType[0] = "HIDE";
        playerType[1] = "GUESS";
        logic = new gameLogic();
        clientInputs = new DataInputStream[MAX_NUM_PLAYERS];
        clientOutputs = new DataOutputStream[MAX_NUM_PLAYERS];
        int playerCount = 0;
        gameOver = false;
        boolean finished;

        try {
            server_Socket = new ServerSocket(PORT);
        } catch (IOException ioEx) {
            System.out.println("Unable to set up port!");
            System.exit(1);
        }

        // main loop //
        while (true) {

            // either we have enough players or we don't
            while (playerCount < MAX_NUM_PLAYERS) {
                clientHandler handler = null;
                System.out.println("Waiting for new players...");
                Socket client = server_Socket.accept();
                System.out.println("New Player Accepted");

                if(playerCount == 0){
                    handler = new clientHandler(client, playerType[0]);
                }else {
                    handler = new clientHandler(client, playerType[1]);
                }

                playerCount++;
                handler.start();
            }

            // yay we have enough people let's get started
            System.out.println("Starting game...");
            while (playerCount == MAX_NUM_PLAYERS) {

                //this block loops until every user has successfully negotiated username
                finished = false;
                int count = 0;
                do {
                    finished = true;
                    for (int i = 0; i < MAX_NUM_PLAYERS; i++) {
                        if (playerNames[i] == null) {
                            finished = false;
                        }
                    }
                } while (!finished);

                //initialize the first round
                // Get a new question player/ client guessing & hidden //
                System.out.println(logic.generateRandomQuestion());
                playerTurn = -1;

                //this part can loop for all normal rounds //
                do {

                    playerTurn++;
                    if(playerTurn == MAX_NUM_PLAYERS){
                        playerTurn = 0;
                    }

                    System.out.print(playerNames[playerTurn]);

                    // game logic here //


                } while (!gameOver);


                // In the case of game over//
                if (!gameOver) {


                }


            }


        }


    }

    class clientHandler extends Thread {
        private Socket client;
        private DataInputStream input;
        private DataOutputStream output;
        private int dataConnPort;
        private String command;
        private String received;
        private int dataInputPort = 1236;
        private String name;
        private String type;
        private int myThreadNumber;

        public clientHandler(Socket socket, String playerType) {
            myThreadNumber = getMyThreadNumber();
            client = socket;
            type = playerType;

            try {
                /* get input/output streams from the client socket
                 * These represent the i/o for the persistent command connection */
                input = new DataInputStream(client.getInputStream());
                output = new DataOutputStream(client.getOutputStream());

                // give inputs and outputs to the respective shared objects
                //    so master server can access them
                clientInputs[myThreadNumber] = input;
                clientOutputs[myThreadNumber] = output;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            //negotiate username
            System.out.println("Getting name for new user...");

            try {
                name = input.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!usernameAccepted(name, myThreadNumber)) {
                try {
                    output.writeUTF("TAKEN");
                    name = input.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                output.writeUTF("ACCEPTED");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("New Player " + name + " has joined. Type: " + type);


            while (!gameOver) {

                // Game logic here //
                // implement methods //

            }


        }

        private synchronized int getMyThreadNumber() {
            threadCount++;
            return threadCount;
        }

    }

    private synchronized boolean usernameAccepted(String userName, int threadNumber) {
        for (int i = 0; i < playerNames.length; i++) {
            if (playerNames[i] != null) {
                if (playerNames[i].equalsIgnoreCase(userName))
                    //name has been taken; return false
                    return false;
            }
        }

        //name has not been taken; write in name; return true
        playerNames[threadNumber] = userName;
        return true;
    }
}
