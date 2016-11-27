package client;

import streamDecorater.ChatStream;
import streamDecorater.CoreChatStream;

import java.io.*;

/**
 * Created by pkogler on 26/11/2016.
 */
public class MessageThread extends Thread {
    //private ObjectInput input;
    private ChatStream stream;

    public MessageThread(ChatStream stream) {
        this.stream = stream;
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
                System.out.println(this.stream.read());
            } catch (IOException e) {
                System.out.println("Client closed connection via \\exit\n" +
                        "Or Server closed down Socket Connection");
                System.exit(0);
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
