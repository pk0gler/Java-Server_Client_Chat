package server;

import client.Message;
import streamDecorater.ChatStream;
import streamDecorater.CoreChatStream;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by pkogler on 26/11/2016.
 * Client Thread will be initiated whe a new client connects to the
 * Chat.
 * It will handle the communication with that client
 */
public class ClientThread extends Thread {
    /**
     * Client
     */
    private Socket client;
    /**
     * Username
     */
    private String usrName;
    /**
     * Messages
     */
    private ConcurrentLinkedQueue<Message> msgs;
    /**
     * Stream
     */
    private ChatStream stream;

    /**
     * Constructor Client Thread
     *
     * @param socket Client Socket
     * @param usrName Its Username
     * @param msgs MEssage Queue
     */
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
                //ObjectOutput out = new ObjectOutputStream(Client.getOutputStream());
                //ObjectInput in = new ObjectInputStream(Client.getInputStream())
                ChatStream stream = new CoreChatStream(client)
        ) {

            Message inputLine, outputLine;

            //this.out = out;
            this.stream = stream;


            //this.msgs.add(new Message("test connected"));

            // Initiate conversation with KnockKnockSingleUser.Client
            outputLine = new Message("Hallo " + this.usrName);
            outputLine.setUsr("Server");
            //out.writeObject(outputLine);
            stream.write(outputLine);
            outputLine = new Message("User " + this.usrName + " connected");
            outputLine.setUsr("Server");
            this.msgs.add(outputLine);


            //while ((inputLine = (Message) in.readObject()) != null) {
            while ((inputLine = (Message) stream.read()) != null) {
                if (inputLine.equals("\\exit")) {
                    break;
                }

                if (inputLine.getCommand().equals("log")) {
                    inputLine.setUsr("Server");
                } else {
                    inputLine.setUsr(this.usrName);
                }
                this.msgs.add(inputLine);

                if (inputLine.getCommand().equals("log")) {
                    Message<String> temp = new Message(Server.readLog());
                    temp.setUsr("Server");
                    //out.writeObject(temp);
                    stream.write(temp);
                }
            }
        } catch (IOException e) {
            System.out.println(this.usrName + " ist disconnected\n");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Will be called from the Allocationservice when a new Message is
     * added to the Queue
     *
     * @param msg @{@link Message}
     */
    public void setNewMsg(Message msg) {
        try {
            //out.writeObject(msg);
            stream.write(msg);
        } catch (IOException e) {

        }
    }
}
