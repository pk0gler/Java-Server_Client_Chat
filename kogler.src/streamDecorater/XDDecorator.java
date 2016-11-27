package streamDecorater;

import java.io.IOException;

/**
 * Created by philippkogler on 27/11/16.
 * XDDecorator for ChatStream
 */
public class XDDecorator extends StreamDecorator {
    /**
     * {@link XDDecorator} Constructor
     * @param inner
     */
    public XDDecorator(ChatStream inner) {
        super(inner);
    }

    /**
     * @see ChatStream
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public Object read() throws IOException, ClassNotFoundException {
        return super.read()+"\tXD\n";
    }
}
