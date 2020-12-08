package client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class hideClient {
    private static final int PORT = 1236;

    public static void main(String[] args) {
        Socket server = null;
        String message, command, serverName = "";
        int clientPort;

        BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
        boolean nameAccepted = false;
        String username = "";
        int score = 0;

        try {
            do {

                System.out.println("Welcome to Find the Human Online");

                message = userEntry.readLine();
                StringTokenizer tokens = new StringTokenizer(message);
                command = tokens.nextToken();

                /////////////////////////////////////////////////////////////////////////////////////////
                if (command.equals("JOIN")) {
                    if (server != null) {
                        System.out.println("A connection with the game has already been established");
                        continue;
                    }
                    serverName = "localhost";
                    clientPort = PORT;
                    server = new Socket(serverName, 1234);
                    DataInputStream serverInput = new DataInputStream(server.getInputStream());
                    DataOutputStream serverOutput = new DataOutputStream(server.getOutputStream());
                    System.out.println("Connection with " + serverName + " has been established");

                    /////////////////////////////////////////////////////////////////////////////////////
                    System.out.println("Please enter a username...");
                    while (nameAccepted == false) {
                        // get input & send to server
                        username = userEntry.readLine();
                        serverOutput.writeUTF(username);

                        // check available
                        String serverResponse = serverInput.readUTF();
                        if (serverResponse.equals("ACCEPTED")) {
                            System.out.println("Your username has been accepted. You are: " + username);
                            nameAccepted = true;
                        } else if (serverResponse.equals("TAKEN")) {
                            System.out.println("This username has already been taken. Please enter another username");
                            continue;
                        } else {
                            System.out.println("An error has occurred. Please enter another username");
                            continue;
                        }
                    }

                    /////////////////////////////////////////////////////////////////////////////////////
                    System.out.println("Entering game...");
                    ServerSocket serverSocket = new ServerSocket(clientPort);
                    Socket dataSocket = serverSocket.accept();
                    DataInputStream inStream = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));
                    DataOutputStream outStream = new DataOutputStream(dataSocket.getOutputStream());

                    System.out.println("Test 4");

                    String responseToString, question, playerTurn = "";
                    int nLength, vLength, sLength;
                    ArrayList<String> nouns = new ArrayList<>();
                    ArrayList<String> verbs = new ArrayList<>();
                    ArrayList<String> subjects = new ArrayList<>();

                    System.out.println("Test 5");


                    // since we don't have "commands" like in the last project
                    // we will want to do a big while loop that the game stuff runs in instead
                    do {
                        System.out.println("Test 6");
                        // read in question from server //
                        question = inStream.readUTF();
                        System.out.println("Question: " + question);

                        // Get player turn - conditional //
                        System.out.println("Getting player turn...");
                        playerTurn = inStream.readUTF();

                        /* If this player turn */
                        if (playerTurn.equals(username)) {
                            System.out.println(playerTurn + " it is your turn to hide!");

                            // Get entered response //
                            System.out.println("Question reminder: " + question);
                            System.out.println("Enter response: (noun + verb + subject)");
                            responseToString = userEntry.readLine();
                            outStream.writeUTF(responseToString);
                        }

                        /* Not this player turn */
                        else {
                            System.out.println("It is player: " + playerTurn + "'s turn. Please wait..");
                            System.out.println("Getting ready for your turn...");
                        }
                    } while (true);
                }


            } while (!userEntry.readLine().equalsIgnoreCase("QUIT"));
        } catch (Exception e) {
            System.out.println("HERE2");
        }
    }

}
