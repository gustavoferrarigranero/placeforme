package net.placeforme;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

import net.placeforme.model.Evento;
import net.placeforme.model.EventoDao;
import net.placeforme.model.Grupo;
import net.placeforme.model.GrupoDao;
import net.placeforme.util.Conv;
import net.placeforme.util.DatePickerFragment;
import net.placeforme.util.TimePickerFragment;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnHoverListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AddEventoActivity extends FragmentActivity implements OnItemSelectedListener {
	
	public static AddEventoActivity criarEventoActivity;
	
	private EditText tituloText;
	private EditText dataText;
	private EditText horariotext;
	private Spinner grupoSpinner;
	private Button buttonSaveEvento;
	private ArrayList<String> arrayList;
	private ArrayList<Grupo> arrayListGrupo;
	private ArrayAdapter<String> arrayAdapter;
	private Evento newEvento;
	private EventoDao eventoDao;
	private GrupoDao grupoDao;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_criar_evento);
		
		criarEventoActivity = this;
		
		newEvento = new Evento();
		grupoDao = new GrupoDao(this);
		eventoDao = new EventoDao(this);
		
		tituloText = (EditText) findViewById(R.id.evento_titulo);
		
		dataText = (EditText) findViewById(R.id.evento_data_inicio);
		dataText.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == event.ACTION_DOWN){
					showDatePickerDialog(dataText);
				}
				return false;
			}
		});
		
		dataText.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==event.ACTION_DOWN)
					showDatePickerDialog(dataText);
				return false;
			}
		});
		
		horariotext = (EditText) findViewById(R.id.evento_horario);
		horariotext.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == event.ACTION_DOWN){
					showTimePickerDialog(horariotext);
				}
				return false;
			}
		});
		
		horariotext.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==event.ACTION_DOWN)
					showTimePickerDialog(horariotext);
				return false;
			}
		});

		
		arrayList = new ArrayList<String>();
		arrayListGrupo = new ArrayList<Grupo>();
		
		for(Grupo grupo : grupoDao.getAll()){
			arrayList.add(grupo.getTitulo());
			arrayListGrupo.add(grupo);
		}
		
		grupoSpinner = (Spinner) findViewById(R.id.evento_grupo);

		arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
		// Specify the layout to use when the list of choices appears
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		grupoSpinner.setAdapter(arrayAdapter);
		
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
	
	//metodos do spinner do grupo
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		newEvento.setGrupoId(arrayListGrupo.get(pos).getGrupoId());		
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.criar_evento, menu);
		return true;
	} 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
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
	
	private void saveEvento(){
		
		newEvento.setTitulo(tituloText.getText().toString());
		newEvento.setDataInicio(Conv.stringToSqlDate(dataText.getText().toString()));
		newEvento.setHorario(Conv.stringToSqlTime(horariotext.getText().toString()));
		//setgrupo_id is set by spinner
		newEvento.setUsuarioId(MainActivity.usuarioLogado.getUsuarioId());
		newEvento.setStatus(1);
		
		Log.d("INFO","setou");
		
		long evento_id = eventoDao.add(newEvento);
		
		Log.d("INFO","add "+evento_id);
		
		if(evento_id>0){
			Log.d("INFO","if1 ");
			
			Intent mIntent = new Intent(this, EditEventoActivity.class);

			mIntent.putExtra("evento_id",evento_id);
			
			startActivity(mIntent);
		}
		
	}
	
}
