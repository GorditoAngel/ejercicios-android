package com.especialcursos.tema12.procesos;

import java.math.BigDecimal;
import java.math.BigInteger;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ProgressBar;

public class CalcularFactorialesTask extends
		AsyncTask<Integer, BigInteger, Long> {

	private ProgressBar progreso;
	private EditText etResultado;
	
	public CalcularFactorialesTask(MainActivity ctxt){
		progreso = ctxt.getBarraProgreso();
		etResultado = ctxt.getETResultados();
	}
	
	public double factorial(int n){
		if (n == 0)
			return 1;
		else
			return n*factorial(n-1);
	}
	
	//este aguanta mas numeros
	//porque necesita menos memoria
	public BigInteger factorialNorecursivo(int n){
		if (n == 0){
			return new BigInteger("1");
		}else{
			BigInteger resultado =  new BigInteger("1");
			for (int i = 1; i < (n+1) ; i++){
				resultado = resultado.multiply(new BigInteger(Integer.toString(i)));
			}
			return resultado;
		}
		
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
		BigInteger indice;
		for (int ii = 0; ii < numero_factoriales; ii++){
			synchronized (this) {
				try {
					wait(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			indice = new BigInteger(Integer.toString((ii +1)));
			publishProgress(new BigInteger(Integer.toString( (int) (((ii+1)/ (double) numero_factoriales)*100))), factorialNorecursivo(ii + 1), indice);
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(BigInteger... values) {
		BigInteger progr = values[0]; 
		BigInteger factorial = values[1];
		BigInteger indice = values[2];
		progreso.setProgress((int) progr.longValue());
		etResultado.append(indice.toString() +":  " + factorial.toString() + "\n");
	}

}
