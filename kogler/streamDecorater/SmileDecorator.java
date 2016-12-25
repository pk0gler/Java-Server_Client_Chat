package streamDecorater;

import client.Message;

import java.io.IOException;

/**
 * Created by philippkogler on 27/11/16.
 * Smile ChatStream Decorator
 */
public class SmileDecorator extends StreamDecorator {
    private ChatStream inner;

    /**
     * SmileDecorator Constructor
     * @param inner
     */
    public SmileDecorator(ChatStream inner) {
        super(inner);
        this.inner = inner;
    }

    /**
     * @param o @{@link Object} Object to write
     * @throws IOException
     * @see ChatStream
     *
     * Add Smile to Message
     */
    @Override
    public void write(Object o) throws IOException {
        String msg = ((Message<String>) o).getMessage();
        String res = msg + " :)";
        this.inner.write(new Message<String>(res));
    }
}
