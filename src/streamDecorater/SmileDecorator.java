package streamDecorater;

import java.io.IOException;

/**
 * Created by philippkogler on 27/11/16.
 */
public class SmileDecorator extends StreamDecorator {
    public SmileDecorator(ChatStream inner) {
        super(inner);
    }

    @Override
    public Object read() throws IOException, ClassNotFoundException {
        return super.read()+":)";
    }
}
