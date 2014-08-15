package net.placeforme.model;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class Presenca {

    private int presenca_id;
    private int usuario_id;
    private int evento_id;


    public int getPresencaId() {
        return presenca_id;
    }

    public void setPresencaId(int presenca_id) {
        this.presenca_id = presenca_id;
    }

    public int getUsuarioId() {
        return usuario_id;
    }

    public void setUsuarioId(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getEventoId() {
        return evento_id;
    }

    public void setEventoId(int evento_id) {
        this.evento_id = evento_id;
    }
}
