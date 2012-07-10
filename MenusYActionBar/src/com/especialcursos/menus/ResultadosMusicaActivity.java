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
// ListActivity, que crea automáticamente una lista de items a pantalla completa
public class ResultadosMusicaActivity extends ListActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// La lista de items se cargan en un array. Los elementos a cargar están en el archivo strings.xml
		String[] resultadosMusica = getResources().getStringArray(R.array.musica_array);
		// Cada elemento de la lista será un TextView, definido en el layout resultado_musica.xml 
		setListAdapter(new ArrayAdapter<String>(this, R.layout.resultado_musica, resultadosMusica));
		// NOTA: se pueden utilizar layouts predefinidos como "android.R.layout.simple_list_item_1"
		//setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultadosMusica));
		
		// Se obtiene la vista tipo lista
		ListView lv = getListView();
		// Se activa su el flitrado de texto en la lista. 
		// Cuando el usuario comience a escribir, la lista se filtrará automáticamente 
		lv.setTextFilterEnabled(true);

		// Se mostrará un pequeño mensaje flotante al usuario (Toast) cuando se haga click en un ítem
		// Este método define el listener tipo onClick para cada elemento de la lista.
		// Cuando se pulse un elemento de la lista, se invocará al método onItemClick y se mostrará la Toast
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Cuando se haga click, se mostrará un popup (toast o "tostada") con el texto del TextView
				Toast.makeText(getApplicationContext(),	((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});	
		
		// Con este método se asigna el menú contextual al ListView
		registerForContextMenu(lv);
	}
	
	// Método que creará el menú contextual para el ListView
	// El menú contextual está definido en menu_lista_musica.xml
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_lista_musica, menu);
	}
	
	// Acciones que se realizarán al seleccionar un ítem del menú contextual
	// Por el momento, ninguna
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		// El menuItem recibido es sobre el que se ha mantenido la pulsación 
		// para mostrar el menú contextual. 
		// "info" contiene el identificador del menuItem seleccionado, sobre el que se 
		// realizará la acción del menú contextual.
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
