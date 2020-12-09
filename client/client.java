/**********************************************************************************
 * Chesten VanPelt
 * Tonae Patterson
 * Aaron HaNasi
 * Spencer Mueller
 * CIS 457 Project 3 - Final
 * Dr. El-Said
 * The client class is the user view of the project and takes two clients that
 * connect to the server. The first client connected will become the card czar
 * and the second one will be the first client to enter their 3 word pharse
 * composed of a noun, verb, and subject. Then the card czar will have to guess
 * which one was the real human and pick it out from 3 other bots who created
 * their own phrases. If the czar gets the choice correct, they get a point,
 * otherwise they get nothing. Then the roles switch. Up to 5 rounds, first to 3
 * points win. If no client wins after 5 rounds, they both lose.
 *********************************************************************************/
package client;
//imports
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.time.LocalTime;
import static java.lang.System.exit;
/******************************************************************************
 * client class will display to the user the view of the czar and/or the person
 * creating the random phrase. It will change after every round, first to 3 or
 * up to 5 rounds. It will check for a winner at a score of 3 and if nobody won,
 * then everybody lost.
 ******************************************************************************/
public class client {
    /**
     * PORT used to connect to the server.
     */
    private static final int PORT = (1236 + LocalTime.now().getSecond() + LocalTime.now().getMinute() );
    /******************************************
     * mian mehtod used to run the client view.
     * @param args command line arguments
     *****************************************/
    public static void main(String[] args) {
        //server socket
        Socket server = null;
        //message, command, and server name
        String message, command, serverName;
        //used for user entry from command line
        BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
        //if the name is accepted
        boolean nameAccepted = false;
        //card czar
        boolean czar=false;
        //score of the game
        int score = 0;
        //each user score
        int[] userScore = new int[2];
        //rounds of the game
        int round = 0;
        //counter1 for turn purposes
        int counter1 = 0;
        //counter2 also for turn purposes
        int counter2 = 0;
        //response from server
        String responseToString;
        //who the czar is
        String CzarUser;
        //whose turn it is
        String playerTurn;
        //the answer that was selected
        String answerSelected;
        //the question that was selected
        String question = "";
        //the winner of the game
        String roundOver;
        //who the user of this client is
        String username = "";
        //array list for options of phrases
        ArrayList<String> answerOptions = new ArrayList<>();
        //displays opening welcome message
        try {
            do {
                System.out.println("Welcome to Find the Human Online");
                //get the user entry
                message = userEntry.readLine();
                StringTokenizer tokens = new StringTokenizer(message);
                command = tokens.nextToken();
                /////////////////////////////////////////////////////////////////////////////////////////
                //if the command is join
                if (command.equals("JOIN")) {
                    if (server != null) {
                        //if already a connection
                        System.out.println("A connection with the game has already been established");
                        continue;
                    }
                    //otherwise give server name and port, set up data stream, and establish connection
                    serverName = "localhost";
                    server = new Socket(serverName, 1234);
                    DataInputStream serverInput = new DataInputStream(server.getInputStream());
                    DataOutputStream serverOutput = new DataOutputStream(server.getOutputStream());
                    System.out.println("Connection with " + serverName + " has been established");

                    /////////////////////////////////////////////////////////////////////////////////////
                    //enter ussername
                    System.out.println("Please enter a username...");
                    while (nameAccepted == false) {
                        // get input & send to server
                        username = userEntry.readLine();
                        serverOutput.writeUTF(username);
                        //check available
                        String serverResponse = serverInput.readUTF();
                        //if username is accepted
                        if (serverResponse.equals("ACCEPTED")) {
                            System.out.println("Your username has been accepted. You are: " + username);
                            nameAccepted = true;
                            //if username is taken
                        } else if (serverResponse.equals("TAKEN")) {
                            System.out.println("This username has already been taken. Please enter another username");
                            continue;
                        } else {
                            //if neither and there is some weird error
                            System.out.println("An error has occurred. Please enter another username");
                            continue;
                        }
                    }
                    /////////////////////////////////////////////////////////////////////////////////////
                    System.out.println("Entering game...");
                    //since we don't have "commands" like in the last project
                    //we will want to do a big while loop that the game stuff runs in instead
                    do {
                    //if it is round 5 or score of 3
                        if(round == 5 || userScore[0] >= 3 || userScore[1] >= 3) {
                            //tell the user if they are a winner or loser
                            if (serverInput.readUTF().equals("Winner")) {
                                roundOver = serverInput.readUTF();
                                System.out.println(roundOver + ", you are the winner!!");
                                exit(0);

                            }
                            else if(serverInput.readUTF().equals("Loser")) {
                                System.out.println(username + ", you are a giant loser!");
                                exit(0);
                            }
                        }
                        //if the user should update who the card czar is or not
                        if(counter1 == counter2) {
                            //who the czar is
                            CzarUser = serverInput.readUTF();
                            if (username.equals(CzarUser)) {
                                czar = true;
                                System.out.println("You are now the Czar!!!");
                            }
                            //this is in here in case we get the loser message because of how the server data is set up, will check here
                            else if(CzarUser.equals("Loser")) {
                                System.out.println(username + ", you are a giant loser!");
                                exit(0);
                            } else {
                                System.out.println(CzarUser + " is now the Czar\n");
                                czar = false;
                            }
                            //read in question from server
                            question = serverInput.readUTF();
                            System.out.println("Question: " + question);
                        }
                            //Get player turn - conditional
                            System.out.println("Getting player turn...\n");
                            playerTurn = serverInput.readUTF();


                        //if this player's turn
                        if (playerTurn.equals(username)) {
                            if(czar){
                                System.out.println(playerTurn + " it is your turn to guess!");
                                System.out.println("Identify the human:\n");
                                //get all options from the server
                                for (int i = 0; i < 4; i++) {
                                    answerOptions.add(serverInput.readUTF());
                                }
                                //present the options to the player
                                for (int i = 0; i < 4; i++) {
                                    System.out.println("Option " + (i + 1) + ": " + answerOptions.get(i) + "\n");
                                }
                                //wait for selection
                                System.out.println("Awaiting selection 1-4");
                                //get the user's selection
                                answerSelected = userEntry.readLine();
                                serverOutput.writeUTF(answerSelected);
                                String isRight = serverInput.readUTF();
                                //if so, acknowledge and show correct answer as well as increment score
                                if (isRight.equals("Correct")){
                                    System.out.println("Correct, the answer was: " + answerSelected);
                                    score+=1;

                                }
                                //if not, tell the user the right answer
                                else if (isRight.equals("Incorrect")){
                                    String wrongAnswer = serverInput.readUTF();
                                    System.out.println("\nIncorrect, the answer was not " + answerSelected + ", it was " + Integer.parseInt(wrongAnswer));
                                }
                                //give the server the client's score
                                serverOutput.writeUTF(String.valueOf(score));
                                //update score for all clients
                                for(int a = 0; a < 2; a++){
                                    userScore[a] = Integer.parseInt(serverInput.readUTF());
                                }
                                //update score and round from the server
                                round = Integer.parseInt(serverInput.readUTF());
                                System.out.println("\nCurrent score: " + score);
                                counter1++;
                            }
                            //if not the czar, then we are hiding, or creating the phrase
                            else{
                                System.out.println(playerTurn + " it is your turn to hide!\n");
                                //get entered response
                                System.out.println("Question reminder: " + question);
                                System.out.println("Enter response: (noun + verb + subject)");
                                //give to server
                                responseToString = userEntry.readLine();
                                System.out.println(responseToString);
                                serverOutput.writeUTF(responseToString);
                                counter1++;
                            } 
                        }
                        //not this player's turn
                        else {
                            //tell user to wait
                            System.out.println("It is player: " + playerTurn + "'s turn. Please wait..");
                            System.out.println("Getting ready for your turn...");
                            counter2++;
                        }
                        //clear the answerOptions array list
                        answerOptions.clear();
                    } while (true);
                }
                //while not quitting
            } while (!userEntry.readLine().equalsIgnoreCase("QUIT"));
        } catch (Exception e) {
            System.out.println("Unknown error, please relaunch the program.");
        }
    }
}
