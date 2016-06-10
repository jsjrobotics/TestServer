package com.spookybox.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

public class TcpServer {
    private static final int BACKLOG_QUEUE = 2;
    private static final int SHUDOWN_TASK_COMPLETION_DELAY_SECONDS = 0;
    final HttpServer server;
    private boolean mSentData = false;

    public TcpServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), BACKLOG_QUEUE);
        server.setExecutor(null);
        for(ContextHandler c : getContextHandlers()){
            server.createContext(c.getContext(), c.getHandler());
        }
    }

    public void start(){
        server.start();
    }

    private List<ContextHandler> getContextHandlers() {
        return Arrays.asList(
                getDefaultHandler()
        );
    }

    private ContextHandler getDefaultHandler() {
        return new ContextHandler() {
            @Override
            String getContext() {
                return "/";
            }

            @Override
            HttpHandler getHandler() {
                return httpExchange -> {
                    String transmit = "testhelp1/ENDrrrtesthelp2/END";
                    httpExchange.sendResponseHeaders(200, transmit.getBytes().length);
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.write(transmit.getBytes());
                    outputStream.close();
                    mSentData = true;

                };
            }
        };
    }

    public boolean isDataSent() {
        return mSentData;
    }

    public void stop() {
        server.stop(SHUDOWN_TASK_COMPLETION_DELAY_SECONDS);
    }
}
