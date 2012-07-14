package com.especialcursos.ui;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

@SuppressLint("FloatMath")
public class AplicacionSencillaActivity extends Activity {
	
	TextView textView = null;
	TextView textView_velocidad = null;
	TextView textView_dxdy = null;
	
	private int x_i;
	private int y_i;
	
	private int x_f;
	private int y_f;
	
	private float x_mov;
	private float y_mov;
	private Calendar instante = null;
	
	Calendar tiempoInicio = null;
	Calendar tiempoFinal = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		textView = (TextView) findViewById(R.id.main_textView);
		textView.setText("No has pulsado todavia");
		textView_velocidad = (TextView) findViewById(R.id.main_textView_velocidad);
		textView_dxdy = (TextView) findViewById(R.id.main_textView_dxdy);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				tiempoInicio = Calendar.getInstance();
				x_mov = (float)event.getX();
				y_mov = (float)event.getY();
				x_i = (int) x_mov;
				y_i = (int) y_mov;
				instante = Calendar.getInstance();
				break;
			case MotionEvent.ACTION_MOVE:{
				final float x = event.getX();
				final float y = event.getY();
				Calendar final_mov = Calendar.getInstance();
				//distancia movida
				final float dx = x - x_mov;
				final float dy = y - y_mov;
				long dt = (final_mov.getTimeInMillis() - 
						instante.getTimeInMillis());
				
				instante = Calendar.getInstance();
				x_mov = x;
				y_mov = y;
				
				//Escribir resultado
				textView_dxdy.setText("(dx,dy) = ("+ dx+ ","+dy+"), " +
						"(Vx,Vy) = ("+ Float.toString(dx*1000F/dt)+ 
						","+ Float.toString(dy*1000F/dt)+"px/s). \n" +
						"dt = " + dt + "ms." );
				
				
				break;
			}
			case MotionEvent.ACTION_UP:
				tiempoFinal = Calendar.getInstance();
				x_f = (int)event.getX();
				y_f = (int)event.getY();
				escribirResultado(x_i,y_i,x_f,y_f);
				
//				Intent intent = new Intent (
//						AplicacionSencillaActivity.this,
//						AplicacionSencillaResults.class);
//				Bundle bundle = new Bundle();
//				bundle.putLong("TIEMPOPRESIONADO",	
//						tiempoFinal.getTimeInMillis() -
//						tiempoInicio.getTimeInMillis());
//				bundle.putInt("X", x);
//				bundle.putInt("Y", y);
//				
//				intent.putExtras(bundle);
//				startActivity(intent);
				break;
		}
	return true;
	}
	
	private void escribirResultado(int xi, int yi, int xf,int yf){
		String s_x = Integer.toString(xi);
		String s_y = Integer.toString(yi);
		String s_xf = Integer.toString(xf);
		String s_yf = Integer.toString(yf);
		String Ax = Integer.toString(xf-xi);
		String Ay = Integer.toString(yf-yi);
		
		long t1 = (tiempoInicio != null)?(tiempoInicio.getTimeInMillis()):0;
		long t2 = (tiempoFinal != null)?(tiempoFinal.getTimeInMillis()):0;
		String diferencia = Long.toString(t2-t1);
		
		float distancia = (float) Math.sqrt(Math.pow(xf-xi,2)+Math.pow(yf-yi,2));
		
		textView.setText("Has pulsado la pantalla durante " 
				+ diferencia + " ms. Desde las coordenadas (x1,y1) = (" + 
				s_x + "," + s_y +") a las coordenadas (x2,y2) = (" +
				s_xf + "," + s_yf +"). (\u0394x,\u0394y) = ("+Ax+","+Ay+").");
		textView_velocidad.setText("vel. media = " + 
				Float.toString(distancia/((t2-t1)/1000F))+ " px/s.");
		
	}
}