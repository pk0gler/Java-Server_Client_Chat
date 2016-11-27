package server;

import client.Message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by pkogler on 26/11/2016.
 */
public class ClientThread extends Thread {
    private Socket client;
    private String usrName;
    private ConcurrentLinkedQueue<Message> msgs;
    private ObjectOutput out;

    public ClientThread(Socket socket, String usrName, ConcurrentLinkedQueue<Message> msgs) {
        this.client = socket;
        this.usrName = usrName;
        this.msgs = msgs;
        System.out.println(
                "+------------------------------------------------------------+\n" +
                        "           New User\t>> " + usrName + " <<\tconnected\n" +
                        "+------------------------------------------------------------+\n"
        );
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     */
    @Override
    public void run() {
        try (
                ObjectOutput out = new ObjectOutputStream(client.getOutputStream());
                ObjectInput in = new ObjectInputStream(client.getInputStream())
        ) {

            Message inputLine, outputLine;

            this.out = out;

            // Initiate conversation with KnockKnockSingleUser.client
            outputLine = new Message("Hallo " + this.usrName);
            outputLine.setUsr("Server");
            out.writeObject(outputLine);

            while ((inputLine = (Message) in.readObject()) != null) {
                if (inputLine.equals("\\exit")) {
                    break;
                }

                inputLine.setUsr(this.usrName);
                this.msgs.add(inputLine);

                if (inputLine.getCommand().equals("log")) {
                    Message<String> temp = new Message(server.readLog());
                    temp.setUsr("Server");
                    out.writeObject(temp);
                }
            }
        } catch (IOException e) {
            System.out.println(this.usrName + " ist disconnected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setNewMsg(Message msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {

        }
    }
}
