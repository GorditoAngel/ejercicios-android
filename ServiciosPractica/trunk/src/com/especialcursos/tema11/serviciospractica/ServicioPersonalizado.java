package com.especialcursos.tema11.serviciospractica;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

public class ServicioPersonalizado extends IntentService {
    public ServicioPersonalizado(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
		//Este metodo se invoca cuando se invoca bindService(Intent)
        throw new UnsupportedOperationException("Not yet implemented");
    }

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		
	}
}
