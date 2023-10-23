package com.cz.viid.be.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SocketClientMain {
    private static final Logger logger = LoggerFactory.getLogger(SocketClientMain.class);
    public static void main(String[] args) {
        Consumer<String> consumer = logger::info;
        WebsocketClient websocketClient = new WebsocketClient("ws://localhost:8120/VIID/Subscribe/WebSocket", "admin", consumer);
        CompletableFuture.runAsync(websocketClient);
        Scanner scanner = new Scanner(System.in);
        String str;
        while (true) {
            str = scanner.next();
            if (str.equals("q")) {
                break;
            } else {
                logger.info(str);
                websocketClient.send(str);
            }
        }
        websocketClient.close();
    }
}
