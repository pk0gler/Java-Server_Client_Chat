package client;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by pkogler on 26/11/2016.
 * The Person class has attributes for storing a person object
 */
public class Person implements Serializable {
    /**
     * Forename
     */
    private String vname;
    /**
     * Surname
     */
    private String nname;

    /**
     * Person Constructor
     * Initializes a new Person Object
     *
     * @param vname
     * @param nname
     */
    public Person(String vname, String nname) {
        this.vname = vname;
        this.nname = nname;
    }

    /**
     *
     * @return
     */
    public String getVname() {
        return vname;
    }

    /**
     *
     * @param vname
     */
    public void setVname(String vname) {
        this.vname = vname;
    }

    /**
     *
     * @return
     */
    public String getNname() {
        return nname;
    }

    /**
     *
     * @param nname
     */
    public void setNname(String nname) {
        this.nname = nname;
    }

    /**
     * @see Object
     *
     * @return
     */
    @Override
    public String toString() {
        return "Person Object" +
                "\n\t\t\t\tvname :\t" + vname + "\n\t\t\t\t" +
                "nname :\t" + nname + "\n";
    }
}
