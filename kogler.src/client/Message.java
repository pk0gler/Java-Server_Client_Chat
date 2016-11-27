package client;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by pkogler on 26/11/2016.
 * This Class holds a generic Message and is used
 * to writeSerialized Objects via ObjectOutputStream
 */
public class Message<E> implements Serializable {
    /**
     * E Message
     */
    private E message;

    /**
     * Timestamp actual
     */
    private Timestamp timestamp;

    /**
     * Username
     */
    private String usr = "";

    /**
     * Command
     * Whether a log is used or not
     */
    private String command = "";

    /**
     * Message Constructor
     * Initializes attributes and takes the generic Message
     *
     * @param message generic Message
     */
    public Message(E message) {
        this.message = message;
        Date date = new Date();
        this.timestamp = new Timestamp(date.getTime());
    }

    /**
     * @see Object
     *
     * @return
     */
    @Override
    public String toString() {
        String output = "";
        if (!this.usr.equals("Server")) {
            output = (this.usr.equals("")) ?
                    "<> New Message" + "\n" +
                            "<> Timestamp\t" + this.timestamp + "\n" +
                            "<> Content\t\t" + this.message.toString() + "\n"

                    :

                    "<> New Message from " + this.usr + "\n" +
                            "<> Timestamp\t" + this.timestamp + "\n" +
                            "<> Content\t\t" + this.message.toString() + "\n";
        } else {
            output = "<<>> New SERVER Message" + "\n" +
                    "<<>> Timestamp\t" + this.timestamp + "\n" +
                    "<<>> Content\t\t" + this.message.toString() + "\n";
        }
        return output;
    }

    /**
     *
     * @return
     */
    public E getMessage() {
        return message;
    }

    /**
     *
     * @return
     */
    public String getUsr() {
        return usr;
    }

    /**
     *
     * @param usr
     */
    public void setUsr(String usr) {
        this.usr = usr;
    }

    /**
     *
     * @param message
     */
    public void setMessage(E message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return
     */
    public String getCommand() {
        return command;
    }

    /**
     *
     * @param command
     */
    public void setCommand(String command) {
        this.command = command;
    }
}
