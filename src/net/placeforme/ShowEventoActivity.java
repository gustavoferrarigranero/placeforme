package net.placeforme;

import java.text.ParseException;
import java.util.ArrayList;

import net.placeforme.model.AvaliacaoDao;
import net.placeforme.model.Evento;
import net.placeforme.model.EventoAtributo;
import net.placeforme.model.EventoAtributoDao;
import net.placeforme.model.EventoDao;
import net.placeforme.model.FotoDao;
import net.placeforme.model.UsuarioDao;
import net.placeforme.model.AtributoDao;
import net.placeforme.util.AdapterListAtributos;
import net.placeforme.util.Utils;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class ShowEventoActivity extends Activity {

	public static ShowEventoActivity showEventoActivity;
	
	private Evento evento;
	
	private EventoDao eventoDao;
	private UsuarioDao usuarioDao;
	private FotoDao fotoDao;
	private AvaliacaoDao avaliacaoDao;
	private EventoAtributoDao eventoAtributoDao;
	private AtributoDao atributoDao;
	
	
	private TextView usuarioText;
	private TextView dataText;
	private TextView horarioText;
	private ListView eventoAtributosList;
	
	private ArrayList<EventoAtributo> eventoAtributos;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_evento);
		
		// Set up the action bar.
        final ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		
		showEventoActivity = this;
		
		Bundle bundle = getIntent().getExtras();
		
		int evento_id = bundle.getInt("evento_id");

		eventoDao = new EventoDao(this);
		usuarioDao = new UsuarioDao(this);
		fotoDao = new FotoDao(this);
		avaliacaoDao =  new AvaliacaoDao(this);
		eventoAtributoDao = new EventoAtributoDao(this);
		atributoDao = new AtributoDao(this);
		
		usuarioText = (TextView) findViewById(R.id.evento_usuario);
		dataText = (TextView) findViewById(R.id.evento_data);
		horarioText = (TextView) findViewById(R.id.evento_horario);
		eventoAtributosList = (ListView) findViewById(R.id.evento_atributos);
		
		try {
			evento = eventoDao.get(evento_id);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(null!=evento){
			actionBar.setTitle(evento.getTitulo());			
			usuarioText.setText("Evento criado por: "+usuarioDao.get(evento.getUsuarioId()).getNome());
			dataText.setText("Marcado para o dia: "+Utils.sqlDateToString(evento.getDataInicio()));
			horarioText.setText("Ás: "+Utils.sqlTimeToString(evento.getHorario())+" hs");
			
			eventoAtributos = eventoAtributoDao.getAllByEvento(evento);
			
			populaListAtributos();
			
		}
		
		
	}
	
	private void populaListAtributos(){
		AdapterListAtributos adapter = new AdapterListAtributos(this, eventoAtributos);
		eventoAtributosList.setAdapter(adapter);
		Utils.setListViewHeightBasedOnChildren(eventoAtributosList);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        this.finish();
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
