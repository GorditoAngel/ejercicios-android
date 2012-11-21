package com.especialcursos.tema12.procesos;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ProgressBar;

public class CalcularFactorialesTask extends
		AsyncTask<Integer, Long, Long> {

	private ProgressBar progreso;
	private EditText etResultado;
	
	public CalcularFactorialesTask(MainActivity ctxt){
		progreso = ctxt.getBarraProgreso();
		etResultado = ctxt.getETResultados();
	}
	
	public long factorial(int n){
		if (n == 0)
			return 1;
		else
			return n*factorial(n-1);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//se activa la barra de progreso
		progreso.setProgress(0);
	}
	
	@Override
	protected Long doInBackground(Integer ... arg0) {
		int numero_factoriales = arg0[0];
		for (int ii = 0; ii < numero_factoriales; ii++){
			synchronized (this) {
				try {
					wait(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			publishProgress((long) (((ii+1)/ (float) numero_factoriales)*100), factorial(ii + 1));
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Long... values) {
		long progr = values[0]; 
		long factorial = values[1];
		progreso.setProgress((int) progr);
		etResultado.append(Long.toString(factorial) + ", ");
	}

}
