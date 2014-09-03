package net.placeforme.model;


/**
 * Created by Gustavo on 13/08/2014.
 */
public class Grupo {

    private int grupo_id;
    private String titulo;
    private int status;

    public int getGrupoId() {
        return grupo_id;
    }

    public void setGrupoId(int grupo_id) {
        this.grupo_id = grupo_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
