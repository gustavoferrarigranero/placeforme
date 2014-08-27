package net.placeforme;

import java.util.ArrayList;
import java.util.List;

import net.placeforme.model.Evento;
import net.placeforme.model.EventoDao;
import net.placeforme.util.Conv;
import net.placeforme.util.MeuAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;


public class TabTwoActivity extends Fragment {

	private static ListView lista;
	private static EventoDao eventoDao;
	private static List<Evento> eventos;
	private static Evento evento;
	private ImageView add_evento;
	private static ViewGroup container;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		this.container = container;
		
		View view = inflater.inflate(R.layout.fragment_tab_two, container, false);
		
		lista = (ListView) view.findViewById(R.id.lista);
		
		eventoDao = new EventoDao(container.getContext());
		
		add_evento = (ImageView) view.findViewById(R.id.add_evento);
		
		add_evento.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent criarEvento = new Intent(getActivity(),CriarEventoActivity.class);
				startActivity(criarEvento);
			}
		});
				
		populaLista();
		
		return view;
    		
    }
	
	private void populaLista(){
		
		eventos = new ArrayList<Evento>();
		
		//eventos = eventoDao.getAll();
		
		evento = new Evento();
		evento.setEventoId(1);
		evento.setTitulo("Festa Top 1");
		evento.setDataInicio(Conv.stringToSqlDate("20/08/2014"));
		evento.setDataFim(Conv.stringToSqlDate("20/08/2014"));
		evento.setGrupoId(1);
		evento.setUsuarioId(MainActivity.usuarioLogado.getUsuarioId());
		evento.setStatus(1);
		
		eventos.add(evento);
		eventos.add(evento);
		eventos.add(evento);
		eventos.add(evento);

		MeuAdapter adapter = new MeuAdapter(getActivity(), eventos);
		
		lista.setAdapter(adapter);
		
	}
	
	public static void populaListaFiltros(){
		
		eventos = new ArrayList<Evento>();
		
		//eventos = eventoDao.getAll();
		
		evento = new Evento();
		evento.setEventoId(1);
		evento.setTitulo("Festa Top 1");
		evento.setDataInicio(Conv.stringToSqlDate("20/08/2014"));
		evento.setDataFim(Conv.stringToSqlDate("20/08/2014"));
		evento.setGrupoId(1);
		evento.setUsuarioId(MainActivity.usuarioLogado.getUsuarioId());
		evento.setStatus(1);
		
		eventos.add(evento);
		eventos.add(evento);
		eventos.add(evento);
		eventos.add(evento);

		MeuAdapter adapter = new MeuAdapter(MainActivity.mainActivity, eventos);
		
		lista.setAdapter(adapter);
		
	}


}
