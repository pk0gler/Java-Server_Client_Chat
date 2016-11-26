package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by pkogler on 26/11/2016.
 */
public class ChatServerThread extends Thread {
    private Socket socket;
    private ConcurrentLinkedQueue<String> queue;
    private String msg;
    private PrintWriter out;
    private BufferedReader in;

    public ChatServerThread(Socket socket, ConcurrentLinkedQueue<String> queue) {
        super("ChatServerThread");
        this.socket = socket;
        this.queue = queue;
        this.msg = "";
        System.out.println("New Client Connected ...");
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
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            this.in = in;
            this.out = out;
            String inputLine, outputLine;
            out.println("jetzt aber");
            while ((inputLine = in.readLine()) != null) {
                /*
                 * If the Ckient types something
                 */
                // Call processInput in the Protocoll
                // Decides what Output to give
                this.queue.add(inputLine);
            }
            //Closing the Socket properly
            socket.close();
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    public void setNewMsg(String newMsg) {
        this.out.println(newMsg);
    }
}
