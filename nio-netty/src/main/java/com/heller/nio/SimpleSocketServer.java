package com.heller.nio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleSocketServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9001)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("接收到了一个新的客户端连接, "
                        + socket.getRemoteSocketAddress());


                new Thread(() -> {
                    handleRequest(socket);
                }).start();

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void handleRequest(Socket socket) {
        try (InputStream inputStream = socket.getInputStream()) {
            byte[] bytes = new byte[1024];
            int readCount;
            while ((readCount = inputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, readCount));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
