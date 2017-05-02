package nfc.itu.mbds.churchprog.modele;

import java.io.Serializable;
import java.util.List;

/**
 * Created by T7 on 22/03/2017.
 */

public class Programme implements Serializable {
    private Integer prog_id;
    private String prog_date;
    private String prog_desc;
    private List<Contenu> contenu;


    public Programme() {
    }

    public Programme(Integer prog_id, String prog_date, String prog_desc) {
        this.prog_id = prog_id;
        this.prog_date = prog_date;
        this.prog_desc = prog_desc;
    }

    public Integer getProg_id() {
        return prog_id;
    }

    public void setProg_id(Integer prog_id) {
        this.prog_id = prog_id;
    }

    public String getProg_date() {
        return prog_date;
    }

    public void setProg_date(String prog_date) {
        this.prog_date = prog_date;
    }

    public String getProg_desc() {
        return prog_desc;
    }

    public void setProg_desc(String prog_desc) {
        this.prog_desc = prog_desc;
    }

    public List<Contenu> getContenu() {
        return contenu;
    }

    public void setContenu(List<Contenu> contenu) {
        this.contenu = contenu;
    }
}
