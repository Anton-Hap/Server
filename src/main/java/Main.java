import constants.Data;
import models.LocalData;
import models.Week;
import utils.JSONUtils;
import utils.ScheduleUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static ServerSocket server;
    private static Socket client;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(8080);
                System.out.println("Server was created");

                waitNewClient();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void waitNewClient() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("New client was connected");

                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                workWithClient(client, out, in);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void workWithClient(Socket client, BufferedWriter out, BufferedReader in) {
        new Thread(() -> {
            try {
                String message = waitAndGetMessage(in);
                System.out.println("Get message from " + client.getInetAddress().getHostAddress() + ":" + client.getLocalPort() +
                        " - " + message);

                LocalData localData = (LocalData) JSONUtils.fromJSONToObject(message, new LocalData());

                ScheduleUtils.updateSchedule(localData);

                Week week = (Week) JSONUtils.fromFileToObject(Data.SCHEDULE_PATH, new Week());

                message = JSONUtils.fromObjectToJSON(week);

                sendMessage(out, message);
            } catch (Exception e) {
                sendMessage(out, "404");
            }
        }).start();
    }

    private static String waitAndGetMessage(BufferedReader in) {
        while (true) {
            try {
                return in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendMessage(BufferedWriter out, String message) {
        try {
            out.write(message + "\n");
            out.flush();
            System.out.println("Message - " + message);
            System.out.println("Message was send");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
