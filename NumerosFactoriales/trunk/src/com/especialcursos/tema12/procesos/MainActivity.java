package com.especialcursos.tema12.procesos;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ProgressBar mProgress;
	private EditText etNumero;
	private EditText etResultado;
	final static int limite_inferior = 4;
	final static int limite_superior = 500; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        etNumero = (EditText) findViewById(R.id.et_numero_factoriales);
        etResultado = (EditText) findViewById(R.id.et_resultado);

        etResultado.setKeyListener(null);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    public void calcularFactoriales(View v){
    	//ñapa para quitar la mierda del keyboard
    	if (etNumero.isFocused()) etResultado.requestFocus(); 	
    	InputMethodManager imm = 
    			(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(etResultado.getWindowToken(), 0);
    	
    	//nos aseguramos de los valores d eentrada antes de ejecutar
    	if (etNumero.getText().toString().length() > 0){
    		if (etResultado.getText().toString().length() > 0)
    			etResultado.getText().clear();
    		try {
    			int num = Integer.parseInt(etNumero.getText().toString());
    			if (num < 0) throw new NumberFormatException();
    			
    			if (num < limite_inferior)
    				new CalcularFactorialesTask(this).execute(limite_inferior);
    			else if (num > limite_superior)
    				Toast.makeText(this, R.string.evitar_que_pete, Toast.LENGTH_SHORT).show();
    			else
    				new CalcularFactorialesTask(this).execute(num);
    			
			} catch (NumberFormatException e) {
				Toast.makeText(this, R.string.error_numero, Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
    	}
    }
    
    public ProgressBar getBarraProgreso(){
    	return mProgress;
    }
    
    public EditText getETResultados(){
    	return etResultado;
    }
}
