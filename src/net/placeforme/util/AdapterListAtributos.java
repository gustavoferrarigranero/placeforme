package net.placeforme.util;

import java.util.ArrayList;
import java.util.List;

import net.placeforme.R;
import net.placeforme.model.Atributo;
import net.placeforme.model.AtributoDao;
import net.placeforme.model.Evento;
import net.placeforme.model.EventoAtributo;
import net.placeforme.model.Usuario;
import net.placeforme.model.UsuarioDao;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterListAtributos extends BaseAdapter {

    private Context context;
    private View view;
    private LayoutInflater inflater;
    
    private List<EventoAtributo> eventoAtributos;
    private EventoAtributo eventoAtributo;
    
    private AtributoDao atributoDao;
    
    private TextView campoTitulo ;
    private TextView campoValor;
    

    public AdapterListAtributos(Context context, ArrayList<EventoAtributo> eventoAtributos){
        this.context = context;
        this.eventoAtributos = eventoAtributos;
    }

    @Override

    public int getCount() {
        return eventoAtributos.size();
    }


    @Override

    public Object getItem(int position) {
        return eventoAtributos.get(position);
    }

    @Override

    public long getItemId(int position) {
    	return position;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.list_atributos, null);
        
        atributoDao = new AtributoDao(parent.getContext());

        eventoAtributo = eventoAtributos.get(position);
        
        campoTitulo = (TextView) view.findViewById(R.id.atributo_titulo);
        campoValor = (TextView) view.findViewById(R.id.atributo_valor);

        campoTitulo.setText(atributoDao.get(eventoAtributo.getAtributoId()).getTitulo()+":");
        campoValor.setText(eventoAtributo.getTexto());
        
        return view;
        
    }

	
}