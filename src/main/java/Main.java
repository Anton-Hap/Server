import constants.Data;
import models.LocalData;
import models.Week;
import utils.JSONUtils;
import utils.ScheduleUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static ServerSocket server;
    private static Socket client;
    private static BufferedReader in;
    private static BufferedWriter out;

    private static int PORT = 8080;

    public static void main(String[] args) {
        System.out.println("Start application");
        waitCommand();
    }

    private static void startServer() {
        new Thread(() -> {
            try {
                server = new ServerSocket(PORT);
                System.out.println("Server was created");

                waitNewClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void closeServer() {
        try {
            server.close();
            System.out.println("Server was closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void waitCommand() {
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (true) {
                try {
                    switch (scanner.nextLine()) {
                        case "/localport":
                            System.out.println("Server's port: " + server.getLocalPort());
                            break;
                        case "/hostaddress":
                            System.out.println("Server's host address: " + server.getInetAddress().getHostAddress());
                            break;
                        case "/hostname":
                            System.out.println("Server's host name: " + server.getInetAddress().getHostName());
                            break;
                        case "/address":
                            System.out.println("Server's address: " + Arrays.toString(server.getInetAddress().getAddress()));
                            break;
                        case "/isclosed":
                            System.out.println(server.isClosed());
                            break;
                        case "/localsocket":
                            System.out.println("Server's local socket address: " + server.getLocalSocketAddress());
                            break;
                        case "/channel":
                            System.out.println("Server's channel: " + server.getChannel().toString());
                            break;
                        case "/reuseaddress":
                            try {
                                System.out.println("Server's reuse address: " + server.getReuseAddress());
                            } catch (SocketException e) {
                                System.out.println("No");
                            }
                            break;
                        case "/isbound":
                            System.out.println(server.isBound());
                            break;
                        case "/timeout":
                            try {
                                System.out.println("Server's timeout: " + server.getSoTimeout());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "/startserver":
                            startServer();
                            break;
                        case "/closeserver":
                            closeServer();
                            System.out.println("Application was closed");
                            System.exit(0);
                            break;
                        case "/setport" :
                            System.out.print("Enter port: ");
                            PORT = scanner.nextInt();
                            break;
                        case "/help":
                            System.out.println("/startserver");
                            System.out.println("/closeserver");
                            System.out.println("/localport - default port: " + PORT);
                            System.out.println("/hostaddress");
                            System.out.println("/hostname");
                            System.out.println("/address");
                            System.out.println("/isclosed");
                            System.out.println("/isbound");
                            System.out.println("/timeout");
                            System.out.println("/channel");
                            System.out.println("/reuseaddress");
                            System.out.println("/localsocket");
                            System.out.println("/setport");
                            break;
                        default :
                            System.out.println("It's known command");
                    }
                } catch(Exception e){
                    System.out.println("Server was not started");
                }
            }
        }).start();
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
