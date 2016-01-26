package qwirkle.network;

import qwirkle.controllers.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private ServerSocket server;
    private boolean running;

    public Server(int port) {
        if(port < 0) {
            port = Protocol.Server.Settings.DEFAULT_PORT;
        }

        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            //TODO error logs
            System.out.println(e.getMessage());
        }

        ServerController.getInstance().getUI().message("Listening on port " + port);
    }

    @Override
    public void run() {
        running = true;

        while(running) {
            ServerController controller = ServerController.getInstance();
            int max_connections = controller.maxConnections();

            if(max_connections != -1 || max_connections < controller.getHandlers().size()) {
                try {
                    Socket s = server.accept();
                    ClientHandler client = new ClientHandler(s);
                    controller.addHandler(client);
                    client.start();
                } catch (IOException e) {
                    //TODO error logs
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
