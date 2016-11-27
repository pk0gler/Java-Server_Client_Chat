package streamDecorater;

import java.io.IOException;

/**
 * Created by philippkogler on 27/11/16.
 * ChatStream interface
 * Basic Chat Functionality
 */
public abstract class ChatStream implements AutoCloseable {
    /**
     * Read from an InputStream
     *
     * @return @{@link Object} read Object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public abstract Object read() throws IOException, ClassNotFoundException;

    /**
     * Write to an OutputStream
     *
     * @param o @{@link Object} Object to write
     * @throws IOException
     */
    public abstract void write(Object o) throws IOException;
}
