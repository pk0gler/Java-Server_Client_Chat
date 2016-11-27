package server;

import client.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pkogler on 26/11/2016.
 */
public class server {
    private static final URL url = server.class.getClass().getResource("/server/log.txt");
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

    public static void main(String[] args) {

        boolean listening = true;

        System.out.println(
                "+---------------------------------------------------+\n" +
                        "|  Server is now running and listening on Port ...  |\n" +
                        "+---------------------------------------------------+\n"
        );

        try (ServerSocket serverSocket = new ServerSocket(4444)) {

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
}
