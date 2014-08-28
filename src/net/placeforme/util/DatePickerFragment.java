package net.placeforme.util;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	
	private EditText editText;

	public DatePickerFragment(EditText edittext) {
		// TODO Auto-generated constructor stub
		this.editText = edittext;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH)+1;

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		
		String day = String.valueOf(dayOfMonth);
		String month = String.valueOf(monthOfYear);
		
		if(day.length() == 1){
			day = "0"+day;
		}
		if(month.length() == 1){
			month = "0"+month;
		}
		this.editText.setText(day+"/"+month+"/"+year);
		
	}
}