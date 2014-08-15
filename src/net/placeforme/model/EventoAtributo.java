package net.placeforme.model;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class EventoAtributo {

    private int evento_atributo_id;
    private String texto;
    private int evento_id;
    private int atributo_id;

    public int getEventoAtributoId() {
        return evento_atributo_id;
    }

    public void setEventoAtributoId(int evento_atributo_id) {
        this.evento_atributo_id = evento_atributo_id;
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

    public int getAtributoId() {
        return atributo_id;
    }

    public void setAtributoId(int atributo_id) {
        this.atributo_id = atributo_id;
    }

}
