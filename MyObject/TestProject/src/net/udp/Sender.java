package net.udp;

import java.net.*;

public class Sender {
    public static void send() throws Exception{
        DatagramSocket ds = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("localhost");
        int port = 9090;
        String message = "This is a sentence.";
        DatagramPacket dp = new DatagramPacket(message.getBytes(), 0, message.length(), ip, port);
        ds.send(dp);
        ds.close();
    }
    public static void main(String[] args) throws Exception {
        send();
    }
}
