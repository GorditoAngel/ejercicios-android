package com.especialcursos.tema13.intents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.especialcursos.tema13.intents.ListaCitas.CitaAdapter;

import android.os.Bundle;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class CalendarioBasico extends Activity {
	
	BDCalendario helper;
	
	EditText etDescripcion;
	EditText etLugar;
	EditText etAvisar;
	Button btFecha;
	
	GregorianCalendar fecha;
	String str_fecha;
	boolean fecha_selecionada;
	
	static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMMM/yyyy HH:mm");
	
    @Override
    public void onCreate(Bundle savedInstanceState) { 	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_basico);
        etDescripcion = (EditText) findViewById(R.id.et_titulo);
        etLugar = (EditText) findViewById(R.id.et_lugar);
        etAvisar = (EditText) findViewById(R.id.et_avisar);
        btFecha = (Button) findViewById(R.id.bt_fecha);
        
        //Una vez que esta toda la parte visual definida
        //vemos el intent que nos ha llamado a ver que tiene
        String action_caller = getIntent().getAction();
        helper = new BDCalendario(this);
        //hay tres opciones + la de main. esta ultima solo abre el fomulario en blanco
        //com.especialcursos.tema13.intents.CITA_NUEVA
        //com.especialcursos.tema13.intents.CITA_GRABAR
        //com.especialcursos.tema13.intents.LISTA_CITAS
        
        if (action_caller.equals("com.especialcursos.tema13.intents.CITA_NUEVA") ||
        		action_caller.equals("android.intent.action.MAIN")){
        	introducirNuevaCita();
        }else  if (action_caller.equals("com.especialcursos.tema13.intents.CITA_GRABAR") ){
        	//tienen que venir los datos en el intent
        	grabarNuevaCita(getIntent().getExtras());
        }else  if (action_caller.equals("com.especialcursos.tema13.intents.LISTA_CITAS") ){
        	//tiene que tener un intent que seleccione que tipo de lista hay que mostrar
        	mostrarLista(getIntent().getExtras());
        }
    }

    private void mostrarLista(Bundle extras) {
		// Mirar que tipo de lista quiere
    	String tipo_lista = extras.getString("tipo_lista");
    	if (tipo_lista != null){
    		if (tipo_lista.equals("hoy")) botonDia(null);
    		if (tipo_lista.equals("semana")) botonSemana(null);
    		if (tipo_lista.equals("mes")) botonMes(null);
    	}else{
    		Toast.makeText(this, R.string.fallo_intent_nohaydatos, Toast.LENGTH_SHORT).show();
    		finish();
    	}
	}

	private void grabarNuevaCita(Bundle extras) {
		//cogemos todos los datos
		String descripcion = extras.getString("descripcion");
		String lugar = extras.getString("lugar");
		long fecha = extras.getLong("fecha_inMilis");
		int avisar = extras.getInt("avisar");
		if ((descripcion == null) ||
				(fecha == 0)){
			Toast.makeText(this, R.string.fallo_intent_nohaydatos, Toast.LENGTH_SHORT).show();
			// auna mala si no hay datos suficientes dejamos todo en blanco
			introducirNuevaCita();
		}else{
			etDescripcion.setText(descripcion);
			if (lugar != null) etLugar.setText(lugar);
			if (avisar != 0) etAvisar.setText(avisar);
			else etAvisar.setText("0");
			
			//delicado tratamiento de la fecha
			this.fecha = new GregorianCalendar();
			this.fecha.setTimeInMillis(fecha);
			str_fecha = sdf.format(this.fecha.getTime());
	    	btFecha.setText(str_fecha);
	    	fecha_selecionada = true;
	    	
	    	
		}
	}

	private void introducirNuevaCita() {
		etAvisar.setText("0");
        fecha = new GregorianCalendar();
        str_fecha = null;
        fecha_selecionada = false;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calendario_basico, menu);
        return true;
    }
    
    /**
     * Accion al pulsar agregar cita
     * @param v
     */
    public void agregarCita(View v){
    	if (todosCamposListos()){
	    	helper.insert(etDescripcion.getText().toString(), 
	    			etLugar.getText().toString(), 
	    			fecha.getTimeInMillis(), 
	    			Integer.parseInt(etAvisar.getText().toString()));
	    	
	    	etDescripcion.getText().clear();
	        etLugar.getText().clear();
	        etAvisar.getText().clear();
    	}else{
    		Toast.makeText(this, R.string.toast_faltan_campos, Toast.LENGTH_SHORT).show();
    	}
    	
    }
    
    private boolean todosCamposListos(){
    	boolean resultado = false;
    	if (fecha_selecionada &&
    			etDescripcion.getText().length() > 0 &&
    			etAvisar.getText().length() > 0 &&
    			etLugar.getText().length() > 0){
    		resultado = true;
    	}
    	return resultado;
    }
    
    /**
     * Acción boton fecha
     * @param v
     */
    public void botonFecha(View v){
    	DialogFragment newFragment = new DatePickerFragment();
    	newFragment.show(getFragmentManager(), "datePicker");
    }
    
    /**
     * Acción botón día
     * @param v
     */
    public void botonDia(View v){
    	Intent intent = new Intent("com.especialcursos.tema13.intents.VER_CITAS_HOY");
    	startActivity(intent);
    }
    
    /**
     * Acción botón semana
     * @param v
     */
    public void botonSemana(View v){
    	Intent intent = new Intent("com.especialcursos.tema13.intents.VER_CITAS_SEMANA");
    	startActivity(intent);
    }
    
    /**
     * Acción botón mes
     * @param v
     */
    public void botonMes(View v){
    	Intent intent = new Intent("com.especialcursos.tema13.intents.VER_CITAS_MES");
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
