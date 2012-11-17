package com.especialcursos.tema11.serviciosenlazados;


import com.especialcursos.tema11.serviciosenlazados.CalcularServicio.CalcularBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class PrincipalActivity extends Activity {

	private CalcularBinder mBinder;
	boolean mServicioUnido = false;
	
	private EditText etAltura;
	private EditText etBase;
	private EditText etVolumen;
	private EditText etArea;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        
        etAltura = (EditText) findViewById(R.id.et_altura);
        etBase = (EditText) findViewById(R.id.et_base);
        etVolumen = (EditText) findViewById(R.id.et_volumen);
        etArea = (EditText) findViewById(R.id.et_area);
        
        //bloqueamos las casillas de altura y volumen para que
        //no se puedan editar
        etVolumen.setKeyListener(null);
        etArea.setKeyListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_principal, menu);
        return true;
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	//Se une con el servicio
    	Intent intent = new Intent(this, CalcularServicio.class);
    	bindService(intent, mConexion, Context.BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	//Desconexion del servicio
    	if(mServicioUnido){
    		unbindService(mConexion);
    		mServicioUnido = false;
    	}
    }
    public void calcular(View v){
    	if (mServicioUnido && camposValidos()){
    		float area = mBinder
    				.getArea(Float.valueOf(etAltura.getText().toString()),
    						Float.valueOf(etBase.getText().toString()));
    		float volumen = mBinder
    				.getVolumen(Float.valueOf(etAltura.getText().toString()),
    						Float.valueOf(etBase.getText().toString()));
    		String for_2dec = "%.2f";
    		etArea.setText(String.format(for_2dec, area));
    		etVolumen.setText(String.format(for_2dec,volumen));
    	}
    }
    
    public boolean camposValidos(){
    	boolean validos = false;
    	if (etAltura.getText().toString().length() > 0 && etBase.getText().toString().length() > 0){
    		try {
				float altura = Float.parseFloat(etAltura.getText().toString());
				float radio_base = Float.parseFloat(etBase.getText().toString());
				if (!Float.isNaN(altura) || !Float.isNaN(radio_base)){
					validos = true;
				}
			} catch (NumberFormatException e) {
				validos = false;
				e.printStackTrace();
			}
    	}
    	return validos;
    }
    
    private ServiceConnection mConexion = new ServiceConnection() {
		
		public void onServiceDisconnected(ComponentName name) {
			mServicioUnido = false;
		}
		
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBinder = (CalcularBinder) service;
			mServicioUnido = true;
		}
	};
}
