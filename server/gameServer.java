/**********************************************************************************
 * Chesten VanPelt
 * Tonae Patterson
 * Aaron HaNasi
 * Spencer Mueller
 * CIS 457 Project 3 - Final
 * Dr. El-Said
 * The gameServer class will create a server that the 2 clients can connect 2 and will
 * control data through data sockets and buffered input and output streams in order to
 * communicate with the clients in order to let them know whose turn it is, what they
 * should be doing, game states, and who won or lost. The server will dipslay messages
 * that will help with keep track of who is winning and if the moves are legal to say
 * a gamekeeper and updates both clients when needed. It also shows who won and who
 * lost and sends them that information as well.
 *********************************************************************************/
package server;
//imports
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.System.exit;
/***********************************************************************************
 * gameServer will create a server that the 2 clients can connect 2 and will control
 * data through data sockets and buffered input and output streams in order to
 * communicate with the clients in order to let them know whose turn it is,
 * what they should be doing, game states, and who won or lost.
 **********************************************************************************/
public class gameServer{
    /**
     * server_socket used to create server connection.
     */
    private static ServerSocket server_Socket;
    /**
     * PORT used to connect server to port.
     */
    private static final int PORT = 1234;
    /**
     * MAX_NUM_PLAYERS used to cap the number of players who can play.
     */
    private static final int MAX_NUM_PLAYERS = 2;
    /**
     * MAX_ROUNDS used to cap the number of rounds.
     */
    private static final int MAX_ROUNDS = 5;
    /**
     * MAX_SCORE used to cap the score.
     */
    private static final int MAX_SCORE = 3;
    /**
     * playerTurn that keeps track of turns.
     */
    private int playerTurn;
    /**
     * gameOver that keeps track of if game is over or ongoing.
     */
    private Boolean gameOver;
    /**
     * logic used to generate questions, phrases, check answers, and check whose turn it is.
     */
    private gameLogic logic;
    /**
     * clientInputs used to read from client to server.
     */
    private DataInputStream[] clientInputs;
    /**
     * clientOutputs used to write from server to client.
     */
    private DataOutputStream[] clientOutputs;
    /**
     * waiting used to check if waiting.
     */
    private Boolean[] waiting;
    /**
     * done used to check if done.
     */
    private Boolean[] done;
    /**
     * list used to create list of bot phrases.
     */
    private ArrayList<String> list = new ArrayList<>();
    /**
     * newList used to create new list with the random order.
     */
    private ArrayList<String> newList = new ArrayList<>();
    /**
     * randomQuestion used for the question asked to the clients.
     */
    private String randomQuestion;
    /**
     * humanGuess used as the human client's phrase
     */
    private String humanGuess;
    /**
     * playerGuess used as the czar client's guess
     */
    private String playerGuess;
    /**
     * playerGuessInt used as the number the czar chose.
     */
    private int playerGuessInt;
    /**
     * humanGuessInt used as the number the answer should be.
     */
    private int humanGuessInt;
    /**
     * round used to keep track of what round it is.
     */
    private int round;
    /**
     * score used to keep track of score.
     */
    private int score;
    /**
     * who used to keep track of who won.
     */
    private int who;
    /**
     * playerNames used to keep track of player names.
     */
    private String[] playerNames;
    /**
     * playerType used to keep track of type, HIDE or GUESS.
     */
    private String[] playerType;
    /**
     * playerScores to keep track of each player's score
     */
    private int[] playerScores = new int[3];
    /**
     * threadCount to keep track of thread counts;
     */
    private int threadCount = -1;
    /******************************************
     * main used to create a new server object.
     * @param args command line arguments
     *****************************************/
    public static void main(String[] args)  {
        //try to create a new server while catching exceptions
        try {
            gameServer game_Server = new gameServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /***********************************************************************
     * gameServer that contains all the game logic and controls the clients,
     * sending and receiving data to and from the clients.
     * @throws IOException
     **********************************************************************/
    public gameServer() throws IOException{
        //initialize shared objects
        playerNames = new String[MAX_NUM_PLAYERS];
        playerType = new String[MAX_NUM_PLAYERS];
        playerType[0] = "HIDE";
        playerType[1] = "GUESS";
        logic = new gameLogic();
        clientInputs = new DataInputStream[MAX_NUM_PLAYERS];
        clientOutputs = new DataOutputStream[MAX_NUM_PLAYERS];
        waiting = new Boolean[MAX_NUM_PLAYERS];
        done = new Boolean[MAX_NUM_PLAYERS];
        int playerCount = 0;
        gameOver = false;
        boolean finished;
        //try to setup new server socket, catching IO Exceptions
        try {
            server_Socket = new ServerSocket(PORT);
        } catch (IOException ioEx) {
            System.out.println("Unable to set up port!");
            exit(0);
        }
        // main loop
        while (true) {
            //either we have enough players or we don't
            while (playerCount < MAX_NUM_PLAYERS) {
                clientHandler handler = null;
                System.out.println("Waiting for new players...");

                if (playerCount == 0) {
                    handler = new clientHandler(server_Socket.accept(), playerType[0]);
                } else {
                    handler = new clientHandler(server_Socket.accept(), playerType[1]);
                }
                //new player was accepted, increment number of players and start the client handler
                System.out.println("New Player Accepted");
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
                //spacing new line
                System.out.println();
                //this part can loop for all normal rounds
                do {
                    //for every player/client
                    for(int i = 0; i < MAX_NUM_PLAYERS; i++) {
                        //czar loop (changing guessingClient each "round")
                        for (DataOutputStream out : clientOutputs) {
                            //writes to all who the czar is
                            out.writeUTF(playerNames[i]);
                        }
                        //random question is retrieved from gameLogic class
                        randomQuestion = logic.generateRandomQuestion();
                        //for getting responses from clients who aren't czars
                        for (int j = 0; j < MAX_NUM_PLAYERS; j++) {
                            //because i is the czar, dont they dont get a turn, or a response
                            if (j != i) {
                                for (DataOutputStream out : clientOutputs)
                                    //remind all users what question is
                                    out.writeUTF(randomQuestion);
                                for (DataOutputStream out : clientOutputs)
                                    //tell everyone whos turn it is
                                    out.writeUTF(logic.whoseTurn(j));
                                //read just from j user
                                humanGuess = clientInputs[j].readUTF();
                                //add response to list
                                list.add(humanGuess);
                            }
                        }
                        //bot answers
                        list.add(logic.generateRandomResponse());
                        list.add(logic.generateRandomResponse());
                        list.add(logic.generateRandomResponse());
                        //create a random integer from 0 to 3
                        Random randInt = new Random();
                        int randy = randInt.nextInt(3);
                        for (DataOutputStream out : clientOutputs)
                            //tell everyone it's the czars turn
                            out.writeUTF(logic.whoseTurn(i));
                        //add each random position of list to a new list called newList
                        for (int r = 0; r < 4; r++) {
                            //if random is 4, which is out of bounds, set it to 0
                            if (randy > 3) {
                                randy = 0;
                            }
                            //write this order to the client fromm the server
                            clientOutputs[i].writeUTF(list.get(randy));
                            //tell which one is the correct answer
                            if (list.get(randy).equals(humanGuess)) {
                                humanGuessInt = (r + 1);
                            }
                            //add each position to the new list
                            newList.add(list.get(randy));
                            randy++;
                        }
                        //player guess number is read from client as well as the actual guess string itself
                        playerGuessInt = Integer.parseInt(clientInputs[i].readUTF()) - 1;
                        playerGuess = newList.get(playerGuessInt);
                        //does not get to this print statement.....
                        System.out.println("Czar " + playerNames[i] + " guessed option: " + (playerGuessInt + 1) + " for the message: " + playerGuess);
                        //see if it is correct, increment score if so
                        if (logic.isCorrect(playerGuess, humanGuess)) {
                            clientOutputs[i].writeUTF("Correct");
                            score++;
                        }
                        //if not, return the actual answer
                        else {
                            clientOutputs[i].writeUTF("Incorrect");
                            clientOutputs[i].writeUTF(String.valueOf(humanGuessInt));
                        }
                        //set this player's score to what we get from the client's score
                        playerScores[i] = Integer.valueOf(clientInputs[i].readUTF());
                        //this will give all player's clients the scores
                        for(int a = 0; a < MAX_NUM_PLAYERS; a++) {
                            clientOutputs[i].writeUTF(String.valueOf(playerScores[a]));
                        }
                        //give round to clients
                        clientOutputs[i].writeUTF(String.valueOf(round));
                        //if max round is met or player go to round 3 without getting a single point, end game
                        if ((round == 5) || (playerScores[i] >= 3)){
                            score = playerScores[i];
                            gameOver = true;
                            break;
                        }
                        //clear the array lists and flush clientOutputs buffer
                        list.clear();
                        newList.clear();
                        clientOutputs[i].flush();
                        }
                    //increment round
                    round++;
                    //while the game is still going
                    } while (!gameOver);
                //In the case of game over
                if (gameOver) {
                    System.out.println();
                    //look for who won
                    for(int n = 0; n < MAX_NUM_PLAYERS; n++) {
                        if (playerScores[n] >= 3) {
                            //write to server the winner and their name
                            System.out.println("Congratulations " + playerNames[n] + ", you win!");
                            clientOutputs[n].writeUTF("Winner");
                            clientOutputs[n].writeUTF(playerNames[n]);
                        } else {
                            //write to server the loser and their name
                            System.out.println("Sorry " + playerNames[n] + ", you lose!");
                            clientOutputs[n].writeUTF("Loser");
                        }
                    }
                    //tell anyone on server it was a pleasure playing
                    System.out.println();
                    System.out.println("Thanks for playing!");
                    exit(0);
                }
            }
        }
    }
    /*******************************************
     * waitForThreads will wait for any threads.
     ******************************************/
    private void waitForThreads() {
        //if somebody isnt done
        Boolean somebodyIsntDone;
        do {
            somebodyIsntDone = false;
            //if the thread is not done, somebody isnt done
            for (int thread = 0; thread < MAX_NUM_PLAYERS; thread++) {
                if (done[thread] == false)
                    somebodyIsntDone = true;
            }
            //wait for the person to be done
        } while (somebodyIsntDone);
    }
    /**************************************************************
     * A small method which tells all the threads they may progress
     *************************************************************/
    private void unleashThreads() {
        for (int thread = 0; thread < MAX_NUM_PLAYERS; thread++) {
            done[thread] = false;
            waiting[thread] = false;
        }
    }
    /***************************************************
     * clientHandler class will hand the client threads.
     **************************************************/
    class clientHandler extends Thread {
        /**
         * client socket for the client connection.
         */
        private Socket client;
        /**
         * input data stream for the client.
         */
        private DataInputStream input;
        /**
         * output data stream for the client.
         */
        private DataOutputStream output;
        /**
         * command for any commands.
         */
        private String command;
        /**
         * received commands for any receive commands.
         */
        private String received;
        /**
         * name for the name of the client.
         */
        private String name;
        /**
         * type for the type of client the client will be each turn.
         */
        private String type;
        /**
         * myThreadNumber used to keep track of number of threads.
         */
        private int myThreadNumber;
        /*******************************************************************
         * clientHandler will take the socket and the player type and handle
         * what the server should do with the client.
         * @param socket server socket
         * @param playerType GUESS or HIDE player type
         ******************************************************************/
        public clientHandler(Socket socket, String playerType) {
            //thread variables and client and type variables
            myThreadNumber = getMyThreadNumber();
            waiting[myThreadNumber] = true;
            done[myThreadNumber] = false;
            client = socket;
            type = playerType;
            try {
                /* get input/output streams from the client socket
                 * These represent the i/o for the persistent command connection */
                input = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                output = new DataOutputStream(client.getOutputStream());
                // give inputs and outputs to the respective shared object so master server can access them
                clientInputs[myThreadNumber] = input;
                clientOutputs[myThreadNumber] = output;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*******************************************************************
         * run used to get each client's username and connect them to sever.
         ******************************************************************/
        public void run() {
            //get user name
            System.out.println("Getting name for new user...");
                //set name to the client input
                try {
                    name = input.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //if the name is not accepted, tell the user it is taken
                while (!usernameAccepted(name, myThreadNumber)) {
                    try {
                        output.writeUTF("TAKEN");
                        name = input.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //otherwise the username was accepted
                try {
                    output.writeUTF("ACCEPTED");
                    //if thread 0, set self to player 1
                    if(myThreadNumber == 0){
                        logic.setPlayer1(name);
                    }
                    //if thread 1, set self to player 2
                    else if(myThreadNumber == 1){
                        logic.setPlayer2(name);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Tell the server the new player has joined
                System.out.println("New Player " + name + " has joined. Type: " + type);
                    //wait for main
                    waitForMain();
                    done[myThreadNumber] = true;
                    waitForMain();
                    done[myThreadNumber] = true;
                    waitForMain();
                    done[myThreadNumber] = true;
                    waitForMain();
                    done[myThreadNumber] = true;
                    waitForMain();
                    done[myThreadNumber] = true;
                    waitForMain();
                    done[myThreadNumber] = true;
                    waitForMain();
                    done[myThreadNumber] = true;
                    waitForMain();
                    done[myThreadNumber] = true;
        }
        /******************************************************************************
         * getMyThreadNumber will increment thread number and return the thread number.
         * @return thread number which is thread count
         ****************************************************************************/
        private synchronized int getMyThreadNumber() {
            //increment thread count and return it
            threadCount++;
            return threadCount;
        }
        /************************************************************
         * waitForMain will tell that thread number to wait for main.
         ***********************************************************/
        private void waitForMain() {
            //while waiting
            while (waiting[myThreadNumber]) {
            }
            ;
            // prep for next wait
            waiting[myThreadNumber] = true;
        }
    }
    /*****************************************************************************
     * userNameAccpeted will check to see if the name is not a duplicate or taken.
     * @param userName the user's name
     * @param threadNumber the thread number
     * @return if the username is accepted or not
     ****************************************************************************/
    private synchronized boolean usernameAccepted(String userName, int threadNumber) {
        //check each of the player's names
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
    /************************************************
     * getUserName will return the player's username.
     * @param i user id (probably 0 or 1)
     * @return the player's name
     ***********************************************/
    public String getUserName(int i){
        return playerNames[i];
    }
}
