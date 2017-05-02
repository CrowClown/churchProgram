package nfc.itu.mbds.churchprog.modele;

import java.io.Serializable;

/**
 * Created by T7 on 22/03/2017.
 */

public class Contenu implements Serializable{
    private Integer c_id;
    private Integer id_prog;
    private String c_type;
    private String c_titre;
    private String c_contenu;
    private Integer c_ordre;

    public Contenu() { }

    public Contenu(Integer c_id, Integer id_prog, String c_type, String c_titre, String c_contenu, Integer c_ordre) {
        this.c_id = c_id;
        this.id_prog = id_prog;
        this.c_type = c_type;
        this.c_titre = c_titre;
        this.c_contenu = c_contenu;
        this.c_ordre = c_ordre;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public Integer getId_prog() {
        return id_prog;
    }

    public void setId_prog(Integer id_prog) {
        this.id_prog = id_prog;
    }

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }

    public String getC_titre() {
        return c_titre;
    }

    public void setC_titre(String c_titre) {
        this.c_titre = c_titre;
    }

    public String getC_contenu() {
        return c_contenu;
    }

    public void setC_contenu(String c_contenu) {
        this.c_contenu = c_contenu;
    }

    public Integer getC_ordre() {
        return c_ordre;
    }

    public void setC_ordre(Integer c_ordre) {
        this.c_ordre = c_ordre;
    }
}
