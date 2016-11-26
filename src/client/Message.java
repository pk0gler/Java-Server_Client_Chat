package client;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by pkogler on 26/11/2016.
 */
public class Message<E> implements Serializable {
    private E message;
    private Timestamp timestamp;
    private String usr = "";


    public Message(E message) {
        this.message = message;
        Date date = new Date();
        this.timestamp = new Timestamp(date.getTime());
    }

    @Override
    public String toString() {
        return (this.usr.equals("")) ?
                "<> New Message" + "\n" +
                        "<> Timestamp\t" + this.timestamp + "\n" +
                        "<> Content\t\t" + this.message.toString() + "\n"

                :

                "<> New Message from " + this.usr + "\n" +
                        "<> Timestamp\t" + this.timestamp + "\n" +
                        "<> Content\t\t" + this.message.toString() + "\n";
    }

    public E getMessage() {
        return message;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public void setMessage(E message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
