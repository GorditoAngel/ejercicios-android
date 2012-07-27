package com.especialcursos.ui;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class DialogosYNotificacionesActivity extends Activity {
	
	static final int DIALOGO_CONFIRMAR_CIERRE_APP_ID = 1;
	static final int DIALOGO_LISTA_ESTILOS_MUSICALES = 2;
	static final int DIALOGO_LISTA_RADIOBUTTONS_ESTILOS_MUSICALES = 3;
	static final int DIALOGO_BARRA_PROGRESO = 4;
	static final int DIALOGO_PERSONALIZADO = 5;
	static final int DIALOGO_PERSONALIZADO_2 = 6;
	static final int DIALOGO_FECHA = 7;
	static final int DIALOGO_HORA = 8;
	
	ThreadProgreso threadProgreso;
    ProgressDialog dialogoBarraProgreso;
    int dia;
    int mes;
    int año;
    int hora;
    int minuto;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnCerrar = (Button) findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(DIALOGO_CONFIRMAR_CIERRE_APP_ID);				
			}
		});
        
        Button btnEstilo = (Button) findViewById(R.id.btnEstilo);
        btnEstilo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(DIALOGO_LISTA_ESTILOS_MUSICALES);				
			}
		});
        
        Button btnEstiloRB = (Button) findViewById(R.id.btnEstiloRB);
        btnEstiloRB.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(DIALOGO_LISTA_RADIOBUTTONS_ESTILOS_MUSICALES);				
			}
		});
        
        Button btnProgresoIndeterminado = (Button) findViewById(R.id.btnProgresoIndeterminado);
        btnProgresoIndeterminado.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				ProgressDialog progreso = ProgressDialog.show(DialogosYNotificacionesActivity.this, "", "Escuchando...", true);
				progreso.setCancelable(true);
			}
		});
        
        Button btnBarraProgreso = (Button) findViewById(R.id.btnBarraProgreso);
        btnBarraProgreso.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(DIALOGO_BARRA_PROGRESO);				
			}
		});
        
        Button btnEscuchar = (Button) findViewById(R.id.btnEscuchar);
        btnEscuchar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(DIALOGO_PERSONALIZADO);				
			}
		});
        
        Button btnEscuchar2 = (Button) findViewById(R.id.btnEscuchar2);
        btnEscuchar2.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(DIALOGO_PERSONALIZADO_2);				
			}
		});
        
        Button btnFecha = (Button) findViewById(R.id.btnFecha);
        btnFecha.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(DIALOGO_FECHA);				
			}
		});
        
        Button btnHora = (Button) findViewById(R.id.btnHora);
        btnHora.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(DIALOGO_HORA);				
			}
		});
                
        // Selección de fecha. Se implementa el método establecerFecha(), 
        // que mostrará la fecha en el TextView "fechaSeleccionada"
        Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        año = c.get(Calendar.YEAR);
        hora = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);
        establecerFecha();   
        establecerHora();
    }
    
    // Se declara el listener de fecha seleccionada como variable miembro de la clase
    // Este listener será pasado al diálogo DatePickerDialog en su constructor, y recibirá
    // el evento on-date-set del mismo.
    DatePickerDialog.OnDateSetListener listenerFecha = new DatePickerDialog.OnDateSetListener() {
		
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			
			dia = dayOfMonth;
			mes = monthOfYear;
			año = year;
			establecerFecha();				
		}
	};
    
	// Se declara el listener de hora seleccionada como variable miembro de la clase
    // Este listener será pasado al diálogo TimePickerDialog en su constructor, y recibirá
    // el evento on-time-set del mismo.
    TimePickerDialog.OnTimeSetListener listenerHora = new TimePickerDialog.OnTimeSetListener() {
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hora = hourOfDay;
			minuto = minute;
			establecerHora();			
		}
	};
	
    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog dialogo;    	
    	switch (id) {
		case DIALOGO_CONFIRMAR_CIERRE_APP_ID:
			dialogo = crearAlertaConfirmarCierreAplicacion();
			break;
		case DIALOGO_LISTA_ESTILOS_MUSICALES:
			dialogo = crearDialogoListaEstilosMusicales();
			break;
		case DIALOGO_LISTA_RADIOBUTTONS_ESTILOS_MUSICALES:
			dialogo = crearDialogoListaRadioButtonsEstilosMusicales();
			break;
		case DIALOGO_BARRA_PROGRESO:
			dialogoBarraProgreso = crearDialogoBarraProgreso();
			return dialogoBarraProgreso;			
		case DIALOGO_PERSONALIZADO:
			dialogo = crearDialogoPersonalizado();
			break;
		case DIALOGO_PERSONALIZADO_2:
			dialogo = crearDialogoPersonalizado2();
			break;
		case DIALOGO_FECHA:
			return new DatePickerDialog(this, listenerFecha, año, mes, dia);			
		case DIALOGO_HORA: // En formato 24h
			return new TimePickerDialog(this, listenerHora, hora, minuto, true);			
		default:
			dialogo = null;
			break;
		}
    	return dialogo;    	
    }
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {    	
        switch(id) {
        case DIALOGO_BARRA_PROGRESO:
        	dialogoBarraProgreso.setProgress(0);
        	threadProgreso = new ThreadProgreso(controlador);
        	threadProgreso.start();
        }    
    }
    
    private void establecerFecha() {
    	
    	TextView fechaSeleccionada = (TextView) findViewById(R.id.fechaSeleccionada);
    	
    	// El primer mes se almacena como cero (tanto en Calendar como en DatePicker),
    	// por lo que se suma 1 al mes.
    	fechaSeleccionada.setText(
    			new StringBuilder().append("Fecha: ").
    								append(dia).append("/").
    								append(mes+1).append("/").
    								append(año));
    }
    
    private void establecerHora() {
    	
    	TextView horaSeleccionada = (TextView) findViewById(R.id.horaSeleccionada);
    	
    	// Se completa con ceros por la izquierda en caso de tener un valor menor que 9
    	String stMinuto = "";
    	String stHora = "";
    	
    	if (minuto <= 9)
    		stMinuto = "0" + String.valueOf(minuto);
    	else
    		stMinuto = String.valueOf(minuto);
    	
    	if (hora <= 9)
    		stHora = "0" + String.valueOf(hora);
    	else
    		stHora = String.valueOf(hora);
    	
    	horaSeleccionada.setText(
    			new StringBuilder().append("Hora: ").
    								append(stHora).append(":").
    								append(stMinuto));
    }
    
    private AlertDialog crearAlertaConfirmarCierreAplicacion() {
    	
    	AlertDialog alerta = null;
    	// Se instancia el constructor de la alerta (Context de parámetro) 
    	AlertDialog.Builder constructor = new AlertDialog.Builder(this);
    	
    	// Se establece el título de la alerta
    	constructor.setTitle(R.string.confirmar_cierre);
    	// Se añade un icono a la izquierda del título
    	constructor.setIcon(R.drawable.logo_android);
    	// La alerta será cancelable (el botón "atrás" la cerrará)
    	constructor.setCancelable(true);

    	// Se añade el botón "Sí", junto con su listener 
    	constructor.setPositiveButton(R.string.si, new OnClickListener() {
			    		
			public void onClick(DialogInterface dialog, int which) {
				// El listener cerrará la aplicación
				DialogosYNotificacionesActivity.this.finish();				
			}
		});
    	
    	// Se añade el botón "No", junto con su listener
    	constructor.setNegativeButton(R.string.no, new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// El diálogo se cierra
				dialog.dismiss();				
			}
		});
    	    	
    	// Se crea la alerta a través del constructor
    	alerta = constructor.create();
    	
    	// Se añade un listener para el evento on-dismiss
    	alerta.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub				
			}
		});
    	
    	// Se añade un listener para el evento on-cancel
    	alerta.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub				
			}
		});
    	
    	return alerta;
    }
    
    private AlertDialog crearDialogoListaEstilosMusicales() {
    	
    	AlertDialog estilos = null;
    	// Se instancia el constructor del diálogo (Context de parámetro) 
    	AlertDialog.Builder constructor = new AlertDialog.Builder(this);
    	
    	// Se establece el título del diálogo
    	constructor.setTitle(R.string.elige_estilo);
    	// Se añade un icono a la izquierda del título
    	constructor.setIcon(R.drawable.note);
    	// El diálogo NO será cancelable (el botón "atrás" NO podrá cerrar el diálogo)
    	constructor.setCancelable(false);

    	// La lista de items se carga en un array. Los elementos a cargar están en el archivo strings.xml
    	final String[] estilosMusica = getResources().getStringArray(R.array.estilos_musica_array);
    	
    	// La lista de items se añade como una lista simple
    	constructor.setItems(estilosMusica, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {    	    	   	    	
    	    	// Se muestra el estilo de música seleccionado, una vez pulsado (cuando el diálogo se cierra)
    	        Toast.makeText(getApplicationContext(), estilosMusica[item], Toast.LENGTH_SHORT).show();    	       
    	    }
    	});    	
    	    	
    	// Se crea el la alerta a través del constructor
    	estilos = constructor.create();
    	
    	// Se añade un listener para el evento on-dismiss
    	estilos.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub				
			}
		});
    	
    	// Se añade un listener para el evento on-cancel
    	estilos.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub				
			}
		});
    	
    	return estilos;
    }
    
    private AlertDialog crearDialogoListaRadioButtonsEstilosMusicales() {
    	
    	AlertDialog estilos = null;
    	// Se instancia el constructor del diálogo (Context de parámetro) 
    	AlertDialog.Builder constructor = new AlertDialog.Builder(this);
    	
    	// Se establece el título del diálogo
    	constructor.setTitle(R.string.elige_estilo);
    	// Se añade un icono a la izquierda del título
    	constructor.setIcon(R.drawable.note);
    	// El diálogo será cancelable (el botón "atrás" podrá cerrar el diálogo)
    	constructor.setCancelable(true);

    	// La lista de items se carga en un array. Los elementos a cargar están en el archivo strings.xml
    	final String[] estilosMusica = getResources().getStringArray(R.array.estilos_musica_array);
    	
    	// La lista de items se añade incluyendo radiobuttons para recordar el estilo seleccionado
    	constructor.setSingleChoiceItems(estilosMusica, -1, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {    	    	   	    	
    	    	// Se muestra el estilo de música seleccionado, una vez pulsado (cuando el diálogo se cierra)
    	        Toast.makeText(getApplicationContext(), estilosMusica[item], Toast.LENGTH_SHORT).show();
    	        // Es necesario cerrar manualmente el diálgo, en caso de tener radiobuttons o checkboxes
    	        dialog.dismiss();
    	    }
    	});    	
    	    	
    	// Se crea el la alerta a través del constructor
    	estilos = constructor.create();
    	
    	// Se añade un listener para el evento on-dismiss
    	estilos.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub				
			}
		});
    	
    	// Se añade un listener para el evento on-cancel
    	estilos.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub				
			}
		});
    	
    	return estilos;
    }
    
    private ProgressDialog crearDialogoBarraProgreso() {
    	    	
    	// Se crea el diálogo, pasando el contexto
    	dialogoBarraProgreso = new ProgressDialog(this);
    	// Se establece el estilo a barra horizontal (por defecto es el círculo)
    	dialogoBarraProgreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);    	
    	dialogoBarraProgreso.setMessage("Cargando...");
    	// Si no se hace cancelable, la aplicación se bloqueará hasta que concluya la hipotética tarea
    	dialogoBarraProgreso.setCancelable(true);

    	dialogoBarraProgreso.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			public void onDismiss(DialogInterface dialog) {										
				dialogoBarraProgreso.setProgress(0);
				threadProgreso.setState(ThreadProgreso.FINALIZADO);
			}
		});
    	
    	return dialogoBarraProgreso;
    } 
    
    private Dialog crearDialogoPersonalizado() {
    	
    	Dialog dialogoPersonalizado = new Dialog(this);
    	// Se establece el layout del diálogo
    	dialogoPersonalizado.setContentView(R.layout.dialogo_personalizado);
    	// Se asigna un título (si no, el espacio del título se visualizará vacío)
    	dialogoPersonalizado.setTitle(R.string.ahora_suena);
    	
    	// Ahora se puede acceder a los componentes del diálogo, para modificarlos
    	TextView texto = (TextView) dialogoPersonalizado.findViewById(R.id.texto_dialogo);
    	texto.setText("Chillout");
    	
    	ImageView icono = (ImageView) dialogoPersonalizado.findViewById(R.id.imagen_dialogo);
    	icono.setImageResource(R.drawable.cd);
	
    	return dialogoPersonalizado;
    }
    
    private AlertDialog crearDialogoPersonalizado2() {
    	
    	AlertDialog dialogoPersonalizado;
    	AlertDialog.Builder constructor;
    	
    	// Se "infla" la vista desde el elemento raíz del layout (LinearLayout)
    	View layoutDialogo = getLayoutInflater().inflate(R.layout.dialogo_personalizado, (ViewGroup) findViewById(R.id.raiz_dialogo_personalizado));
    	
    	// Ahora se puede acceder a los componentes del diálogo, para modificarlos
    	TextView texto = (TextView) layoutDialogo.findViewById(R.id.texto_dialogo);
    	texto.setText(texto.getText().toString() + " Chillout");
    	
    	ImageView icono = (ImageView) layoutDialogo.findViewById(R.id.imagen_dialogo);
    	icono.setImageResource(R.drawable.cd);
	
    	// Se instancia el constructor, pasándole el contexto
    	constructor = new AlertDialog.Builder(this);
    	// Se asocia la vista "inflada" desde el XML
    	constructor.setView(layoutDialogo);
    	
    	// Se añade el botón "Parar", junto con su listener 
    	constructor.setNeutralButton(R.string.parar, new OnClickListener() {
			    		
			public void onClick(DialogInterface dialog, int which) {
				// El listener cerrará el diálogo
				dismissDialog(DIALOGO_PERSONALIZADO_2);			
			}
		});
    	
    	// Se crea el diálogo a través del constructor
    	dialogoPersonalizado = constructor.create();
    	
    	return dialogoPersonalizado;
    }
       
    // Se define el controlador que recibirá mensajes del hilo que actualiza el progreso del ProgressDialog
    final Handler controlador = new Handler() {
    	
        public void handleMessage(Message msg) {
            int total = msg.arg1;
            dialogoBarraProgreso.setProgress(total);
            if (total >= 100) {
                dismissDialog(DIALOGO_BARRA_PROGRESO);
                threadProgreso.setState(ThreadProgreso.FINALIZADO);
            }
        }
    };   
            
    private class ThreadProgreso extends Thread {
        
    	Handler mControlador;
        final static int FINALIZADO = 0;
        final static int EJECUTANDO = 1;
        int estado;
        int total;
       
        public ThreadProgreso(Handler h) {
            mControlador = h;
        }
       
        public void run() {
            estado = EJECUTANDO;   
            total = 0;
            while (estado == EJECUTANDO) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Log.e("ERROR", "Thread Interrupted");
                }
                Message msg = mControlador.obtainMessage();
                msg.arg1 = total;
                mControlador.sendMessage(msg);
                total++;
            }
        }
        
        // Establece el estado actual del hilo, usado para pararlo
        public void setState(int state) {
            estado = state;
        }
    }
}
