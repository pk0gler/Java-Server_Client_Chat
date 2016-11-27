package client;

import streamDecorater.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;
import client.*;

/**
 * Created by pkogler on 26/11/2016.
 * This Class acts as the Client it will receive Messages
 * from other Clients via a tcp socket
 */
public class Client {

    /**
     * CLIENT USAGE
     */
    private static final String CLIENT_USAGE = "" +
            "Usage:\n\t" +
            "java -jar client <server IP | hostname> <server port>";

    /**
     * Cient Constructor
     * Takes the program argumnents and initiates the SocketConnection
     *
     * @param args Program Argumnets supplying Port and Hostname
     */
    public Client(String[] args) {
        if (args.length != 2) {
            System.err.println(CLIENT_USAGE);
            System.exit(1);
        }

        String ipAddr = args[0];
        int port = 0;

        // Check if input is valid
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println(args[1] + " is no valid port");
            System.err.println(CLIENT_USAGE);
            System.exit(1);
        }

        Pattern ip = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
        Pattern hostname = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");
        if (!ipAddr.matches(ip.pattern())) {
            if (!ipAddr.matches(hostname.pattern())) {
                System.err.println("Invalid IP - Address");
                System.err.println(CLIENT_USAGE);
                System.exit(1);
            }
        }

        // Initiate Socket
        this.initiateClientChat(ipAddr, port);

    }

    /**
     * Initiate The Client Socket and connectiing to the
     * specified Server
     *
     * @param ipAddr Server Ip Address
     * @param port Server Port
     */
    private void initiateClientChat(String ipAddr, int port) {
        System.out.println(
                "Commands:\n\t\\help -> Show this Message\n\t" +
                        "\\exit -> Close / Disconnect\n\t" +
                        "\\person -> Create new Person\n\n\t" +
                        "<<>>\t... Server Message\n\t" +
                        "<>\t\t... Client Message\n"
        );
        try (
                Socket socket = new Socket(ipAddr, port);
                ChatStream stream = new XDDecorator(new SadFaceDecorator(new SmileDecorator(new CoreChatStream(socket))));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            Object fromServer;
            Message fromUser;

            //new MessageThread(in).start();
            new MessageThread(stream).start();

            while (true) {
                fromUser = new Message(stdIn.readLine());
                System.out.println();
                if (fromUser.getMessage() != null) {
                    if (fromUser.getMessage().equals("\\exit")) {
                        System.out.println("Ciau");
                        break;
                    } else if (fromUser.getMessage().equals("\\person")) {
                        System.out.print("Name:\t\t");
                        String vname = stdIn.readLine();
                        System.out.print("Nachname:\t");
                        String nname = stdIn.readLine();
                        System.out.println();
                        Person p = new Person(vname, nname);
                        //Message<Person> temp = new Message<>(new Message("as"));
                        Message<Person> temp = new Message<Person>(p);
                        //out.writeObject(temp);
                        stream.write(temp);
                    } else if (fromUser.getMessage().equals("\\help")) {
                        System.out.println(
                                "Commands:\n\t\\help -> Show this Message\n\t" +
                                        "\\exit -> Close / Disconnect\n\t" +
                                        "\\person -> Create new Person\n\n\t" +
                                        "<<>>\t... Server Message\n\t" +
                                        "<>\t\t... Client Message\n"
                        );
                        //out.writeObject(new Message("Someone invoked help"));
                        stream.write(new Message("Someone invoked help"));
                    } else if (fromUser.getMessage().equals("\\showLog")) {
                        Message temp = new Message("Someone is sowing log history o.0");
                        temp.setCommand("log");
                        //out.writeObject(temp);
                        stream.write(temp);
                    } else if (!fromUser.getMessage().equals("")) {
                        //out.writeObject(fromUser);
                        stream.write(fromUser);
                    } else {
                        System.out.println("Keine Eingabe bitte vergewissern Sie sich das ihr Keyboard funktioniert\n");
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Main Method
     * Created a new Client
     *
     * @param args
     */
    public static void main(String[] args) {
        new Client(args);
    }
}
