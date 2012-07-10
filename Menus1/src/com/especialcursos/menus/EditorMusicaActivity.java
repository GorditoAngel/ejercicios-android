package com.especialcursos.menus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class EditorMusicaActivity extends Activity {
	
	static int CD_VINILO = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_musica, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Se gestiona la opción seleccionada
        switch (item.getItemId()) {
        case R.id.buscar:
            buscar();
            return true;
        case R.id.escuchar:
            escuchar();
            return true;
        case R.id.grabar:
            grabar();
            return true;
        case R.id.cd_vinilo:
        	cd_vinilo();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    // Método invocado inmediatamente antes de mostrarse el menú, cada vez. 
    // Recibe como parámetro el Menu, con la estructura tal y como esté justo antes de ser mostrado.
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	    	
    	if (CD_VINILO == 1) {
    		menu.findItem(R.id.cd_vinilo).setIcon(R.drawable.cd);
    		menu.findItem(R.id.cd_vinilo).setTitle(R.string.cd);
    	} 
    	else {
    		menu.findItem(R.id.cd_vinilo).setIcon(R.drawable.vinilo);
    		menu.findItem(R.id.cd_vinilo).setTitle(R.string.vinilo);
    	}
    	
    	// return false: no muestra menú
    	// return true: se muestra menú. Buena práctica: delegar en clase padre.
    	return super.onPrepareOptionsMenu(menu);
    }
            
    private void cd_vinilo() {
    	if (CD_VINILO == 0)
    		CD_VINILO = 1;
    	else
    		CD_VINILO = 0;
    }    
    
    private void escuchar() {

    }

    private void buscar() {
    	// TODO
    }
    
    private void grabar() {
    	// TODO
    }
}