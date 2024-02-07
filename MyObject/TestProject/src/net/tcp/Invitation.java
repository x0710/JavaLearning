package net.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class Invitation {
    private Socket socket;

    public Invitation(String ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendText(String text) {
        try {
            OutputStream os = socket.getOutputStream();
            os.write(text.getBytes());
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Invitation i = new Invitation("localhost", 6666);
        i.sendText("泥嚎");
        i.sendText("Test2");
    }
}
