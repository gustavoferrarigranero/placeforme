package net.placeforme.model;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class Evento {

    private int evento_id;
    private String titulo;
    private Date data_inicio;
    private Time horario;
    private int grupo_id;
    private int usuario_id;
    private int status;

    public int getEventoId() {
        return evento_id;
    }

    public void setEventoId(int evento_id) {
        this.evento_id = evento_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataInicio() {
        return data_inicio;
    }

    public void setDataInicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public int getGrupoId() {
        return grupo_id;
    }

    public void setGrupoId(int grupo_id) {
        this.grupo_id = grupo_id;
    }

    public int getUsuarioId() {
        return usuario_id;
    }

    public void setUsuarioId(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
