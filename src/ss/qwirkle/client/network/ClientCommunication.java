package ss.qwirkle.client.network;

import ss.qwirkle.common.Protocol;
import ss.qwirkle.server.controllers.ServerController;

import java.io.*;
import java.net.Socket;

public class ClientCommunication extends Thread {

    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;

    public ClientCommunication(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ioe ) {
            //TODO error logs
        }
    }


    public void sendMessage(String command, String content) {
        try {
            String msg = command + Protocol.Server.Settings.DELIMITER + content;
            out.write(msg + System.lineSeparator());
            out.flush();
        } catch (IOException e) {
            //TODO error logs
            shutdown();
        }
    }


    public void shutdown() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            ServerController.getInstance().log("error", e.getMessage());
        }
    }
}
