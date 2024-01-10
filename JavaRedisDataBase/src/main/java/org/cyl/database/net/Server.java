package org.cyl.database.net;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) throws IOException {
        Map<String, String> map = new HashMap<>();
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器已启动，等待客户端连接...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("客户端已连接，IP地址为：" + socket.getInetAddress().getHostAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String message;
            while (true) {
                message = in.readLine();
                String[] s = message.split(" ");
                for (int i = 0; i < s.length; i++) {
                    if (s[i].equals("set")) {
                        map.put(s[i + 1], s[i + 2]);
                        System.out.println(i + 1);
                        break;
                    }
                    if (s[i].equals("get")) {
                        System.out.println(i + 1);
                        String value = map.get(s[i + 1]);
                        System.out.println(value);
                        out.println("已查到：" + value);
                        break;
                    } else {
                        System.out.println("不包含此键值");
                    }
                }
                System.out.println(map.get("k1"));
                System.out.println("已跳出循环");
            }
        }
    }
}