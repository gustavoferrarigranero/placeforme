package net.placeforme.util;

import java.util.List;

import net.placeforme.R;
import net.placeforme.model.Evento;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MeuAdapter extends BaseAdapter {

    private Context context;
    private List<Evento> eventos;

    public MeuAdapter(Context context, List<Evento> eventos){

        this.context = context;

        this.eventos = eventos;

    }

    @Override

    public int getCount() {

        return eventos.size();

    }


    @Override

    public Object getItem(int position) {

        return eventos.get(position);

    }

    @Override

    public long getItemId(int position) {

        return position;

    }

 

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_eventos, null);

        Evento evento = eventos.get(position);
        
        TextView campotitulo = (TextView) view.findViewById(R.id.text);

        campotitulo.setText(evento.getTitulo());
        
        TextView campoDataInicio = (TextView) view.findViewById(R.id.datainicio);

        campoDataInicio.setText("Início: "+Conv.sqlDateToString(evento.getDataInicio()) + 
        		" - Fim: "+Conv.sqlDateToString(evento.getDataFim()));

        return view;
        
    }

	
}