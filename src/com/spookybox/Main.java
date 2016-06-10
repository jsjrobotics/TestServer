package com.spookybox;

import com.spookybox.server.TcpServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            TcpServer server = new TcpServer(8000);
            server.start();
            while(!server.isDataSent()){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
