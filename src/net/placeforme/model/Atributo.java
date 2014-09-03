package net.placeforme.model;


/**
 * Created by Gustavo on 13/08/2014.
 */
public class Atributo {

    private int atributo_id;
    private String titulo;
    private int padrao;
    private int status;


    public int getAtributoId() {
        return atributo_id;
    }

    public void setAtributoId(int atributo_id) {
        this.atributo_id = atributo_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPadrao() {
        return padrao;
    }

    public void setPadrao(int padrao) {
        this.padrao = padrao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
