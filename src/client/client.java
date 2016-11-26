package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by pkogler on 26/11/2016.
 */
public class client {
    public static void main(String[] args) {
        try (
                Socket kkSocket = new Socket("localhost", 4444);
                //PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                //BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
                ObjectOutput out = new ObjectOutputStream(kkSocket.getOutputStream());
                ObjectInput in = new ObjectInputStream(kkSocket.getInputStream())
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            Object fromServer;
            Message fromUser;

            new MessageThread(in).start();

            while (true) {
                //System.out.println(fromServer);
                //if (fromServer.equals("Bye."))
                //    break;

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
                        Message<Person> temp = new Message<>(p);
                        out.writeObject(temp);
                    } else if (fromUser.getMessage().equals("\\help")) {
                        System.out.println(
                                "Commands:\n\t\\help -> Show this Message\n\t" +
                                "\\exit -> Close / Disconnect\n\t" +
                                "\\person -> Create new Person\n\t"
                        );
                        out.writeObject(new Message("Someone invoked help"));
                    } else {
                        out.writeObject(fromUser);
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
            System.exit(1);
        }
    }
}
