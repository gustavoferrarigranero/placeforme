package net.placeforme;

import java.util.ArrayList;
import java.util.List;

import net.placeforme.model.Evento;
import net.placeforme.model.EventoDao;
import net.placeforme.util.AdapterListEventos;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;


public class TabTwoActivity extends Fragment {

	private static ListView lista;
	private static EventoDao eventoDao;
	private static List<Evento> eventos;
	private static Evento evento;
	private ImageView add_evento;
	private static ViewGroup container;
	private Intent criarEvento;
	public static Button buttonRemFiltrosStatic;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		if(!isAdded()) {
		}
		
		this.container = container;
		
		View view = inflater.inflate(R.layout.fragment_tab_two, container, false);
		

	    buttonRemFiltrosStatic = (Button) view.findViewById(R.id.button_remove_filtros);
	    
	    buttonRemFiltrosStatic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				populaLista();
			}
		});
		
		lista = (ListView) view.findViewById(R.id.lista);
		
		eventoDao = new EventoDao(container.getContext());
		
		add_evento = (ImageView) view.findViewById(R.id.add_evento);
		
		add_evento.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				criarEvento = new Intent(MainActivity.mainActivity,AddEventoActivity.class);
				MainActivity.mainActivity.startActivity(criarEvento);
			}
		});
				
		populaLista();
		
		return view;
    		
    }
	
	private void populaLista(){
		
		eventos = new ArrayList<Evento>();
		
		eventos = eventoDao.getAll();

		AdapterListEventos adapter = new AdapterListEventos(getActivity(), eventos);
		
		lista.setAdapter(adapter);
		
		buttonRemFiltrosStatic.setVisibility(View.GONE);
		
	}
	
	public static void populaListaStatic(){
		
		eventos = new ArrayList<Evento>();
		
		eventos = eventoDao.getAll();

		AdapterListEventos adapter = new AdapterListEventos(MainActivity.mainActivity, eventos);
		
		lista.setAdapter(adapter);
		
		buttonRemFiltrosStatic.setVisibility(View.GONE);
		
	}
	
	public static void populaListaStaticFiltros(String titulo, String data, int grupo_id){
		
		eventos = new ArrayList<Evento>();
		
		eventos = eventoDao.getAllFiltros(titulo, data, grupo_id);

		AdapterListEventos adapter = new AdapterListEventos(MainActivity.mainActivity, eventos);
		
		lista.setAdapter(adapter);
		
		buttonRemFiltrosStatic.setVisibility(View.VISIBLE);
		
	}


}
