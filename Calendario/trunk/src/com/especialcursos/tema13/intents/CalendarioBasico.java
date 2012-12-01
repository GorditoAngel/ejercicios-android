package com.especialcursos.tema13.intents;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class CalendarioBasico extends Activity {

	EditText etDescripcion;
	EditText etLugar;
	EditText etAvisar;
	Button btFecha;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_basico);
        etDescripcion = (EditText) findViewById(R.id.et_titulo);
        etLugar = (EditText) findViewById(R.id.et_lugar);
        etAvisar = (EditText) findViewById(R.id.et_avisar);
        btFecha = (Button) findViewById(R.id.bt_fecha);
        
        Intent intent = getIntent();
        String action = intent.getAction();
        
        //Se se ha llamado desde lista
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
    	
    }
    
    /**
     * Acción boton fecha
     * @param v
     */
    public void botonFecha(View v){
    	
    }
    
    /**
     * Acción botón día
     * @param v
     */
    public void botonDia(View v){
    }
    
    /**
     * Acción botón semana
     * @param v
     */
    public void botonSemana(View v){
    }
    
    /**
     * Acción botón mes
     * @param v
     */
    public void botonMes(View v){
    }
}
