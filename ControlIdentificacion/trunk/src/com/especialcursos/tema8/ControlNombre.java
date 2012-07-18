package com.especialcursos.tema8;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ControlNombre extends LinearLayout{

	private EditText et_nombre;
	private EditText et_apellidos;
	private EditText er_email;
	private Button bt_mostrar;
	private Button bt_borrar;
	private TextView texto_concatenado;
	
	public ControlNombre(Context context) {
		super(context);
		inicializar();
	}
	public ControlNombre(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		inicializar();
	}
	public ControlNombre(Context context, AttributeSet attrs) {
		super(context, attrs);
		inicializar();
	}
	
	public void inicializar(){
		
		LayoutInflater inflater = 
				(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.control_identificacion, this, true);
		
		et_nombre = (EditText) findViewById(R.id.et_nombre);
		et_apellidos = (EditText) findViewById(R.id.ed_apellidos);
		er_email = (EditText) findViewById(R.id.ed_email);
		bt_borrar = (Button) findViewById(R.id.b_borrar);
		bt_mostrar = (Button) findViewById(R.id.b_mostrar);
		texto_concatenado = (TextView) findViewById(R.id.tv_todo_concatenado);
		
		//Asignamos oyentes
		bt_mostrar.setOnClickListener(new BotonListener());
		bt_borrar.setOnClickListener(new BotonListener());
	}
	
	private class BotonListener implements OnClickListener{
		public void onClick(View v) {
			if (v.getId() == bt_mostrar.getId()){
				escribirTexto();
			}else if(v.getId() == bt_borrar.getId()){
				borrarTexto();
			}
		}
	}
	
	private void escribirTexto(){
		if (et_nombre.length()!= 0 && et_apellidos.length()!= 0 && er_email.length()!= 0){
			texto_concatenado.setText(getNombre() + " " + getApellidos() + ".\n" + getEmail());
		}
	}
	private void borrarTexto(){
		texto_concatenado.setText("");
	}
	
	public String getNombre(){
		return et_nombre.getText().toString();
	}
	public String getApellidos(){
		return et_apellidos.getText().toString();
	}
	public String getEmail(){
		return er_email.getText().toString();
	}
	
}
