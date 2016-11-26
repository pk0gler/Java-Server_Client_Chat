package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by pkogler on 26/11/2016.
 */
public class ChatServer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage\t: java RMServer <port number>");
            System.err.println("Example\t: java RMServer 4444");
            System.exit(1);
        }
        ConcurrentMap<Integer, ChatServerThread> clients = new ConcurrentHashMap<>();
        //Assigning the prot number to initialize the Connection
        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;
        int clientNumb = 0;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server is now running ...");
            ConcurrentLinkedQueue<String> msgs = new ConcurrentLinkedQueue<>();
            QueueListener x = new QueueListener(msgs, clients);
            x.start();
            while (listening) {
                clients.put(clientNumb,new ChatServerThread(serverSocket.accept(),msgs));
                clients.get(clientNumb).start();
                clientNumb++;
                System.out.println(msgs.toString());
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port Maybe another service is running on the specified port" + portNumber);
            System.exit(-1);
        }
    }
}
