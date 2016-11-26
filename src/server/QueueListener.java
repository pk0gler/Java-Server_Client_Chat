package server;

import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by pkogler on 26/11/2016.
 */
public class QueueListener extends Thread {
    private final ConcurrentMap<Integer, ChatServerThread> clients;
    private ConcurrentLinkedQueue<String> queue;
    public QueueListener(ConcurrentLinkedQueue<String> msgs, ConcurrentMap<Integer, ChatServerThread> clients) {
        this.queue = msgs;
        this.clients = clients;
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
            if (queue.size() != 0) {
                String msg = queue.remove();
                for (Map.Entry<Integer, ChatServerThread> entry : this.clients.entrySet()) {
                    entry.getValue().setNewMsg(msg);
                    System.out.println("New Msg set" + queue.toString());
                }
            }
        }
    }
}
