package net.placeforme.util;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {
	
	private EditText editText;

	public TimePickerFragment(EditText edittext) {
		// TODO Auto-generated constructor stub
		this.editText = edittext;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		String hour = String.valueOf(hourOfDay);
		String minu = String.valueOf(minute);
		
		if(hour.length() == 1){
			hour = "0"+hour;
		}
		if(minu.length() == 1){
			minu = "0"+minu;
		}
		this.editText.setText(hour+":"+minu);

	}
}