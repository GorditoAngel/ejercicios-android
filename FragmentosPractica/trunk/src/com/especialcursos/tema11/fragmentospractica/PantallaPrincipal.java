package com.especialcursos.tema11.fragmentospractica;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class PantallaPrincipal extends Activity 
								implements ListaFragment.OnElementoSelectedListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pantalla_principal, menu);
        return true;
    }

	public void onElementoSelected(long idCancion, String nombre) {
		DetailFragment fragmento = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
		fragmento.rellenarTextView(new String(Long.toString(idCancion) + ", " + nombre));
	}
    
    
}
