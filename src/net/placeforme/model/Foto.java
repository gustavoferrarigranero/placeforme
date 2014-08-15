package net.placeforme.model;

import android.graphics.Bitmap;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class Foto {

    private int foto_id;
    private String legenda;
    private Bitmap foto;
    private int evento_id;
    private int status;


    public int getFotoId() {
        return foto_id;
    }

    public void setFotoId(int foto_id) {
        this.foto_id = foto_id;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
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
