package net.placeforme;

import java.util.ArrayList;

import net.placeforme.model.Atributo;
import net.placeforme.model.AtributoDao;
import net.placeforme.model.Evento;
import net.placeforme.model.EventoAtributo;
import net.placeforme.model.EventoAtributoDao;
import net.placeforme.model.EventoDao;
import net.placeforme.model.Grupo;
import net.placeforme.model.GrupoDao;
import net.placeforme.util.AdapterListAtributos;
import net.placeforme.util.DatePickerFragment;
import net.placeforme.util.TimePickerFragment;
import net.placeforme.util.Utils;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class AddEventoActivity extends FragmentActivity implements
		OnItemSelectedListener {

	public static AddEventoActivity addEventoActivity;

	private EditText tituloText;
	private EditText dataText;
	private EditText horariotext;
	private EditText eventoAtributosValorText;
	private Spinner grupoSpinner;
	private Spinner atributoSpinner;
	private ArrayList<String> arrayListStringGrupo;
	private ArrayList<Grupo> arrayListGrupo;
	private ArrayList<String> arrayListStringAtributos;
	private ArrayList<Atributo> arrayListAtributos;
	private ArrayList<EventoAtributo> arrayListEventoAtributos;
	private Button buttonSaveEvento;
	private Button buttonAddAtributo;
	private Button buttonDialogAddAtributo;
	private ListView listaAtributosScreen;

	private ArrayAdapter<String> arrayAdapterGrupo;
	private ArrayAdapter<String> arrayAdapterAtributo;
	private Evento newEvento;
	private EventoDao eventoDao;
	private GrupoDao grupoDao;
	private AtributoDao atributoDao;
	private EventoAtributoDao eventoAtributoDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_evento);

		// Set up the action bar.
        final ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		
		addEventoActivity = this;

		newEvento = new Evento();
		grupoDao = new GrupoDao(this);
		eventoDao = new EventoDao(this);
		eventoAtributoDao = new EventoAtributoDao(this);
		atributoDao = new AtributoDao(this);

		arrayListEventoAtributos = new ArrayList<EventoAtributo>();
		listaAtributosScreen = (ListView) findViewById(R.id.list_evento_atributos_screen);

		tituloText = (EditText) findViewById(R.id.evento_titulo);

		dataText = (EditText) findViewById(R.id.evento_data_inicio);
		dataText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == event.ACTION_DOWN) {
					showDatePickerDialog(dataText);
				}
				return false;
			}
		});

		dataText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == event.ACTION_DOWN)
					showDatePickerDialog(dataText);
				return false;
			}
		});

		horariotext = (EditText) findViewById(R.id.evento_horario);
		horariotext.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == event.ACTION_DOWN) {
					showTimePickerDialog(horariotext);
				}
				return false;
			}
		});

		horariotext.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == event.ACTION_DOWN)
					showTimePickerDialog(horariotext);
				return false;
			}
		});

		buttonAddAtributo = (Button) findViewById(R.id.button_add_atributo);
		buttonAddAtributo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(
						AddEventoActivity.addEventoActivity);
				dialog.setContentView(R.layout.dialog_add_evento_atributo);
				dialog.setTitle("Adicionar atributos");

				arrayListStringAtributos = new ArrayList<String>();
				arrayListAtributos = new ArrayList<Atributo>();

				for (Atributo atributo : atributoDao.getAll()) {
					arrayListStringAtributos.add(atributo.getTitulo());
					arrayListAtributos.add(atributo);
				}

				atributoSpinner = (Spinner) dialog
						.findViewById(R.id.evento_atributos_spinner);

				arrayAdapterAtributo = new ArrayAdapter<String>(
						addEventoActivity,
						android.R.layout.simple_spinner_item,
						arrayListStringAtributos);

				arrayAdapterAtributo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				atributoSpinner.setAdapter(arrayAdapterAtributo);

				eventoAtributosValorText = (EditText) dialog.findViewById(R.id.evento_atributos_valor);

				buttonDialogAddAtributo = (Button) dialog.findViewById(R.id.button_dialog_add_atributo);

				buttonDialogAddAtributo.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						EventoAtributo eventoAtributo = new EventoAtributo();
						eventoAtributo.setAtributoId(arrayListAtributos.get(atributoSpinner.getSelectedItemPosition())
								.getAtributoId());
						eventoAtributo.setTexto(eventoAtributosValorText.getText().toString());
						arrayListEventoAtributos.add(eventoAtributo);
						populaAtributosList();
						listaAtributosScreen.requestFocus();
						dialog.dismiss();
					}
				});

				dialog.show();

			}
		});

		arrayListStringGrupo = new ArrayList<String>();
		arrayListGrupo = new ArrayList<Grupo>();

		for (Grupo grupo : grupoDao.getAll()) {
			arrayListStringGrupo.add(grupo.getTitulo());
			arrayListGrupo.add(grupo);
		}

		grupoSpinner = (Spinner) findViewById(R.id.evento_grupo);

		arrayAdapterGrupo = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayListStringGrupo);
		// Specify the layout to use when the list of choices appears
		arrayAdapterGrupo
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		grupoSpinner.setAdapter(arrayAdapterGrupo);

		grupoSpinner.setOnItemSelectedListener(this);

		buttonSaveEvento = (Button) findViewById(R.id.button_save_evento);
		buttonSaveEvento.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				saveEvento();
			}
		});

	}
	
	private void populaAtributosList(){
		AdapterListAtributos adapter = new AdapterListAtributos(this, arrayListEventoAtributos);
		listaAtributosScreen.setAdapter(adapter);
		Utils.setListViewHeightBasedOnChildren(listaAtributosScreen);
	}

	// metodos do spinner do grupo
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// An item was selected. You can retrieve the selected item using
		// parent.getItemAtPosition(pos)
		newEvento.setGrupoId(arrayListGrupo.get(pos).getGrupoId());
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else if(id == android.R.id.home){
			this.finish();
	        return true;
		}
			
		return super.onOptionsItemSelected(item);
	}

	public void showDatePickerDialog(EditText edittext) {
		DialogFragment datePicker = new DatePickerFragment(edittext);
		datePicker.show(getFragmentManager(), "Escolha a Data");
	}

	public void showTimePickerDialog(EditText edittext) {
		DialogFragment timePicker = new TimePickerFragment(edittext);
		timePicker.show(getFragmentManager(), "Escolha a Hora");
	}

	private void saveEvento() {

		newEvento.setTitulo(tituloText.getText().toString());
		newEvento.setDataInicio(Utils.stringToSqlDate(dataText.getText()
				.toString()));
		newEvento.setHorario(Utils.stringToSqlTime(horariotext.getText()
				.toString()));
		// setgrupo_id is set by spinner
		newEvento.setUsuarioId(MainActivity.usuarioLogado.getUsuarioId());
		newEvento.setStatus(1);

		long evento_id = eventoDao.add(newEvento);		

		if (evento_id > 0) {
			if (!arrayListEventoAtributos.isEmpty()) {

				for (EventoAtributo ea : arrayListEventoAtributos) {
					ea.setEventoId((int) evento_id);
					eventoAtributoDao.add(ea);
					Log.d("ATTR+", atributoDao.get(ea.getAtributoId()).getTitulo());
				}
			}
			TabTwoActivity.populaListaStatic();
			addEventoActivity.finish();
		}

	}
	
}
