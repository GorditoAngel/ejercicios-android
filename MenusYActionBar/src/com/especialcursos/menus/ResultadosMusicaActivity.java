package com.especialcursos.menus;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// Esta actividad no extiende de Activity, si no de una subclase suya,
// ListActivity, que crea autom�ticamente una lista de items a pantalla completa
public class ResultadosMusicaActivity extends ListActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// La lista de items se cargan en un array. Los elementos a cargar est�n en el archivo strings.xml
		String[] resultadosMusica = getResources().getStringArray(R.array.musica_array);
		// Cada elemento de la lista ser� un TextView, definido en el layout resultado_musica.xml 
		setListAdapter(new ArrayAdapter<String>(this, R.layout.resultado_musica, resultadosMusica));
		// NOTA: se pueden utilizar layouts predefinidos como "android.R.layout.simple_list_item_1"
		//setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultadosMusica));
		
		// Se obtiene la vista tipo lista
		ListView lv = getListView();
		// Se activa su el flitrado de texto en la lista. 
		// Cuando el usuario comience a escribir, la lista se filtrar� autom�ticamente 
		lv.setTextFilterEnabled(true);

		// Se mostrar� un peque�o mensaje flotante al usuario (Toast) cuando se haga click en un �tem
		// Este m�todo define el listener tipo onClick para cada elemento de la lista.
		// Cuando se pulse un elemento de la lista, se invocar� al m�todo onItemClick y se mostrar� la Toast
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Cuando se haga click, se mostrar� un popup (toast o "tostada") con el texto del TextView
				Toast.makeText(getApplicationContext(),	((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});	
		
		// Con este m�todo se asigna el men� contextual al ListView
		registerForContextMenu(lv);
	}
	
	// M�todo que crear� el men� contextual para el ListView
	// El men� contextual est� definido en menu_lista_musica.xml
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_lista_musica, menu);
	}
	
	// Acciones que se realizar�n al seleccionar un �tem del men� contextual
	// Por el momento, ninguna
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		// El menuItem recibido es sobre el que se ha mantenido la pulsaci�n 
		// para mostrar el men� contextual. 
		// "info" contiene el identificador del menuItem seleccionado, sobre el que se 
		// realizar� la acci�n del men� contextual.
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	  
		switch (item.getItemId()) {	  
		case R.id.escuchar:
			//escuchar(info.id);
			return true;
		case R.id.enviar:
			//enviar(info.id);
			return true;
		case R.id.borrar:
			//borrar(info.id);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
}
