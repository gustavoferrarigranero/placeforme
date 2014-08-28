package net.placeforme;

import net.placeforme.util.DatePickerFragment;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class CriarEventoActivity extends FragmentActivity {
	
	public static CriarEventoActivity criarEventoActivity;
	
	private EditText tituloText;
	private EditText dataText;
	private EditText horariotext;
	//grupo?

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_criar_evento);
		
		criarEventoActivity = this;
		
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
}
