package com.especialcursos.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ControlesBasicosActivity extends Activity {
    /** Called when the activity is first created. */

    
    private RadioGroup group;
    private CheckBox checkNegrita;
    private CheckBox checkCursiva;
    private ToggleButton toggle;
    private TextView texto;
    private Button botoncillo;
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        group = (RadioGroup)findViewById(R.id.grupoRadio);
        checkNegrita = (CheckBox)findViewById(R.id.checkNegrita);
        checkCursiva = (CheckBox)findViewById(R.id.checkCursiva);
        toggle = (ToggleButton)findViewById(R.id.toggle);
        texto = (TextView)findViewById(R.id.texto);
        botoncillo = (Button)findViewById(R.id.Boton);
        
        
        RadioGroup.OnCheckedChangeListener eventoGrupo = new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				RadioButton radio = (RadioButton)findViewById(checkedId);
				texto.setText(getResources().getString(R.string.main_texto_resutado) + radio.getText());
//				texto.setBackgroundColor(2371125);
			}
		};
		
		CompoundButton.OnCheckedChangeListener eventoCheck = new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				cambiarTexto();
			}
		};
		
		View.OnClickListener eventoToggle = new View.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cambiarTexto();
			}
			
		};
		
		
		
//		View.OnLongClickListener eventoBoton = new View.OnLongClickListener() {
//			
//			@Override
//			public boolean onLongClick(View v) {
//				texto.setBackgroundColor(Color.MAGENTA);
//				return false;
//			}
//		};
		
		View.OnTouchListener eventotouch = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()){
				case MotionEvent.ACTION_DOWN:
					texto.setBackgroundColor(Color.MAGENTA);
					break;
				case MotionEvent.ACTION_MOVE:
					texto.setBackgroundColor(Color.MAGENTA);
					break;
				case MotionEvent.ACTION_UP:
					texto.setBackgroundColor(2371125);
					break;
				default:
					texto.setBackgroundColor(2371125);
					break;
				}
				return true;
			}
		};
		
		
		
		
		checkCursiva.setOnCheckedChangeListener(eventoCheck);
		checkNegrita.setOnCheckedChangeListener(eventoCheck);
		group.setOnCheckedChangeListener(eventoGrupo);
		toggle.setOnClickListener(eventoToggle);
//		botoncillo.setOnLongClickListener(eventoBoton);
		botoncillo.setOnTouchListener(eventotouch);
    }
    
    public void cambiarTexto(){
    	if (toggle.isChecked()){
			if (checkCursiva.isChecked()){
				if(checkNegrita.isChecked())
					texto.setTypeface(null, Typeface.BOLD_ITALIC);
				else
					texto.setTypeface(null, Typeface.ITALIC);
			}else{
				if(checkNegrita.isChecked())
					texto.setTypeface(null, Typeface.BOLD);
				else
					texto.setTypeface(null, Typeface.NORMAL);
			}
    	}else
    		texto.setTypeface(null, Typeface.NORMAL);
	}
    
    
}