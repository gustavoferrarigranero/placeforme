package net.placeforme.util;

import java.util.List;

import net.placeforme.R;
import net.placeforme.model.Evento;
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

public class AdapterListEventos extends BaseAdapter {

    private Context context;
    private View view;
    private LayoutInflater inflater;
    
    private List<Evento> eventos;
    private Evento evento;
    private UsuarioDao usuarioDao;
    private Usuario usuarioEvento;
    
    private TextView campotitulo ;
    private TextView campoData;
    private ImageView campoFoto;
    

    public AdapterListEventos(Context context, List<Evento> eventos){

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

        inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.list_eventos, null);

        evento = eventos.get(position);
        
        usuarioDao = new UsuarioDao(parent.getContext());
        usuarioEvento = new Usuario();
        usuarioEvento = usuarioDao.get(evento.getUsuarioId());
                
        campotitulo = (TextView) view.findViewById(R.id.text);
        campoFoto = (ImageView) view.findViewById(R.id.imageUsuario);

        campotitulo.setText(evento.getTitulo());
        
        campoData = (TextView) view.findViewById(R.id.datainicio);

        campoData.setText("Data: "+Utils.sqlDateToString(evento.getDataInicio()) + 
        		" - Horário: "+Utils.sqlTimeToString(evento.getHorario())+"hs");

        if(null!=usuarioEvento.getFoto()){
        	campoFoto.setImageBitmap(Utils.circleImage(usuarioEvento.getFoto()));
        }
        
        return view;
        
    }

	
}