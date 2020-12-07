package findHuman.client;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class client {

    //private int playNumber = 0;
    private static final String DEFAULT_SERVER_ADDRESS = "localhost";
    private static final int DEFAULT_SERVER_PORT = 44444;
    private clientModel model;
    private clientView view;
    private int serverPort;
    private String serverAddress;


    /* */
    public client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /* */
    public void startConnection() {
        System.out.println("Starting Find-the-Humans client\nServer address: " + serverAddress + "\nServer port: " + serverPort);
        model = new clientModel(serverAddress, serverPort, this);
        view = new clientView(this);
        getServerMessage();
    }

    public clientModel getClientModel() {
        return this.model;
    }

    private void getServerMessage() {
        SwingWorker swingWorker = new SwingWorker<String, String>() {
            @Override
            public String doInBackground() throws Exception {
                return model.getServerMessage();
            }

            @Override
            public void done() {
                try {
                    updateView(get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }

    private void updateView (String serverMessage) {
        String[] serverMessageComponents = serverMessage.split("--");
        switch (serverMessageComponents[1]){
            // Welcome & prompt player name
            case "WELCOME":
                view.showWelcomePanel();
                getServerMessage();
                break;
            case "GETNAME":
                view.getHumanName();
                getServerMessage();
                break;

            case "PANELTYPE":
                switch (serverMessageComponents[2]){
                    case "GUESS":
                        view.addHumanPanel("GUESS");
                        getServerMessage();
                        break;
                    case "HIDE" :
                        view.addHumanPanel("HIDE");
                        getServerMessage();
                        break;
                }

            case "NEWROUND":
                switch ()
        }

    }




        public static void main(String[] args) {
        String serverAddress = DEFAULT_SERVER_ADDRESS;
        int serverPort = DEFAULT_SERVER_PORT;
        for (int i = 0; i < args.length; i += 2) {
            String option = args[i];
            String argument = null;
            try {
                argument = args[i + 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Options: [-a serverAddress] [-p serverPort]");
                System.exit(1);
            }
            switch (option) {
                case "-a":
                    serverAddress = argument;
                    break;
                case "-p":
                    try {
                        serverPort = Integer.parseInt(argument);
                    } catch (NumberFormatException e) {
                        System.err.println("Server port must be an integer");
                        System.exit(1);
                    }
                    break;
                default:
                    System.err.println("Options: [-a serverAddress] [-p serverPort]");
                    System.exit(1);
                    break;
            }
        }
        client controller = new client(serverAddress, serverPort);
        controller.startConnection();
    }


}
