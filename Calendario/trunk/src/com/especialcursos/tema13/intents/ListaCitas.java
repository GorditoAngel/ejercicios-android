package com.especialcursos.tema13.intents;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class ListaCitas extends ListActivity {
	
	public static int VER_CITAS_HOY = 0;
	public static int VER_CITAS_SEMANA = 1;
	public static int VER_CITAS_MES = 2;
	public static int VER_CITAS = 3;
	
	private int accion; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}
