package client;

import message.CoreMessageStream;
import message.MessageStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by pkogler on 26/11/2016.
 */
public class ChatClient extends Thread {
    private final MessageStream p;

    public static void main(String[] args) {
        /*
         *  Check if port is specified
         */
        if (args.length != 2) {
            System.err.println("Usage:\t java RMCLient <host name> <port number>");
            System.err.println("Example:\t java RMCLient localhost 4444");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        /*
         * Establish the connection with the Server
         */
        try (
                Socket serverSocket = new Socket(hostName, portNumber);
                CoreMessageStream messageStream = new CoreMessageStream(serverSocket);
                PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
                //BufferedReader in = new BufferedReader(
                //     new InputStreamReader(serverSocket.getInputStream()));
        ) {

            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            new ChatClient(messageStream).start();
            while (true) {
                fromUser = "";
                fromUser = stdIn.readLine();

                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    messageStream.writeToSocket(fromUser);
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChatClient(MessageStream msgStream) {
        this.p = msgStream;
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
        while (true) {
            try {
                System.out.println(this.p.readFromSocket());
            } catch (IOException e) {
                System.out.println("Server is down ...");
                System.exit(0);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
