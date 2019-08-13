package com.fy.io.demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class BIOServer {
    public void initBIOServer(int port)
    {
        //服务端Socket
        ServerSocket serverSocket = null;
        //客户端socket
        Socket socket1 = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(100);
            System.out.println(stringNowTime() + ": serverSocket started");
            Set<Socket> sokeList = new HashSet<>();
            while(true) {
                // 产生阻塞，阻塞主线程
                try {
                    socket1 = serverSocket.accept();
                } catch (Exception e) {
                    System.out.println("accept check ...");
                }
                if (null == socket1 && sokeList.isEmpty()) {
                    continue;
                }
                if (null != socket1) {
                    socket1.setSoTimeout(1000);
                    sokeList.add(socket1);
                    System.out.println(stringNowTime() + "Receive ClientSocket:" + socket1.hashCode() + " connected");
                    socket1 = null;
                }
                for (Socket socket : sokeList) {
                    try {

                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        String inputContent;
                        while ((inputContent = reader.readLine()) != null) {
                            System.out.println("Receive socket:" + socket.hashCode() + ", " + inputContent);
                            writer.write("Success\n");
                            writer.flush();
                        }
                        System.out.println("Receive ClientSocket:" + socket.hashCode() + " - " + stringNowTime() + " - End");

                    } catch (Exception e) {
                        System.out.println("Receive ClientSocket:" + socket.hashCode() + " - " + stringNowTime() + " - read finish!");
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
//            try {
//                assert reader != null;
//                assert writer != null;
//                assert socket1 != null;
//                writer.close();
//                reader.close();
//                socket1.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
    private String stringNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static void main(String[] args) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BIOServer server = new BIOServer();
                server.initBIOServer(6543);
            }
        }, "MySocketThread");
        thread.start();


    }
}
