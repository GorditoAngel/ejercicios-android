package com.especialcursos.tema13.intentsImplicitos;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;


import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.format.DateFormat;
import com.especialcursos.tema13.intentsImplicitos.R;

public class IntentsCalendario extends Activity {

    private EditText etDescripcion;
	private Button btFecha;
	GregorianCalendar fecha;
	String str_fecha;
	boolean fecha_selecionada;
	static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMMM/yyyy HH:mm");

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intents_calendario);
        etDescripcion = (EditText) findViewById(R.id.et_titulo);
        btFecha = (Button) findViewById(R.id.bt_fecha);
        fecha = new GregorianCalendar();
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_intents_calendario, menu);
        return true;
    }

    public void botonFecha(View view){
    	DialogFragment newFragment = new DatePickerFragment();
    	newFragment.show(getFragmentManager(), "datePicker");
    }
    
    public void botonNueva(View view){
    	Intent intent = new Intent("com.especialcursos.tema13.intents.CITA_NUEVA");
    	startActivity(intent);	
    }
    
    public void botonGuardar(View view){
    	if (fecha_selecionada && 
    			etDescripcion.getText().length() > 0){
    		Intent intent = new Intent("com.especialcursos.tema13.intents.CITA_GRABAR");
    		intent.putExtra("descripcion", etDescripcion.getText().toString());
    		intent.putExtra("fecha_inMilis", fecha.getTimeInMillis());
    		startActivity(intent);
    	}else{
    		Toast.makeText(this, R.string.toast_faltan_campos, Toast.LENGTH_SHORT).show();
    	}
    }
    
    public void botonDia(View view){
    	Intent intent = new Intent("com.especialcursos.tema13.intents.LISTA_CITAS");
    	intent.putExtra("tipo_lista", "hoy");
    	startActivity(intent);	
    }
    
    public void botonSemana(View view){
    	Intent intent = new Intent("com.especialcursos.tema13.intents.LISTA_CITAS");
    	intent.putExtra("tipo_lista", "semana");
    	startActivity(intent);	
    }
    
    public void botonMes(View view){
    	Intent intent = new Intent("com.especialcursos.tema13.intents.LISTA_CITAS");
    	intent.putExtra("tipo_lista", "mes");
    	startActivity(intent);	
    }
    
    public  class DatePickerFragment extends DialogFragment 
		implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			
			final GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
			int year = c.get(GregorianCalendar.YEAR);
			int month = c.get(GregorianCalendar.MONTH);
			int day = c.get(GregorianCalendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			setFecha(year, month, day);
		}
    }
    
    public  class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			// Use the current time as the default values for the picker
			final GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
			int hour = c.get(GregorianCalendar.HOUR_OF_DAY);
			int minute = c.get(GregorianCalendar.MINUTE);
			
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
		}
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			setHora(hourOfDay, minute);
		}
	}
    
    public  void setFecha(int year, int month, int day){
    	fecha.set(year, month, day);
    	DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
    
    public void setHora(int hourOfDay, int minute){
    	fecha.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
    	fecha.set(GregorianCalendar.MINUTE, minute);
    	str_fecha = sdf.format(fecha.getTime());
    	btFecha.setText(str_fecha);
    	fecha_selecionada = true;
    }    
   
    
}
