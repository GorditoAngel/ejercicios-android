package com.especialcursos.tema11.serviciospractica;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class PrincipalActivity extends Activity {

	private EditText mTexto;
	private Button mBoton;
	private String[] _lista;
	private int _posicion;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mTexto = (EditText) findViewById(R.id.editText1);
        mBoton = (Button) findViewById(R.id.button1);
        _lista = getResources().getStringArray(R.array.array_planetas);
        _posicion = 0;
        
        mBoton.setOnClickListener(new Button.OnClickListener() {	
			public void onClick(View v) {
				botonPresionado();
			}
		});
        
    }

    private void botonPresionado(){
    	boolean textoVacio = mTexto.getText().length() <= 0;
    	if (textoVacio){
    		mostrarTexto(_lista[_posicion]);
    		//le digo que avance una posicion para la siguiente vez
    		if (_posicion < (_lista.length - 1))
    			_posicion++;
    		else
    			_posicion = 0;
    	}else{
    		mostrarTexto(mTexto.getText().toString());
    		//borrar texto
    		mTexto.getText().clear();
    	}
    		
    }
    
    private void mostrarTexto(String str){
    	
    }
    
    private void lanzarServicio(){
    	Intent intent = new Intent(this, ServicioPersonalizado.class);
    	startService(intent);
    }
    
    private void lanzarServicio(String str){
    	Intent intent = new Intent(this, ServicioPersonalizado.class);
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

}
