package igserrano.control.controlesextendidos;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public class ControlesExtendidosActivity extends Activity {
    /** Called when the activity is first created. */
	
	EditTextContador control_personalizado;
	TextView  textView_control;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        control_personalizado = (EditTextContador) findViewById(R.id.controlPersonalizado);
        textView_control = (TextView) findViewById(R.id.textView1);
        
        control_personalizado.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				textView_control.setText("onTextChanged");
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				textView_control.setText("beforeTextChanged");
			}
			
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				textView_control.setText("afterTextChanged");
			}
		});
    }
}