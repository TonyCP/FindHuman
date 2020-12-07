import java.awt.Checkbox;
import java.io.*;
import java.net.*;
import java.util.*;

public class client{
    private static InetAddress host;
    private static final int PORT = 1236;

    public static void main(String[] args)
    {
        Socket server = null;
        String message, command, response, serverName = "";
        int clientPort;
        int MAXIMUM_HAND_SIZE = 7;

        Scanner serverInput = null;
        PrintWriter serverOutput = null;

        Scanner userEntry = new Scanner(System.in);
        boolean nameAccepted = false;
        String username = "";


        try {
            do {

                System.out.println("Welcome to Cards Against Humanity Online");

                message = userEntry.nextLine();
                StringTokenizer tokens = new StringTokenizer(message);
                command = tokens.nextToken();

                // user will send "JOIN" with the IP and port info
                if(command.equals("JOIN"))
                {
                    if (server != null)
                    {
                        System.out.println("A connection with the game has already been establisheed");
                        continue;
                    }
                    serverName = tokens.nextToken();
                    clientPort = Integer.parseInt(tokens.nextToken());
                    server = new Socket("localhost", 1234);

                    serverInput = new Scanner(server.getInputStream());
                    serverOutput = new PrintWriter(server.getOutputStream(), true);

                    System.out.println("Connection with " + serverName + " has been established");


					System.out.println("Please enter a username...");
                    // while we haven't had a name accepted by the server
                    while(nameAccepted == false) {
                        // get input
                        username = userEntry.nextLine();
                        // send to server
                        serverOutput.println(username);

                        // check available
                        String serverResponse = serverInput.next();
                        if(serverResponse.equals("ACCEPTED")){
                            System.out.println("Your username has been accepted. You are: " + username);
                            nameAccepted = true;
                        }

                        else if (serverResponse.equals("TAKEN")) {
                            System.out.println("This username has already been taken. Please enter another username");
                            continue;
                        }

                        else {
                            System.out.println("An error has occurred. Please enter another username");
                            continue;
                        }
                    }

                    // TODO: User needs to wait until the server sends a card list for first round
					System.out.println("Test");
					
                    // name stuff is done, user
                    System.out.println("Entering game...");
                    serverOutput.println(clientPort);
					System.out.println("Test2");
                    ServerSocket welcomeSocket = new ServerSocket(clientPort);
					System.out.println("Test3");
                    Socket dataSocket = welcomeSocket.accept();
                    InputStream inStream = dataSocket.getInputStream();
                    OutputStream outStream = dataSocket.getOutputStream();

                    String[] hand;
                    String currentBlackCard;
                    String cardTzar;
                    int cardSelection = 0;
                    ObjectInputStream objectIn = new ObjectInputStream(inStream);
                    Scanner scanIn = new Scanner(inStream);

                    // since we don't have "commands" like in the last project
                    // we will want to do a big while loop that the game stuff runs in instead
                    do {
						/* read in and print players hand */
						System.out.println("Getting your hand...");
						hand = (String[])objectIn.readObject();
						int num = 0;
						System.out.println("Your hand: ");
						for(int i=0; i<MAXIMUM_HAND_SIZE; i++){
							num = i+1;
							System.out.println(num+". " + hand[i]);
						}

						/* conditional for if you are tzar or a player */
						System.out.println("Getting card tzar...");
						cardTzar = scanIn.nextLine();
						
						/* if you are card tzar */
						if(cardTzar.equals(username)){
							System.out.println("You are the card tzar this turn!");
							System.out.println("Getting your black card...");
							currentBlackCard = scanIn.nextLine();
							System.out.println(currentBlackCard);
							System.out.println("Awaiting submissions...");

						}
						/* you are playing this turn */
						else{
							System.out.println(cardTzar + " is the card tzar this turn");
							System.out.print("Getting the black card for this turn...");
							currentBlackCard = scanIn.nextLine();
							System.out.println(currentBlackCard);

							while(cardSelection<=0 || cardSelection>7){
								System.out.println("Submit your white card now");
								cardSelection = userEntry.nextInt();
							}
						}
                    } while(true);
                }


            } while (!userEntry.nextLine().equalsIgnoreCase("QUIT"));
        }
        catch (Exception e) {
            // TODO: handle exception
        }
    }
}