package org.cyl.database.net;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        System.out.println("已连接到服务器");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        String message;
        while (true) {
            System.out.print("请输入要发送的消息：");
            message = keyboard.readLine();
            out.println(message);

            String response = in.readLine();
            System.out.println("收到服务器回复：" + response);
        }
    }
}
