package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class.getName());

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    while (in.ready()) {
                        String str = in.readLine();
                        System.out.println(str);
                        if (str.startsWith("GET /?msg=")) {
                            out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                            if (str.contains("Exit")) {
                                server.close();
                                continue;
                            } else if (str.contains("Hello")) {
                                out.write("Hello".getBytes());
                                continue;
                            }
                            out.write("What".getBytes());
                        }
                    }
                    out.flush();
                }
            }
        } catch (Exception e) {
            LOG.error("Error information: ", e);
        }
    }
}
