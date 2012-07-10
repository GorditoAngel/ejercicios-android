package com.especialcursos.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AplicacionSencillaResults extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		
		Bundle bundle = getIntent().getExtras();
		float segundosPresionado = bundle.getLong("TIEMPOPRESIONADO") / 1000F;
		
		TextView txtMensaje1 = (TextView) findViewById(R.id.textView1);
		TextView txtMensaje2 = (TextView) findViewById(R.id.textView2);
		
		txtMensaje1.setText("Has pulsado la pantalla durante " + 
				segundosPresionado + " segundos");
		txtMensaje2.setText("has pulsado en las coordenadas (" +
				bundle.getInt("X") + "," +
				bundle.getInt("Y") + ")");
	}
	
}
