package com.especialcursos.tema11.serviciospractica;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class PrincipalActivity extends Activity {

	private EditText mTexto;
	private Button mBoton;
	private int tam_lista;
	private int posicion;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        mTexto = (EditText) findViewById(R.id.editText1);
        mBoton = (Button) findViewById(R.id.button1);
        
        tam_lista = getResources()
        		.getStringArray(R.array.array_planetas)
        		.length;
        posicion = 0;
        
        mBoton.setOnClickListener(new Button.OnClickListener() {	
			public void onClick(View v) {
				botonPresionado();
			}
		});
        
    }

    private void botonPresionado(){
    	boolean textoVacio = mTexto.getText().length() <= 0;
    	if (textoVacio){
    		lanzarServicio();
    		if (posicion < (tam_lista - 1))
    			posicion++;
    		else
    			posicion = 0;
    	}else{
    		lanzarServicio(mTexto.getText().toString());
    		mTexto.getText().clear();
    	}
    		
    }
    
    private void lanzarServicio(){
    	Intent intent = new Intent(this, ServicioPersonalizado.class);
    	intent.putExtra("POSICION", posicion);
    	startService(intent);
    }
    
    private void lanzarServicio(String str){
    	Intent intent = new Intent(this, ServicioPersonalizado.class);
    	intent.putExtra("POSICION", posicion);
    	intent.putExtra("TEXTO", str);
    	startService(intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_principal, menu);
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
    
    @Override
    protected void onStop() {
    	Intent intent = new Intent(this, ServicioPersonalizado.class);
    	stopService(intent);
    	super.onStop();
    }

}
