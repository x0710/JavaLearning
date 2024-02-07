package net.tcp;

import java.io.*;
import java.net.*;

public class Host {
    private ServerSocket serverSocket;
    public Host(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendText(String text) {
    }
    public String receiveText() {
        try {
            Socket socket = serverSocket.accept();
            System.out.println("已接收数据，来自" + socket.getInetAddress()+":"+socket.getLocalPort());
            InputStream os = socket.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1<<10];
            for(int size;(size = os.read(buffer)) != -1;) {
                sb.append(new String(buffer,0,size));
            }
            os.close();
            socket.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Host host = new Host(6666);
        System.out.println(host.receiveText());
        System.out.println(host.receiveText());
        System.out.println(host.receiveText());
        host.close();
    }
}
