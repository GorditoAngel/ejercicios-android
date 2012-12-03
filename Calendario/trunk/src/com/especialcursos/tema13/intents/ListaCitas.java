package com.especialcursos.tema13.intents;

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
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ListaCitas extends ListActivity {
	
	public static int VER_CITAS_HOY = 0;
	public static int VER_CITAS_SEMANA = 1;
	public static int VER_CITAS_MES = 2;
	public static int VER_CITAS = 3;
	
	private int accion;
	Cursor datos;
	CitaAdapter adapter; 
	
	private BDCalendario helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        helper = new BDCalendario(this);
        
        datos = helper.getAll();
        startManagingCursor(datos);
        adapter = new CitaAdapter(this, datos);
        setListAdapter(adapter);
        
        String action_caller = getIntent().getAction();
        if (action_caller.equals("com.especialcursos.tema13.intents.VER_CITAS_HOY") ){
        	accion = VER_CITAS_HOY;
        }else if(action_caller.equals("com.especialcursos.tema13.intents.VER_CITAS_SEMANA")){
        	accion = VER_CITAS_SEMANA;
        }else if(action_caller.equals("com.especialcursos.tema13.intents.VER_CITAS_MES")){
        	accion = VER_CITAS_MES;
        }else{
        	//como defecto vemos todas
        	accion = VER_CITAS;
        }
        
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
    
    @SuppressLint("NewApi")
	public class CitaAdapter extends CursorAdapter{

    	public CitaAdapter(Context context, Cursor c) {
    		super(context, c);
    	}

    	@Override
    	public void bindView(View row, Context ctx , Cursor cursor) {
    		TextView descripcion = (TextView) row.findViewById(R.id.tv_row_descripcion);
    		TextView lugar = (TextView) row.findViewById(R.id.tv_row_lugar);
    		TextView fecha = (TextView) row.findViewById(R.id.tv_row_fecha);
    		TextView avisar = (TextView) row.findViewById(R.id.tv_row_avisar);
    		
    		descripcion.setText(helper.getDescripcion(cursor));
    		lugar.setText(helper.getLugar(cursor));
    		fecha.setText(helper.getFechaStr(cursor));
    		avisar.setText(helper.getAvisarStr(cursor));

    	}

    	@Override
    	public View newView(Context context, Cursor cursor, ViewGroup parent) {
    		LayoutInflater inflater = LayoutInflater.from(context);
    		View row = inflater.inflate(R.layout.fila_cita, parent, false);
    		bindView(row, context, cursor);
    		return(row);
    	}

    }

}
