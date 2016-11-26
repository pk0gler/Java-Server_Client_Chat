package server;

import client.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by pkogler on 26/11/2016.
 */
public class AllocationService extends Thread {
    private ConcurrentHashMap<String, ClientThread> clients;
    private ConcurrentLinkedQueue<Message> msgs;

    public AllocationService(ConcurrentHashMap<String, ClientThread> clients) {
        this.clients = clients;
        this.msgs = new ConcurrentLinkedQueue<>();
    }

    public ConcurrentLinkedQueue<Message> getMsgs() {
        return msgs;
    }

    public void setMsgs(ConcurrentLinkedQueue<Message> msgs) {
        this.msgs = msgs;
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
            if (this.msgs.size() != 0) {
                Message msg = msgs.remove();
                for (Map.Entry<String, ClientThread> entry : this.clients.entrySet()) {
                    server.log(msg);
                    entry.getValue().setNewMsg(msg);
                    System.out.println(msg);
                }
            }
        }
    }
}
