package com.igserrano.bluetoothrccarcontroller;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PresentacionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_presentacion);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.presentacion, menu);
		return true;
	}
	
	public void esperar(int segundos){
		try{
			Thread.sleep(1000*segundos);
		}catch (Exception e){
			
		}
	}

}
