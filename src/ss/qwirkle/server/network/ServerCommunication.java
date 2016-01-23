package ss.qwirkle.server.network;

import ss.qwirkle.server.controllers.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunication extends Thread {

    private ServerSocket server;
    private boolean running;

    public ServerCommunication(int port) throws IOException{
        server = new ServerSocket(port);
        running = true;
    }

    @Override
    public void run() {
        ServerController controller = ServerController.getInstance();

        controller.log("info", "Server is listening on port: " + controller.getPort());

        while (running) {
            if(controller.getMaxConnections() == -1 || controller.getConnections().size() < controller.getMaxConnections()) {
                try {
                    Socket s = server.accept();
                    controller.log("info", "New client connected: " + s.getRemoteSocketAddress());

                    ClientHandler handler = new ClientHandler(s);
                    controller.addHandler(handler);
                    handler.start();
                } catch (IOException e) {
                    controller.log("error", e.getMessage());
                }
            }
        }
    }
}
