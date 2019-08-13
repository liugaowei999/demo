package com.fy.io.demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NotBServer2 {
    public void initBioServer(int port) {
        //服务端Socket
        ServerSocket serverSocket = null;
        //客户端socket
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(stringNowTime() + ": serverSocket started");
            while(true) {
                socket = serverSocket.accept();
                new ServerHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert socket != null;
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String stringNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static void main(String[] args) {
        NotBServer2 server = new NotBServer2();
        server.initBioServer(8888);

    }
}
