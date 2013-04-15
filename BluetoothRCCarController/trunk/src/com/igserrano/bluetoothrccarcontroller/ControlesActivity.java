package com.igserrano.bluetoothrccarcontroller;

import com.MobileAnarchy.Android.Widgets.Joystick.DualJoystickView;
import com.MobileAnarchy.Android.Widgets.Joystick.JoystickMovedListener;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


// codigo del jostick cogido de http://code.google.com/p/mobile-anarchy-widgets/source/checkout

public class ControlesActivity extends Activity {

	private  int REQUEST_ENABLE_BT = 1;
	TextView txtX1, txtY1;
	TextView txtX2, txtY2;
	DualJoystickView joystick;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controles);

		txtX1 = (TextView)findViewById(R.id.TextViewX1);
        txtY1 = (TextView)findViewById(R.id.TextViewY1);
        
		txtX2 = (TextView)findViewById(R.id.TextViewX2);
        txtY2 = (TextView)findViewById(R.id.TextViewY2);

        joystick = (DualJoystickView)findViewById(R.id.dualjoystickView);
        
        joystick.setOnJostickMovedListener(_listenerLeft, _listenerRight);
        
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
        	 Toast.makeText(getApplicationContext(), getString(R.string.no_soporta_bluetooth), Toast.LENGTH_LONG).show();
        } else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Recogemos la información enviada por BluetoothAdapter
		if (requestCode == REQUEST_ENABLE_BT){
			if (resultCode == RESULT_OK){
				Toast.makeText(getApplicationContext(), getString(R.string.bluetooth_encendido), Toast.LENGTH_LONG).show();
			}
			if (resultCode == RESULT_CANCELED){
				Toast.makeText(getApplicationContext(), getString(R.string.bluetooth_cancelado), Toast.LENGTH_LONG).show();
				finish(); //nos salimos de la aplicación
			}
		}
	}
	
    private JoystickMovedListener _listenerLeft = new JoystickMovedListener() {

		@Override
		public void OnMoved(int pan, int tilt) {
			txtX1.setText(Integer.toString(pan));
			txtY1.setText(Integer.toString(tilt));
		}

		@Override
		public void OnReleased() {
			txtX1.setText("released");
			txtY1.setText("released");
		}
		
		public void OnReturnedToCenter() {
			txtX1.setText("stopped");
			txtY1.setText("stopped");
		};
	}; 

    private JoystickMovedListener _listenerRight = new JoystickMovedListener() {

		@Override
		public void OnMoved(int pan, int tilt) {
			txtX2.setText(Integer.toString(pan));
			txtY2.setText(Integer.toString(tilt));
		}

		@Override
		public void OnReleased() {
			txtX2.setText("released");
			txtY2.setText("released");
		}
		
		public void OnReturnedToCenter() {
			txtX2.setText("stopped");
			txtY2.setText("stopped");
		};
	}; 

}

