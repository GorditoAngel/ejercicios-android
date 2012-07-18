package com.especialcursos.tema9;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }
    
    @Override
    /**
     * Oyente del menu
     */
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.sub1_radio1:
    	case R.id.sub1_radio2:
    	case R.id.sub1_radio3:
    	case R.id.sub1_radio4:
    		item.setChecked(true);
    		break;
    	case R.id.sub1_checkbox1:
    	case R.id.sub1_checkbox2:
    		if (item.isChecked())
    			item.setChecked(false);
    		else
    			item.setChecked(true);
    		break;
    	case R.id.sub2_item1:
    	case R.id.sub2_item2:
    	case R.id.sub2_item3:
    		goToListActivity(item);
    	default:
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    
    public void goToListActivity(MenuItem item){
    	//TODO enviar a la otra actividad
    	Toast.makeText(this, "funciona (amndar a listActivity", Toast.LENGTH_LONG).show();
    }

    
}
