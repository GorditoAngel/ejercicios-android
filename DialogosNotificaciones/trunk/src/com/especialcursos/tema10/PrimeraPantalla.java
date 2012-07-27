package com.especialcursos.tema10;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class PrimeraPantalla extends Activity {
	
	Button boton1 = null;
	Button boton2 = null;
	Button boton3 = null;
	Button boton4 = null;
	Button boton5 = null;
	
	static final int DIALOGO_PERSONAL = 1; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_pantalla);
        
        boton1 = (Button) findViewById(R.id.button1);
        boton2 = (Button) findViewById(R.id.button2);
        boton3 = (Button) findViewById(R.id.button3);
        boton4 = (Button) findViewById(R.id.button4);
        boton5 = (Button) findViewById(R.id.button5);
        
        boton1.setOnClickListener(new BotonListener());
        boton2.setOnClickListener(new BotonListener());
        boton3.setOnClickListener(new BotonListener());
        boton4.setOnClickListener(new BotonListener());
        boton5.setOnClickListener(new BotonListener());
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	// TODO Auto-generated method stub
    	return super.onCreateDialog(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_primera_pantalla, menu);
        return true;
    }

    
    private class BotonListener implements OnClickListener{

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1:
				//Toast personalizado
				mostrarToastPersonalizado();
				break;
			case R.id.button2:
				//Cuadro de dialogo personalizado
				mostrarCuadroDialogoPersonalizado();
				break;
			case R.id.button3:
			case R.id.button4:
			case R.id.button5:
				//Mostrar notificaciones en la barra de estado
				//Patron de vibracion medio segundo vibrando
				//medio segundo sin vibrar y medio segundo vibrando
				//Se navegará ala segunda pantalla de aplicación
				vibrar();
				navegarSegundaPantalla();
				break;
			default:
				break;
			}
		}
    	
    }
    
    private void mostrarToastPersonalizado(){
    	//con un icono a la izquierda y texto a la derecha.
    	LayoutInflater inflater = getLayoutInflater();
    	View layout = inflater.inflate(R.layout.toast_personal, 
    			(ViewGroup) findViewById(R.id.toast_personal_layout));
    	Toast toast = new Toast(getApplicationContext());
    	toast.setGravity(Gravity.CENTER, 0, -250);
    	toast.setDuration(Toast.LENGTH_LONG);
    	toast.setView(layout);
    	toast.show();
    }
    
    private void mostrarCuadroDialogoPersonalizado(){
    	//TODO cuadro de diálogo personalizado, con dos
    	//botones, un icono y un mensaje que pida confirmación para salir de la aplicación. Si se pulsa
    	//“Aceptar”, se saldrá de la aplicación, no sin antes mostrar otro toast personalizado con otro
    	//icono y otro mensaje de despedida. Si se pulsa el botón “Cancelar”, el diálogo se cerrará sin
    	//salir de la aplicación.
    	View layoutDialogo = getLayoutInflater().inflate(R.layout.dialogo_personal, 
    			(ViewGroup) findViewById(R.id.dialogo_personal_raiz));
    	AlertDialog dialogoPersonalizado;
    	AlertDialog.Builder constructor;
    	constructor = new AlertDialog.Builder(this);
    	constructor.setView(layoutDialogo);
    	
    	//Añadimos el boton aceptar
    	constructor.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// este listener cierra la aplicacion muestra un Toast
				cierraYToast();
			}
		});
    	//Añadimos en boton cancelar
    	constructor.setNegativeButton(R.string.cancelar,  new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//cierra el dialogo
				dialog.dismiss();
			}
		});
    	
    	dialogoPersonalizado = constructor.create();
    }
    
    private void vibrar(){
    	//TODO Las tres notificaciones generarán un patrón de vibración (medio segundo vibrando, medio
    	//segundo sin vibrar, medio segundo vibrando)
    }
    
    private void navegarSegundaPantalla(){
    	//TODO
    }
    
    private void cierraYToast(){
    	onStop();
    }
    
}
