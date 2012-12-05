package com.especialcursos.tema13.intents;

import java.util.ArrayList;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ListaCitas extends ListActivity {
	
	public static int VER_CITAS_HOY = 0;
	public static int VER_CITAS_SEMANA = 1;
	public static int VER_CITAS_MES = 2;
	public static int VER_CITAS = 3;
	
	private int accion;
	CitaAdapter adapter;
	
	private BDCalendario helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        helper = new BDCalendario(this);
        
        String action_caller = getIntent().getAction();
        if (action_caller.equals("com.especialcursos.tema13.intents.VER_CITAS_HOY") ){
        	accion = VER_CITAS_HOY;
        	ArrayList<Cita> citas =  helper.getHoy();
        	if (citas != null)
        		adapter = new CitaAdapter(this, citas);
        	else adapter = null;
        }else if(action_caller.equals("com.especialcursos.tema13.intents.VER_CITAS_SEMANA")){
        	accion = VER_CITAS_SEMANA;
        	ArrayList<Cita> citas =  helper.getSemana();
        	if (citas != null)
        		adapter = new CitaAdapter(this, citas);
        	else adapter = null;
        }else if(action_caller.equals("com.especialcursos.tema13.intents.VER_CITAS_MES")){
        	accion = VER_CITAS_MES;
        	ArrayList<Cita> citas =  helper.getMes();
        	if (citas != null)
        		adapter = new CitaAdapter(this, citas);
        	else adapter = null;
        }else{
        	//como defecto vemos todas
        	accion = VER_CITAS;
        	adapter = new CitaAdapter(this, helper.getAll());
        }
        if (adapter != null)
        	setListAdapter(adapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lista_citas, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
protected class CitaAdapter extends ArrayAdapter<Cita> {
		
		private ArrayList<Cita> items;
		
        public CitaAdapter(Context context, ArrayList<Cita> citas) {
            super(context, R.layout.fila_cita, citas);
			this.items = citas;
        }
        

        /**
         *Vamos llenando dato por posicion en la lista.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	View v = convertView;

            if (v == null) {
            	LayoutInflater vi;
            	vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	v = vi.inflate(R.layout.fila_cita, null);
            }
            Cita cita =  items.get(position);
           
            if (cita != null) {
            	TextView descripcion = (TextView) v.findViewById(R.id.tv_row_descripcion);
        		TextView lugar = (TextView) v.findViewById(R.id.tv_row_lugar);
        		TextView fecha = (TextView) v.findViewById(R.id.tv_row_fecha);
        		TextView avisar = (TextView) v.findViewById(R.id.tv_row_avisar);

                if (descripcion != null) {
                	descripcion.setText(cita.get_descripcion());
                }
                if (lugar != null) {
                	lugar.setText(cita.get_lugar());
                }
                if (lugar != null) {
                	lugar.setText(cita.get_lugar());
                }
                if (fecha != null) {
                	fecha.setText(CalendarioBasico.sdf.format(cita.get_fecha().getTime()));
                }
                if (avisar != null) {
                	avisar.setText( String.format(getString(R.string.tv_row_avisar),cita.get_avisar()));
                }
            }
            return v;
        }      
    }

}
