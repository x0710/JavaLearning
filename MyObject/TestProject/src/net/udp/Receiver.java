package net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver {
    public static void receive() throws IOException {
        DatagramSocket ds = new DatagramSocket(9090);
        byte[] buffer = new byte[1<<10];
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        ds.receive(dp);
        ds.close();
        System.out.println(new String(dp.getData()));
    }
    public static void main(String[] args) throws Exception {
        receive();
    }
}
