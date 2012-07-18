package igserrano.control.controlesextendidos;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class ControlesExtendidosActivity extends Activity {
    /** Called when the activity is first created. */
	
	EditTextContador control_personalizado;
	TextView  textView_control;
	private int limite_texto = 250;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        control_personalizado = (EditTextContador) findViewById(R.id.controlPersonalizado);
        textView_control = (TextView) findViewById(R.id.textView1);
        
        control_personalizado.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int num_car = control_personalizado.length();
				if (num_car > limite_texto){
					textView_control.setTextColor(Color.RED);
					textView_control.setText(getString(R.string.sobrepasado_limite) + " (+" + (num_car -limite_texto)+ ")" +".");
				}else{
					textView_control.setText("");
				}
					
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			public void afterTextChanged(Editable s) {
				
			}
		});
    }
}