package server;

import client.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pkogler on 26/11/2016.
 * Server Socket
 * Distributes Clients and creates its Threads
 */
public class Server {
    /**
     * SERVER Usage
     */
    private static final String SERVER_USAGE = "USAGE\n\tjava -jar server.jar <port>";
    /**
     * Random User Names
     */
    private static String[] usrnames = new String[]{
            "Kaitlin",
            "Francesca",
            "Hector",
            "Zoe",
            "Andrew",
            "Otto",
            "Salvador",
            "Audra",
            "Judah",
            "Brett",
            "Britanney",
            "Ferdinand",
            "Madeline",
            "Aubrey",
            "Rogan",
            "Henry",
            "Yoshio",
            "Rinah",
            "Hermione",
            "Lamar",
            "MacKenzie",
            "Garrison",
            "Steven",
            "Levi",
            "Melodie",
            "Olympia",
            "Silas",
            "Aquila",
            "Cassady",
            "Burton",
            "Deacon",
            "Owen",
            "Brennan",
            "Iola",
            "Arthur",
            "Murphy",
            "Karen",
            "Ariel",
            "Fatima",
            "Uta",
            "Octavius",
            "Aubrey",
            "Uta",
            "Barbara",
            "Sandra",
            "Zachery",
            "Edward"};
    private boolean listening;

    /**
     * Server Constructor
     * Pasrses cli arguments
     * Initiates server connection
     *
     * @param args
     */
    public Server(String[] args) {
        if (args.length != 1) {
            System.err.println(SERVER_USAGE);
            System.exit(1);
        }

        int port = 0;

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid Port not a number");
            System.err.println(SERVER_USAGE);
        }

        this.listening = true;
        this.initiateServerChat(port);
    }

    /**
     * Initiating Server Chat
     * Creates Client Threads
     *
     * @param port
     */
    private void initiateServerChat(int port) {
        System.out.println(
                "+---------------------------------------------------+\n" +
                        "|  Server is now running and listening on Port "+port+"  |\n" +
                        "+---------------------------------------------------+\n"
        );

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            ConcurrentHashMap<String, ClientThread> clients = new ConcurrentHashMap<>();
            AllocationService allocationService = new AllocationService(clients);
            allocationService.start();

            while (listening) {
                String usrName = usrnames[new Random().nextInt((usrnames.length))];
                ClientThread clientThread = new ClientThread(serverSocket.accept(), usrName, allocationService.getMsgs());
                clientThread.start();
                clients.put(usrName, clientThread);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writing to log File
     * Thread safe Method when multiple Threads want to log
     *
     * @param msg @{@link Message}
     */
    public synchronized static void log(Message msg) {
        File file = new File(System.getProperty("user.dir")+"/log.txt");
        boolean append = file.length() >= 1000 ? false : true;
        try (
                FileOutputStream fout = new FileOutputStream(file, append);
        ) {
            if (!file.exists()) {
                file.createNewFile();
            }

            String temp = msg.toString() + "\n";
            byte[] msgBytes = temp.getBytes();

            fout.write(msgBytes);
            fout.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read Log file
     * when a client wants to see the Log File
     *
     * @return @{@link Message}
     */
    public synchronized static Message<String> readLog() {
        Message<String> res = new Message<String>("");
        try {
            //Scanner s = new Scanner(new File("src/log.txt"));
            Scanner s = new Scanner(new File(System.getProperty("user.dir")+"/log.txt"));
            String out = "\n--------------------\nServer Logs:\n--------------------\n\t";
            while (s.hasNext()) {
                out += s.nextLine() + "\n\t";
            }
            res.setMessage(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Main Method
     * Creates a new Server
     *
     * @param args
     */
    public static void main(String[] args) {
        Server s = new Server(args);
    }
}
