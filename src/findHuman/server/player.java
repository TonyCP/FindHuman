package findHuman.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/************************************************************************
 * The player class
 *
 * @author Tony Chanelle
 * @author
 * @author
 * @author
 ***********************************************************************/
public class player implements Runnable{

    private static final int MAXIMUM_SCORE = 21;                            // maximum score before bust
    private String playerName;
    private String playerResponse;
    private int playerScore = 0;
    private BufferedReader inSocket;
    private DataOutputStream outSocket;
    private boolean continuePlaying = false;                                // true if player wants to keep playing, false if does not


    public player(Socket socket, String playerName){
        this.playerName = playerName;
        try {
            this.inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outSocket = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerResponse(){
        return this.playerResponse;
    }

    public void setPlayerResponse(String playerResponse){
        this.playerResponse = playerResponse;
    }

    public int getPlayerScore(){
        return this.playerScore;
    }

    public void incrementPScore(){
        this.playerScore++;
    }

    public void resetPScore(){
        this.playerScore = 0;
    }


    @Override
    public void run() {
//        try {
//            outSocket.writeUTF("SERVERMESSAGE--WELCOME");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        do {
//            playFindHuman();
//        } while (continuePlaying);
//        try {
//            outSocket.writeUTF("SERVERMESSAGE--GAMEOVER");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
