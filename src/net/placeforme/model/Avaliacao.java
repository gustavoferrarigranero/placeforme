package net.placeforme.model;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class Avaliacao {

    private int avaliacao_id;
    private int nota;
    private String texto;
    private int evento_id;
    private int status;


    public int getAvaliacaoId() {
        return avaliacao_id;
    }

    public void setAvaliacaoId(int avaliacao_id) {
        this.avaliacao_id = avaliacao_id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getEventoId() {
        return evento_id;
    }

    public void setEventoId(int evento_id) {
        this.evento_id = evento_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
