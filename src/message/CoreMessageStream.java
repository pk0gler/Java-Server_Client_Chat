package message;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by pkogler on 26/11/2016.
 */
public class CoreMessageStream implements MessageStream, Closeable, AutoCloseable {
    public PrintWriter socketOut;
    private BufferedReader socketIn;

    public CoreMessageStream(Socket serverSocket) throws IOException {
        this.socketOut = new PrintWriter(serverSocket.getOutputStream(), true);
        this.socketIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
    }

    @Override
    public void writeToSocket(String msg) {
        this.socketOut.println(msg);
    }

    @Override
    public String readFromSocket() throws IOException {
        return this.socketIn.readLine();
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     * <p>
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        this.socketIn.close();
        this.socketOut.close();
    }
}

