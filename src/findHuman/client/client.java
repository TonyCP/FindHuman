package findHuman.client;

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
        model = new clientModel(serverAddress, serverPort);
        view = new clientView(this);
    }

    public clientModel getClientModel() {
        return this.model;
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
