package com.especialcursos.tema9;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ListaCosas extends ListActivity {

	
	static boolean estado_chk1 = false;
	static boolean estado_chk2 = false;
	static boolean estado_chk3 = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] resultado_lista = 
        		getResources().getStringArray(R.array.musica_array);
        setListAdapter(new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1,resultado_lista));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        registerForContextMenu(getListView());
    }

    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	getMenuInflater().inflate(R.menu.contextual_lista_cosas, menu);
    	
    	menu.findItem(R.id.ctx_chk_item1).setChecked(estado_chk1);
    	menu.findItem(R.id.ctx_chk_item2).setChecked(estado_chk2);
    	menu.findItem(R.id.ctx_chk_item3).setChecked(estado_chk3);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	switch (item.getItemId()){
    	case R.id.contextual_item2:
    	case R.id.contextual_item3:
    	case R.id.contextual_item4:
    		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
    		return true;
    	case R.id.ctx_chk_item1:
    		if (item.isChecked()){
    			item.setChecked(false);
    			estado_chk1 = false;
    		}else{
    			item.setChecked(true);
    			estado_chk1 = true;
    		}
    		return true;
    	case R.id.ctx_chk_item2:
    		if (item.isChecked()){
    			item.setChecked(false);
    			estado_chk2 = false;
    		}else{
    			item.setChecked(true);
    			estado_chk2 = true;
    		}
    		return true;
    	case R.id.ctx_chk_item3:
    		if (item.isChecked()){
    			item.setChecked(false);
    			estado_chk2 = false;
    		}else{
    			item.setChecked(true);
    			estado_chk2 = true;
    		}
    		return true;
    	default:
    		return super.onContextItemSelected(item);
    	}
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lista_cosas, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
