package com.especialcursos.tema11.serviciosenlazadosipc;



import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PrincipalActivity extends Activity {

	private Messenger mServicio = null;
	boolean mServicioUnido = false;
	
	private EditText etAltura;
	private EditText etBase;
	private EditText etVolumen;
	private EditText etArea;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        
        etAltura = (EditText) findViewById(R.id.et_altura);
        etBase = (EditText) findViewById(R.id.et_base);
        etVolumen = (EditText) findViewById(R.id.et_volumen);
        etArea = (EditText) findViewById(R.id.et_area);
        
        //bloqueamos las casillas de altura y volumen para que
        //no se puedan editar
        etVolumen.setKeyListener(null);
        etArea.setKeyListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_principal, menu);
        return true;
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	conectarServicio();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	//Desconexion del servicio
    	desconectarServicio();
    }
    public void calcular(View v){
    	if (mServicioUnido && mServicio != null && camposValidos()){
    		try {
				Message msg = Message.obtain(null,CalcularServicio.MSG_GENERAR_AREA,
						(int) Float.parseFloat(etAltura.getText().toString()),
						(int) Float.parseFloat(etBase.getText().toString()));
				msg.replyTo = mMessenger;
				mServicio.send(msg);
				
				Message msg2 = Message.obtain(null,CalcularServicio.MSG_GENERAR_VOLUMEN,
						(int) Float.parseFloat(etAltura.getText().toString()),
						(int) Float.parseFloat(etBase.getText().toString()));
				msg2.replyTo = mMessenger;
				mServicio.send(msg2);
			} catch (RemoteException e) {
				toastErrorConexion();
			}
    	}
    }
    
    public boolean camposValidos(){
    	boolean validos = false;
    	if (etAltura.getText().toString().length() > 0 && etBase.getText().toString().length() > 0){
    		try {
				float altura = Float.parseFloat(etAltura.getText().toString());
				float radio_base = Float.parseFloat(etBase.getText().toString());
				if (!Float.isNaN(altura) || !Float.isNaN(radio_base)){
					validos = true;
				}
			} catch (NumberFormatException e) {
				validos = false;
				e.printStackTrace();
			}
    	}
    	return validos;
    }
    
    /**
     * Metodo invocado por el manejador cuando recibe el volumen
     * @param f
     */
    public void recibirVolumen(int f){
    	if (!Float.isNaN(f)){
    		etVolumen.setText(Integer.toString(f));
    	}else{
    		try {
				Message msg = Message.obtain(null,CalcularServicio.MSG_NOTIFICAR_VOLUMEN_MAL);
				msg.replyTo = mMessenger;
				mServicio.send(msg);
			} catch (RemoteException e) {
				toastErrorConexion();
			}
    	}
    }
    
    /**
     * Metodo invocado por el manejador cuando recibe el area
     * @param f
     */
    public void recibirArea(int f){
    	if (!Float.isNaN(f)){
    		etArea.setText(Integer.toString(f));
    	}else{
    		try {
				Message msg = Message.obtain(null,CalcularServicio.MSG_NOTIFICAR_AREA_MAL);
				msg.replyTo = mMessenger;
				mServicio.send(msg);
			} catch (RemoteException e) {
				toastErrorConexion();
			}
    	}
    }
    
    private ServiceConnection mConexion = new ServiceConnection() {
		
		public void onServiceDisconnected(ComponentName name) {
			mServicio = null;
			mServicioUnido = false;
		}
		
		public void onServiceConnected(ComponentName name, IBinder service) {
			mServicio = new Messenger(service);
			mServicioUnido = true;
			
			try{
				Message msg = Message.obtain(null,CalcularServicio.MSG_CONECTAR_CLIENTE);
				//conctamos a traves del Mensajero
				msg.replyTo = mMessenger;
				mServicio.send(msg);
			}catch (RemoteException e){
				toastErrorConexion();
			}
		}
	};
	
	/**
	 * Va recibir todos los mensajes que vienen del servicio
	 * y los clasifica con un switch y una id que le hemos asignado 
	 * cada caso.
	 * @author ismae_000
	 *
	 */
	public class HandlerCliente extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case CalcularServicio.MSG_RECIBIR_VOLUMEN:
					recibirVolumen(msg.arg1);
					break;
				case CalcularServicio.MSG_RECIBIR_AREA:
					recibirArea(msg.arg1);
					break;
			}
		}
	}
	
	/**
	 * Mensajero que se conecta con el servicio y manda
	 * los mensajes a HandlerCliente que los interpreta
	 */
	final Messenger mMessenger = new Messenger(new HandlerCliente());
	
	//CONEXION Y DESCONEXION DEL SERVICIO
	public void conectarServicio(){
		Intent intent = new Intent(this,CalcularServicio.class);
		bindService(intent, mConexion, Context.BIND_AUTO_CREATE);
		mServicioUnido = true;
	}
	public void desconectarServicio(){
		if(mServicioUnido){
			unbindService(mConexion);
			mServicioUnido = false;
		}
	}
	
	public void toastErrorConexion(){
		Toast.makeText(getApplicationContext(), R.string.string_error_msg_servicio, Toast.LENGTH_SHORT).show();
	}
	
}
