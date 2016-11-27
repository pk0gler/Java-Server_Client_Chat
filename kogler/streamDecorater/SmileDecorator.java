package streamDecorater;

import java.io.IOException;

/**
 * Created by philippkogler on 27/11/16.
 * Smile ChatStream Decorator
 */
public class SmileDecorator extends StreamDecorator {
    /**
     * SmileDecorator Constructor
     * @param inner
     */
    public SmileDecorator(ChatStream inner) {
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
        return super.read()+"\t:)";
    }
}
