package server;

import client.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by pkogler on 26/11/2016.
 * Allocation service holds a queue with Messages
 * and whether the queu is empty or not it will distribute
 * its content to each client
 */
public class AllocationService extends Thread {
    /**
     * Clients
     */
    private ConcurrentHashMap<String, ClientThread> clients;
    /**
     * Messages Queue
     */
    private ConcurrentLinkedQueue<Message> msgs;

    /**
     * AllocationService Constructor
     *
     * @param clients
     */
    public AllocationService(ConcurrentHashMap<String, ClientThread> clients) {
        this.clients = clients;
        this.msgs = new ConcurrentLinkedQueue<>();
    }

    /**
     *
     * @return
     */
    public ConcurrentLinkedQueue<Message> getMsgs() {
        return msgs;
    }

    /**
     *
     * @param msgs
     */
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
                Server.log(msg);
                System.out.println(msg);
                for (Map.Entry<String, ClientThread> entry : this.clients.entrySet()) {
                    entry.getValue().setNewMsg(msg);
                }
            }
        }
    }
}
