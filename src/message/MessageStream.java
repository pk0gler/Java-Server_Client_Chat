package message;

import java.io.*;

/**
 * Created by pkogler on 26/11/2016.
 */
public interface MessageStream {
    void writeToSocket(String  msg) throws IOException;
    String readFromSocket() throws IOException, ClassNotFoundException;
}
