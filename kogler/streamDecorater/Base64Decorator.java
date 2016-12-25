package streamDecorater;

import client.Message;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Base64;

/**
 * Created by pkogler on 25/12/2016.
 */
public class Base64Decorator extends StreamDecorator {
    private ChatStream inner;


    /**
     * {@link StreamDecorator} Constructor
     *
     * @param inner
     */
    public Base64Decorator(ChatStream inner) {
        super(inner);
        this.inner = inner;
    }

    /**
     * @param o @{@link Object} Object to write
     * @throws IOException
     * @see ChatStream
     * <p>
     * Write to inner {@link java.io.OutputStream} with encrypted {@link com.sun.xml.internal.org.jvnet.staxex.Base64Encoder}
     */
    @Override
    public void write(Object o) throws IOException {
        byte[] msg = ((Message<String>) o).getMessage().getBytes();
        byte[] encodedMsg = Base64.getEncoder().encode(msg);
        System.out.println("base64 encoded Message: " + new String(encodedMsg));
        Message<String> old = (Message<String>)o;
        old.setMessage(new String(encodedMsg));
        this.inner.write(old);
    }

    /**
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @see ChatStream
     */
    @Override
    public Object read() throws IOException, ClassNotFoundException {
        Message<String> a = (Message<String>) super.read();
        byte[] msg = a.getMessage().getBytes();
        byte[] decodedBytes = Base64.getDecoder().decode(msg);
        a.setMessage(new String(decodedBytes));
        return a;

    }
}
