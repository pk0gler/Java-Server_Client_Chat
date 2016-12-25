package streamDecorater;

import client.Message;

import java.io.IOException;

/**
 * Created by pkogler on 25/12/2016.
 */
public class HashDecorator extends StreamDecorator {
    private ChatStream inner;

    /**
     * {@link StreamDecorator} Constructor
     *
     * @param inner
     */
    public HashDecorator(ChatStream inner) {
        super(inner);
        this.inner = inner;
    }

    /**
     * @param o @{@link Object} Object to write
     * @throws IOException
     * @see ChatStream
     *
     * Generate Hash with {@link Object}{@link #hashCode()}
     */
    @Override
    public void write(Object o) throws IOException {
        String msg = ((Message<String>) o).getMessage();
        String hashMsg = msg.hashCode()+"";
        this.inner.write(new Message<String>(hashMsg));
    }
}
