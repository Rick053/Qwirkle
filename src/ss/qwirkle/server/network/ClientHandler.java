package ss.qwirkle.server.network;

import ss.qwirkle.common.Protocol;
import ss.qwirkle.server.Server;
import ss.qwirkle.server.controllers.ServerController;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

public class ClientHandler extends Thread{

    private String name;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.name = null;

        try {
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), Charset.forName(Protocol.Server.Settings.ENCODING)));
            out = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream(), Charset.forName(Protocol.Server.Settings.ENCODING)));
        } catch (IOException e) {
            ServerController.getInstance().log("error", e.getMessage());
            shutdown();
        }

        System.out.println("New ClientHandler created");
    }

    @Override
    public void run() {
        boolean running = true;

        while(running) {
            try {
                String msg = in.readLine();
                parseMessage(msg);
            } catch (IOException e) {
                ServerController.getInstance().log("error", e.getMessage());
                shutdown();
            }
        }
    }

    private void parseMessage(String msg) {
        System.out.println("Incoming message: " + msg);
        String[] params = msg.split(String.valueOf(Protocol.Server.Settings.DELIMITER));
        String command = params[0];
        params = Arrays.copyOfRange(params, 1, params.length);

        switch (command) {
            case Protocol.Client.HALLO:
                String name = params[0];
//                if (!isNameUsed(name)) {
//                    sendHallo(client);			// Reply with hallo
//                    client.setName(name);
//                    client.setFeatures(Arrays.copyOfRange(parameters, 1, parameters.length));
//                } else {
//                    client.sendMessage(Protocol.Server.ERROR +
//                            Protocol.Server.Settings.DELIMITER + 4);
//                }
                break;
            case Protocol.Client.ACCEPTINVITE:

                break;
            case Protocol.Client.CHANGESTONE:

                break;
            case Protocol.Client.CHAT:
//                broadcast(Protocol.Server.CHAT +
//                        Protocol.Server.Settings.DELIMITER +
//                        client.getName() + ": " + parameters[0]);	// The first parameter is the text.
                break;
            case Protocol.Client.DECLINEINVITE:

                break;
            case Protocol.Client.ERROR:

                break;
            case Protocol.Client.GETLEADERBOARD:

                break;
            case Protocol.Client.GETSTONESINBAG:

                break;
            case Protocol.Client.INVITE:

                break;
            case Protocol.Client.MAKEMOVE:

                break;
            case Protocol.Client.QUIT:

                break;
            case Protocol.Client.REQUESTGAME:
                int opponents = Integer.parseInt(params[0]);
                ServerController.getInstance().joinWaitingRoom(this, opponents);
                break;
            default:
                sendMessage(Protocol.Server.ERROR, Integer.toString(-1));
                break;
        }
    }

    public void sendMessage(String command, String content) {
        try {
            String msg = command + Protocol.Server.Settings.DELIMITER + content;
            out.write(msg + System.lineSeparator());
            out.flush();
        } catch (IOException e) {
            ServerController.getInstance().log("error", e.getMessage());
            shutdown();
        }
    }

    public void shutdown() {
        try {
            in.close();
            out.close();
            socket.close();
            ServerController.getInstance().removeHandler(this);
        } catch (IOException e) {
            ServerController.getInstance().log("error", e.getMessage());
        }
    }
}
