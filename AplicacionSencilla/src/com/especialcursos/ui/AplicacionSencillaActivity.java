package com.especialcursos.ui;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

@SuppressLint("FloatMath")
public class AplicacionSencillaActivity extends Activity {
	
	TextView textView = null;
	TextView textView_velocidad = null;
	TextView textView_dxdy = null;
	
	TextView textView2 = null;
	TextView textView_velocidad2 = null;
	
	private ArrayList<Dedito> lista_dedos = null;
	
	
	Resources res;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		textView = (TextView) findViewById(R.id.main_textView);
		textView_velocidad = (TextView) findViewById(R.id.main_textView_velocidad);
		textView_dxdy = (TextView) findViewById(R.id.main_textView_dxdy);
		
		textView2 = (TextView) findViewById(R.id.main_textView2);
		textView_velocidad2 = (TextView) findViewById(R.id.main_textView_velocidad2);
		
		res = getResources();
		lista_dedos = new ArrayList<Dedito>();
		
		//Como maximo 2 dedos
		for (int i=0; i < 2; i++){
			Dedito dedillo = new Dedito();
			lista_dedos.add(dedillo);
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int index = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
				>> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		final int action = event.getAction();
		if (index < 2){
			Dedito dedito_pulsado = lista_dedos.get(index);
			
			switch (action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					dedito_pulsado.setID(event.getPointerId(index));
					dedito_pulsado.setTiempoInicio(Calendar.getInstance());
					dedito_pulsado.setX_mov((float)event.getX());
					dedito_pulsado.setY_mov((float)event.getY());
					dedito_pulsado.setX_i((int) dedito_pulsado.getX_mov());
					dedito_pulsado.setY_i((int) dedito_pulsado.getY_mov());
					dedito_pulsado.setInstante(Calendar.getInstance());
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					dedito_pulsado.setID(event.getPointerId(index));
					dedito_pulsado.setTiempoInicio(Calendar.getInstance());
					dedito_pulsado.setX_mov((float)event.getX());
					dedito_pulsado.setY_mov((float)event.getY());
					dedito_pulsado.setX_i((int) dedito_pulsado.getX_mov());
					dedito_pulsado.setY_i((int) dedito_pulsado.getY_mov());
					dedito_pulsado.setInstante(Calendar.getInstance());
					break;
				case MotionEvent.ACTION_MOVE:{
					final float x = event.getX(index);
					final float y = event.getY(index);
					Calendar final_mov = Calendar.getInstance();
					//distancia movida
					final float dx = x - lista_dedos.get(index).getX_mov();
					final float dy = y - lista_dedos.get(index).getY_mov();
					long dt = (final_mov.getTimeInMillis() - 
							lista_dedos.get(index).getInstante().getTimeInMillis());
					lista_dedos.get(index).setInstante(Calendar.getInstance());
					lista_dedos.get(index).setX_mov(x);
					lista_dedos.get(index).setY_mov(y);
					//escribir movimientos
					escribirMovimiento(dx, dy, dt);
					break;
				}
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					dedito_pulsado.setTiempoFinal(Calendar.getInstance());
					dedito_pulsado.setX_f((int)event.getX());
					dedito_pulsado.setY_f((int)event.getY());
					//escribir resultados
					escribirResultado_dedo(dedito_pulsado);
					escribirVelocidad(dedito_pulsado);
					break;
			}
		}
	return true;
	}
	
	private void escribirResultado_dedo(Dedito dedito_pulsado){
		long t1 = (dedito_pulsado.getTiempoInicio() != null)?(dedito_pulsado.getTiempoInicio().getTimeInMillis()):0;
		long t2 = (dedito_pulsado.getTiempoFinal() != null)?(dedito_pulsado.getTiempoFinal().getTimeInMillis()):0;
		String diferencia = Long.toString(t2-t1);
		if (dedito_pulsado.getID() == 0)
			textView.setText(res.getString(R.string.has_pulsado) + diferencia + 
					"ms. "+ res.getString(R.string.desde) +" (x1,y1) = (" 
						+ dedito_pulsado.getX_i() + "," + dedito_pulsado.getY_i() +") " +
					"a (x2,y2) = ("+ dedito_pulsado.getX_f() + "," + dedito_pulsado.getY_f() +").") ;
		else
			textView2.setText(res.getString(R.string.has_pulsado) + diferencia + 
					"ms. "+ res.getString(R.string.desde) +" (x1,y1) = (" 
					+ dedito_pulsado.getX_i() + "," + dedito_pulsado.getY_i() +") " +
					"a (x2,y2) = ("+ dedito_pulsado.getX_f() + "," + dedito_pulsado.getY_f() +").") ;
	}
	private void escribirVelocidad(Dedito dedito_pulsado){
		long t1 = (dedito_pulsado.getTiempoInicio() != null)?(dedito_pulsado.getTiempoInicio().getTimeInMillis()):0;
		long t2 = (dedito_pulsado.getTiempoFinal() != null)?(dedito_pulsado.getTiempoFinal().getTimeInMillis()):0;
		float X = dedito_pulsado.getX_f() - dedito_pulsado.getX_i();
		float Y = dedito_pulsado.getY_f() - dedito_pulsado.getY_i();
		float distancia = (float) Math.sqrt(Math.pow(X,2)+Math.pow(X,2));
		if (dedito_pulsado.getID() == 0)
			textView_velocidad.setText("(\u0394x,\u0394y) = ("+X+","+Y+"). " + "\n" + res.getString(R.string.vel_med) 
					+ Float.toString(distancia/((t2-t1)/1000F))+ " px/s.");
		else
			textView_velocidad2.setText("(\u0394x,\u0394y) = ("+X+","+Y+"). " + "\n" + res.getString(R.string.vel_med) 
					+ Float.toString(distancia/((t2-t1)/1000F))+ " px/s.");
		
	}
	private void escribirMovimiento(float dx, float dy, float dt){
		textView_dxdy.setText("(dx,dy) = ("+ dx+ ","+dy+"), \n" +
				"(Vx,Vy) = ("+ Float.toString(dx*1000F/dt)+ 
				","+ Float.toString(dy*1000F/dt)+")px/s. \n" +
				"dt = " + dt + "ms." );

	}
	
}