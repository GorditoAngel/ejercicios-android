package com.especialcursos.ui;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class AplicacionSencillaActivity extends Activity {
	
	Calendar tiempoInicio = null;
	Calendar tiempoFinal = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				tiempoInicio = Calendar.getInstance();
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				tiempoFinal = Calendar.getInstance();
				Intent intent = new Intent (
						AplicacionSencillaActivity.this,
						AplicacionSencillaResults.class);
				Bundle bundle = new Bundle();
				bundle.putLong("TIEMPOPRESIONADO",	
						tiempoFinal.getTimeInMillis() -
						tiempoInicio.getTimeInMillis());
				bundle.putInt("X", x);
				bundle.putInt("Y", y);
				
				intent.putExtras(bundle);
				startActivity(intent);
				break;
		}
	return true;
	}
}