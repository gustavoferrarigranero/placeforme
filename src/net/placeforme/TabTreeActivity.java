package net.placeforme;

import java.util.ArrayList;
import java.util.List;

import net.placeforme.model.Evento;
import net.placeforme.model.EventoDao;
import net.placeforme.util.AdapterListEventos;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class TabTreeActivity extends Fragment {

	private static ListView listaOld;
	private static EventoDao eventoDao;
	private static List<Evento> eventos;
	private static AdapterListEventos adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (!isAdded()) {
		}

		View view = inflater.inflate(R.layout.fragment_tab_tree, container,
				false);

		listaOld = (ListView) view.findViewById(R.id.listaOlds);

		eventoDao = new EventoDao(container.getContext());

		populaLista();

		return view;

	}

	private void populaLista() {

		eventos = new ArrayList<Evento>();

		eventos = eventoDao.getAllOlds();

		adapter = new AdapterListEventos(
				MainActivity.mainActivity, eventos);
		
		listaOld.setAdapter(adapter);

	}
	
	public static void populaListaStatic() {

		eventos = new ArrayList<Evento>();

		eventos = eventoDao.getAllOlds();

		adapter = new AdapterListEventos(
				MainActivity.mainActivity, eventos);

		listaOld.setAdapter(adapter);

	}

	public static void populaListaStaticFiltros(String titulo, String data,
			int grupo_id) {
		
		eventos = new ArrayList<Evento>();

		eventos = eventoDao.getAllOldsFiltros(titulo, data, grupo_id);
		
		AdapterListEventos adapter2 = new AdapterListEventos(MainActivity.mainActivity, eventos);
		
		listaOld.setAdapter(null);
		
		listaOld.setAdapter(adapter2);

	}
}




