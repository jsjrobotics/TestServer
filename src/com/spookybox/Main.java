package com.spookybox;

public class Main {

    private static boolean sDataSent = false;

    public static void main(String[] args) {
        TcpServer tcpServer = new TcpServer("server1", 8000, server -> {
            transmitData(server);
            sDataSent = true;
        });
        tcpServer.start();
        while(!sDataSent){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void transmitData(TcpServer server) {
        String transmit = "testhelp1/ENDrrrtesthelp2/END";
        int[] data = new int[transmit.length()];
        for(int index = 0; index < transmit.length(); index++){
            data[index] = transmit.charAt(index);
        }
        server.transmit(data);
    }
}
