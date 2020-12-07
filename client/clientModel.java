package findHuman.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

public class clientModel {

    private static final int MESSAGE_WAIT_TIME = 500;    // time between server messages
    private Socket socket;
    private BufferedReader inSocket;
    private DataOutputStream outSocket;
    private client client;

    public clientModel(String serverAddress, int serverPort, client client) {
        try {
            this.socket = new Socket(serverAddress, serverPort);
        } catch (IOException e) {
            System.err.println("No FindHumans server on port " + serverPort + " at address " + serverAddress);
            System.exit(1);
        }

        try {
            this.inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outSocket = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.client = client;
    }

    public String getServerMessage() {
        String serverMessage = null;

        try {
            Thread.sleep(MESSAGE_WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (serverMessage == null) {
            try {
                serverMessage = inSocket.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return serverMessage;
    }

    public void sendServerMessage(String Message) {
        try {
            if (socket.isClosed()) {
                System.out.print("Error: Server not connected!");
            }
            this.outSocket.writeUTF(Message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getResponseArray() throws IOException {
        String[] responses = new String[3]; // 1 hidden human + 2 bots = 3 players

        for (int i = 0; i < responses.length; i++) {
            responses[i] = inSocket.readLine();
        }
        return responses;
    }

    private HashMap<String, String> createResultMap() throws IOException {

        int numWinners = Integer.parseInt(inSocket.readLine());
        HashMap<String, String> resultMap = new HashMap<>();

        for (int i = 0; i < numWinners; i++) {
            resultMap.put(inSocket.readLine(), inSocket.readLine());
        }
        return resultMap;
    }

//    private LinkedList<String> createWinnerList (BufferedReader sockIn){
//
//        LinkedList<String> winnerList = new LinkedList<>();
//        int numOfWinners = receiveNumber(sockIn);
//
//        for (int i = 0; i < numOfWinners; i++) {
//            winnerList.add(receiveMsg(sockIn));
//        }
//        return winnerList;
//    }


    public void quitGame() {
        //sendServerMessage("CLIENT MESSAGE -- QUIT GAME");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }


}
