package streamDecorater;


import java.io.IOException;

/**
 * Created by philippkogler on 27/11/16.
 * Decorator for ChatStream
 * Adds SadFace Decoration
 */
public class SadFaceDecorator extends StreamDecorator {
    public SadFaceDecorator(ChatStream inner) {
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
        return super.read()+"\t:(";
    }
}
