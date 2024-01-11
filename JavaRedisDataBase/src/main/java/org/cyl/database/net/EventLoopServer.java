package org.cyl.database.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class EventLoopServer {
    public static void main(String[] args) {
        int port = 8888;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务器已启动，监听端口：" + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端已连接：" + clientSocket.getInetAddress());

                new Thread(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("服务器启动失败：" + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            Map<String, String> keyValueMap = new HashMap<>();
            while ((message = in.readLine()) != null) {
                System.out.println("收到客户端消息：" + message);

                // 解析命令，提取键和值
                String[] parts = message.split(" ");
                if (parts.length == 3 && parts[0].equalsIgnoreCase("set")) {
                    String key = parts[1];
                    String value = parts[2];
                    // 将键值对存储到 HashMap 中
                    keyValueMap.put(key, value);
                    out.println("服务器已收到消息：" + message);
                } else if (parts.length == 2 && parts[0].equalsIgnoreCase("get")) {
                    String key = parts[1];
                    // 从 HashMap 中获取对应的值
                    String actualValue = keyValueMap.get(key);
                    if (actualValue != null) {
                        out.println("服务器已找到匹配的值：" + actualValue);
                    } else {
                        out.println("未找到匹配的值：" + actualValue);
                    }
                } else {
                    out.println("无法识别的命令：" + message);
                }
            }
        } catch (Exception e) {
            System.err.println("处理客户端请求失败：" + e.getMessage());
        } finally {
            clientSocket.close();
        }
    }
}