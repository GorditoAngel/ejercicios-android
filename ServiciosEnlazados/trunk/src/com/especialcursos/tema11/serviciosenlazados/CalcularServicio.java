package com.especialcursos.tema11.serviciosenlazados;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class CalcularServicio extends Service {
	
	//creamos el binder para enviar a lso clientes
	private final IBinder mMiBinder = new CalcularBinder();
	//Notificacion
	NotificationManager mNotificationManager;
	Notification mNotification;
	int mNotificationID;
	
    public CalcularServicio() {
    }
    
    @Override
    public void onCreate() {
    	mNotificationManager  = 
    			(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	super.onCreate();
    }

    public class CalcularBinder extends Binder{
    	public float getArea(float altura, float radio_base){
    		mNotificationID = crearNotificacion(getResources().getString(R.string.notificacion_calculando_area));
        	Toast.makeText(getApplicationContext(), R.string.notificacion_calculando_area, Toast.LENGTH_SHORT).show();
    		return (float) (altura*2*Math.PI*radio_base + 2*Math.PI*radio_base*radio_base);
    	}
    	public float getVolumen(float altura, float radio_base){
    		mNotificationID = crearNotificacion(getResources().getString(R.string.notificacion_calculando_volumen));
        	Toast.makeText(getApplicationContext(), R.string.notificacion_calculando_volumen, Toast.LENGTH_SHORT).show();
    		return (float) ((altura*Math.PI*radio_base*radio_base)*(0.001));
    	}
    	
    }
    
    @Override
    public IBinder onBind(Intent intent) {
    	mNotificationID = crearNotificacion(getResources().getString(R.string.string_on_bind));
    	Toast.makeText(this, R.string.string_on_bind, Toast.LENGTH_SHORT).show();
    	return mMiBinder;
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
