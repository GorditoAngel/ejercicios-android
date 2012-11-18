package com.especialcursos.tema11.serviciosenlazadosipc;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class CalcularServicio extends Service {
	
	//Comandos para comunicarse con la actividad cliente
	public static final int MSG_CONECTAR_CLIENTE = 1;
	public static final int MSG_NOTIFICAR_VOLUMEN_MAL = 2;
	public static final int MSG_NOTIFICAR_AREA_MAL = 3;
	public static final int MSG_GENERAR_VOLUMEN = 4;
	public static final int MSG_GENERAR_AREA = 5;

	
	public static final int MSG_RECIBIR_VOLUMEN = 6;
	public static final int MSG_RECIBIR_AREA = 7;
	
	//Messenger del cliente recibido
	private Messenger mMessengerCliente;
	
	//Notificacion
	NotificationManager mNotificationManager;
	Notification mNotification;
	int mNotificationID;
	
    public CalcularServicio() {
    }
    
    /**
     * Handler que gestiona los mensajes de los clientes
     * @author ismae_000
     *
     */
    class MiHandler extends Handler{
    	@Override
    	public void handleMessage(Message msg) {
    		switch(msg.what){
    			case MSG_CONECTAR_CLIENTE:
    				mMessengerCliente = msg.replyTo;
    				crearNotificacion(getResources().getString(R.string.string_on_bind));
    				break;
    			case MSG_GENERAR_AREA:
    				enviarAreaACliente(msg.arg1, msg.arg2);
    				break;
    			case MSG_GENERAR_VOLUMEN:
    				enviarVolumenACliente(msg.arg1, msg.arg2);
    				break;
    			case MSG_NOTIFICAR_AREA_MAL:
    				crearNotificacion(getResources().getString(R.string.string_error_msg_area_mal));
    				break;
    			case MSG_NOTIFICAR_VOLUMEN_MAL:
    				crearNotificacion(getResources().getString(R.string.string_error_msg_volumen_mal));
    				break;
    			default:
    				super.handleMessage(msg);	
    		}
    		
    	}
    }
    /**
     * Messenger que se envia a los clientes
     */
    final Messenger mMessenger = new Messenger(new MiHandler());
    
    @Override
    public void onCreate() {
    	mNotificationManager  = 
    			(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	super.onCreate();
    }
    
    private void enviarVolumenACliente(float altura, float radio_base){
    	mNotificationID = crearNotificacion(getResources().getString(R.string.notificacion_calculando_volumen));
    	Toast.makeText(getApplicationContext(), R.string.notificacion_calculando_volumen, Toast.LENGTH_SHORT).show();
    	float volumen = (float) ((altura*Math.PI*radio_base*radio_base));
    	try {
			mMessengerCliente.send(Message.obtain(null, MSG_RECIBIR_VOLUMEN, (int) volumen, 0));
		} catch (RemoteException e) {
			mNotificationID = crearNotificacion(getResources().getString(R.string.string_error_msg_calc_volumen));
			e.printStackTrace();
		}
    }
    
    private void enviarAreaACliente(float altura, float radio_base){
    	mNotificationID = crearNotificacion(getResources().getString(R.string.notificacion_calculando_area));
    	Toast.makeText(getApplicationContext(), R.string.notificacion_calculando_area, Toast.LENGTH_SHORT).show();
    	float area = (float) (altura*2*Math.PI*radio_base + 2*Math.PI*radio_base*radio_base);
    	try {
			mMessengerCliente.send(Message.obtain(null, MSG_RECIBIR_AREA, (int) area, 0));
		} catch (RemoteException e) {
			mNotificationID = crearNotificacion(getResources().getString(R.string.string_error_msg_calc_area));
			e.printStackTrace();
		}
    }
    
    @Override
    public IBinder onBind(Intent intent) {
    	Toast.makeText(this, R.string.string_on_bind, Toast.LENGTH_SHORT).show();
    	return mMessenger.getBinder();
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
    	mNotificationID = crearNotificacion(getResources().getString(R.string.string_on_unbind));
    	Toast.makeText(this, R.string.string_on_unbind, Toast.LENGTH_SHORT).show();
    	return super.onUnbind(intent);
    }
    
    @Override
    public void onDestroy() {
    	mNotificationID = crearNotificacion(getResources().getString(R.string.string_servicio_destruido));
    	Toast.makeText(this, R.string.string_servicio_destruido, Toast.LENGTH_SHORT).show();
    	super.onDestroy();
    }
    
    private int crearNotificacion(String str) {
		//Solo una notificacion
		int notificationID = 97197;
		
		NotificationCompat.Builder constructor = new NotificationCompat.Builder(getApplicationContext());
		constructor.setTicker(str);
		constructor.setSmallIcon(R.drawable.ic_launcher);
		
		CharSequence contentTitle = str;
    	CharSequence contentText = str;
    	Intent notificationIntent = new Intent(this, PrincipalActivity.class);
    	notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	PendingIntent contentIntent = 
    			PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    	//El flag vale para que no abra otra actividad si ya hay una.
    	constructor.setContentText(contentText);
    	constructor.setContentTitle(contentTitle);
    	constructor.setContentIntent(contentIntent);
    	//Se cancela cuando pinchamos en ella
    	constructor.setAutoCancel(true);
    	
    	mNotification = constructor.getNotification();
    	
    	
    	mNotificationManager.notify(notificationID, mNotification);

		return notificationID;
	}
}
