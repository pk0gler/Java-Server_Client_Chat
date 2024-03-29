package streamDecorater;

import java.io.*;
import java.net.Socket;

/**
 * Created by philippkogler on 27/11/16.
 * CoreChat Stream simple chatstream functionality
 */
public class CoreChatStream extends ChatStream {
    /**
     * Output
     */
    private ObjectOutput out;
    /**
     * Input
     */
    private ObjectInput in;

    /**
     * {@link CoreChatStream} Constructor
     *
     * @param socket
     * @throws IOException
     */
    public CoreChatStream(Socket socket) throws IOException {
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }


    /**
     * Read from an InputStream
     *
     * @return @{@link Object} read Object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public Object read() throws IOException, ClassNotFoundException {
        System.out.println();
        return this.in.readObject();
    }

    /**
     * Write to an OutputStream
     *
     * @param o @{@link Object} Object to write
     * @throws IOException
     */
    @Override
    public void write(Object o) throws IOException {
        this.out.writeObject(o);
    }


    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     * <p>
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     * <p>
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     * <p>
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     * <p>
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {
        this.out.close();
        this.in.close();
    }

    public ObjectOutput getOut() {
        return out;
    }

    public void setOut(ObjectOutput out) {
        this.out = out;
    }

    public ObjectInput getIn() {
        return in;
    }

    public void setIn(ObjectInput in) {
        this.in = in;
    }
}
