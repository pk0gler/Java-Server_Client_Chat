package client;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by pkogler on 26/11/2016.
 */
public class Person implements Serializable {
    private String vname;
    private String nname;

    public Person(String vname, String nname) {
        this.vname = vname;
        this.nname = nname;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "vname='" + vname + '\'' +
                ", nname='" + nname + '\'' +
                '}';
    }
}
