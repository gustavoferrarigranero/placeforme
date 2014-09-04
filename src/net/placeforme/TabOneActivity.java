package net.placeforme;

import java.util.ArrayList;

import net.placeforme.model.Grupo;
import net.placeforme.model.GrupoDao;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class TabOneActivity extends Fragment {

	private ArrayList<Grupo> arrayListGrupo;
	private ArrayList<String> arrayListStringGrupo;
	
	@Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
		
    		View view = inflater.inflate(R.layout.fragment_tab_one, container, false);
    		
    		final EditText tituloText = (EditText)view.findViewById(R.id.busca_titulo);
    		final EditText dataText = (EditText)view.findViewById(R.id.busca_data);
    		final Spinner grupoCampoSpinner = (Spinner)view.findViewById(R.id.busca_grupo);
    		Button buttonFiltro = (Button)view.findViewById(R.id.button_filtro);
    		
    		
    		GrupoDao grupoDao = new GrupoDao(container.getContext());
    		
    		arrayListGrupo = new ArrayList<Grupo>();
    		arrayListStringGrupo = new ArrayList<String>();
    	
    		Grupo g = new Grupo();
    		g.setGrupoId(0);
    		g.setStatus(1);
    		g.setTitulo("Selecione um grupo");
    		
    		arrayListGrupo.add(g);
			arrayListStringGrupo.add(g.getTitulo());
    		
			for (Grupo grupo : grupoDao.getAll()) {
				arrayListGrupo.add(grupo);
    			arrayListStringGrupo.add(grupo.getTitulo());
    		}

    		ArrayAdapter<String> arrayAdapterGrupo = new ArrayAdapter<String>(container.getContext(),
    				android.R.layout.simple_spinner_item, arrayListStringGrupo);

    		arrayAdapterGrupo
    				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    		grupoCampoSpinner.setAdapter(arrayAdapterGrupo);
    		
    		buttonFiltro.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					String titulo = tituloText.getText().toString();
					String data = dataText.getText().toString();
					int grupo_id = arrayListGrupo.get(grupoCampoSpinner.getSelectedItemPosition()).getGrupoId();
					
					TabTwoActivity.populaListaStaticFiltros(titulo,data,grupo_id);
					
					MainActivity.actionBarStatic.selectTab(MainActivity.actionBarStatic.getTabAt(1));
					
				}
			});
    		
    		return view;
    		
    }


}
