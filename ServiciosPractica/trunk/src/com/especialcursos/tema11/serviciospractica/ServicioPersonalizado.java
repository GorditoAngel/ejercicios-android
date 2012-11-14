package com.especialcursos.tema11.serviciospractica;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ServicioPersonalizado extends IntentService {
	
	NotificationManager mNotificationManager;
	Notification mNotification;
	int mNotificationID;
	private String[] _lista;
	private int _posicion;
	
    public ServicioPersonalizado() {
		super("ServicioPersonalizado");
	}

    @Override
    public void onCreate() {
    	_lista = getResources().getStringArray(R.array.array_planetas);
    	_posicion = 0;
    	mNotificationManager  = 
    			(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	super.onCreate();
    }
    
    
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, R.string.servicio_iniciando, Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		//Este metodo mira si hay algo en el intent
		
		String str = intent.getStringExtra("TEXTO");
		if (str != null){
			//Si hay string
			//metemos el string como titulo en la notificacion
			mNotificationID = crearNotificacion(str);
		}else{
			//Si no hay String metemos el siguiente string de la lista
			//en la notificacion y sumamos 1 a posicion
			mNotificationID = 
					crearNotificacion(_lista[intent.getIntExtra("POSICION", 0)]);
		}
	}
	
	
	@Override
	public void onDestroy() {
		//destroyNotification(mNotificationID);
		Toast.makeText(this, R.string.servicio_destruyendose, Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}
	
	private int crearNotificacion(String str) {
		//Solo una notificacion
		int notificationID = 97198;
		
		Notification.Builder constructor = new Notification.Builder(getApplicationContext());
		constructor.setTicker(str);
		constructor.setSmallIcon(R.drawable.ic_launcher);
		
		CharSequence contentTitle = str;
    	CharSequence contentText = str;
    	Intent notificationIntent = new Intent(getApplicationContext(), PrincipalActivity.class);
    	PendingIntent contentIntent = 
    			PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
  
    	constructor.setContentText(contentText);
    	constructor.setContentTitle(contentTitle);
    	constructor.setContentIntent(contentIntent);
    	//Se cancela cuando pinchamos en ella
    	constructor.setAutoCancel(true);
    	
    	mNotification = constructor.getNotification();
    	
    	
    	mNotificationManager.notify(notificationID, mNotification);

		return notificationID;
	}
	
	private void destroyNotification(int notificationID) {
		//se para un segundito el hilo antes de destruir
		try {
			// Se ha de utilizar synchronized para que el sistema bloquee el servicio hasta que finalice el wait()
			// Si no, dará una IllegalMonitorStateException
			synchronized (this) {
				wait(10000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//mNotificationManager.cancel(notificationID);

	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onBind(intent);
	}
}
