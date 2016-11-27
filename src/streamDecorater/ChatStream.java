package streamDecorater;

import java.io.IOException;

/**
 * Created by philippkogler on 27/11/16.
 */
public abstract class ChatStream implements AutoCloseable {
    public abstract Object read() throws IOException, ClassNotFoundException;
    public abstract void write(Object o) throws IOException;
}
