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
	TextView textView_dxdy2 = null;
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
		textView_dxdy2 = (TextView) findViewById(R.id.main_textView_dxdy2);
		
		textView2 = (TextView) findViewById(R.id.main_textView2);
		textView_velocidad2 = (TextView) findViewById(R.id.main_textView_velocidad2);
		
		res = getResources();
		lista_dedos = new ArrayList<Dedito>();
		
		//Como maximo 10 dedos
		for (int i=0; i < 10; i++){
			Dedito dedillo = new Dedito();
			lista_dedos.add(dedillo);
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
				>> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		final int action = (event.getAction() & MotionEvent.ACTION_MASK);
		int pointerId = event.getPointerId(pointerIndex);
		switch (action) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				lista_dedos.get(pointerId).setID(event.getPointerId(pointerId));
				lista_dedos.get(pointerId).setTiempoInicio(Calendar.getInstance());
				lista_dedos.get(pointerId).setX_mov((float)event.getX(pointerIndex));
				lista_dedos.get(pointerId).setY_mov((float)event.getY(pointerIndex));
				lista_dedos.get(pointerId).setX_i((int) lista_dedos.get(pointerId).getX_mov());
				lista_dedos.get(pointerId).setY_i((int) lista_dedos.get(pointerId).getY_mov());
				lista_dedos.get(pointerId).setInstante(Calendar.getInstance());
				break;
			case MotionEvent.ACTION_MOVE:{
				int pointerCount = event.getPointerCount();
				for (int i = 0; i < pointerCount; i++){
					pointerIndex = i;
					pointerId = event.getPointerId(pointerIndex);
					
					final float x = event.getX(pointerIndex);
					final float y = event.getY(pointerIndex);
					Calendar final_mov = Calendar.getInstance();
					//distancia movida
					final float dx = x - lista_dedos.get(pointerId).getX_mov();
					final float dy = y - lista_dedos.get(pointerId).getY_mov();
					long dt = (final_mov.getTimeInMillis() - 
							lista_dedos.get(pointerId).getInstante().getTimeInMillis());
					lista_dedos.get(pointerId).setInstante(Calendar.getInstance());
					lista_dedos.get(pointerId).setX_mov(x);
					lista_dedos.get(pointerId).setY_mov(y);
					//escribir movimientos
					escribirMovimiento(dx, dy, dt, pointerId);
				}
					
				break;
			}
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL:
				lista_dedos.get(pointerId).setTiempoFinal(Calendar.getInstance());
				lista_dedos.get(pointerId).setX_f((int)event.getX(pointerIndex));
				lista_dedos.get(pointerId).setY_f((int)event.getY(pointerIndex));
				//escribir resultados
				escribirResultado_dedo(lista_dedos.get(pointerId));
				escribirVelocidad(lista_dedos.get(pointerId));
				break;
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
		else if (dedito_pulsado.getID() == 1)
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
		else if (dedito_pulsado.getID() == 1)
			textView_velocidad2.setText("(\u0394x,\u0394y) = ("+X+","+Y+"). " + "\n" + res.getString(R.string.vel_med) 
					+ Float.toString(distancia/((t2-t1)/1000F))+ " px/s.");
		
	}
	private void escribirMovimiento(float dx, float dy, float dt, int idx){
		if (idx == 0)
		textView_dxdy.setText("(dx,dy) = ("+ dx+ ","+dy+"), \n" +
				"(Vx,Vy) = ("+ Float.toString(dx*1000F/dt)+ 
				","+ Float.toString(dy*1000F/dt)+")px/s. \n" +
				"dt = " + dt + "ms." );
		else if (idx == 1)
		textView_dxdy2.setText("(dx,dy) = ("+ dx+ ","+dy+"), \n" +
				"(Vx,Vy) = ("+ Float.toString(dx*1000F/dt)+ 
				","+ Float.toString(dy*1000F/dt)+")px/s. \n" +
				"dt = " + dt + "ms." );

	}
	
}