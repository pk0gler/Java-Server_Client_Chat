package server;

import client.Message;

import java.io.*;
import java.net.ServerSocket;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pkogler on 26/11/2016.
 */
public class server {
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
        File file = new File("src/log.txt");
        try (
                FileOutputStream fout = new FileOutputStream(file,true);
        ) {
            if (!file.exists()) {
                file.createNewFile();
            }

            String temp = msg.toString()+"\n";
            byte[] msgBytes = temp.getBytes();

            fout.write(msgBytes);
            fout.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
