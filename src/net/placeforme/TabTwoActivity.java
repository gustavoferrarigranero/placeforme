package net.placeforme;

import java.util.ArrayList;
import java.util.List;

import net.placeforme.model.Evento;
import net.placeforme.model.EventoDao;
import net.placeforme.util.Conv;
import net.placeforme.util.MeuAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class TabTwoActivity extends Fragment {


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab_two, container, false);
		
		ListView lista = (ListView) view.findViewById(R.id.lista);
		
		EventoDao eventoDao = new EventoDao(container.getContext());
				
		List<Evento> eventos = new ArrayList<Evento>();
		
		//eventos = eventoDao.getAll();
		
		Evento evento = new Evento();
		evento.setEventoId(1);
		evento.setTitulo("Festa Top 1");
		evento.setDataInicio(Conv.stringToSqlDate("20/08/2014"));
		evento.setDataFim(Conv.stringToSqlDate("20/08/2014"));
		evento.setGrupoId(1);
		evento.setUsuarioId(1);
		evento.setStatus(1);
		
		Evento evento2 = new Evento();
		evento2.setEventoId(2);
		evento2.setTitulo("Balada Open");
		evento2.setDataInicio(Conv.stringToSqlDate("25/08/2014"));
		evento2.setDataFim(Conv.stringToSqlDate("26/08/2014"));
		evento2.setGrupoId(1);
		evento2.setUsuarioId(1);
		evento2.setStatus(1);
		
		eventos.add(evento);
		eventos.add(evento2);

		MeuAdapter adapter = new MeuAdapter(getActivity(), eventos);
		
		lista.setAdapter(adapter);
		
		
		return view;
    		
    }


}
