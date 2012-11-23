package com.especialcursos.tema13.intents;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCalendario extends SQLiteOpenHelper{
	
	static final String NOMBRE_DB = "calendario_db";
	static final String TB_CALENDARIO = "Calendario";
	static final String CITA_ID = "cita_id";
	static final String CITA_DESCRIPCION = "descripcion";
	static final String CITA_LUGAR = "lugar";
	static final String CITA_FECHA = "fecha";
	static final String CITA_AVISAR = "avisar";
	
	private String crear_tabla= "CREATE TABLE " + TB_CALENDARIO
			+ " (" + CITA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ CITA_DESCRIPCION + " TEXT, "
			+ CITA_LUGAR + " TEXT, "
			+ CITA_FECHA + " TIMESTAMP NOT NULL DEFAULT current_timestamp, "
			+ CITA_AVISAR + " INTEGER)";
	
	public BDCalendario(Context context){
		super (context, NOMBRE_DB, null, 1);
	}
	
	public BDCalendario(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Creación de la base de datos y tabla
		db.execSQL(crear_tabla);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TB_CALENDARIO);
        //Se crea la nueva versión de la tabla
        db.execSQL(crear_tabla);
		
	}
	
	private void agregarDatosIniciales(SQLiteDatabase db){
		ArrayList<Cita> citas = new ArrayList<Cita>();
		citas.add(new Cita(0, "tomar un cafe", "Startbucks de Atocha",
				 new Date(), 10));
	}
	
	private void agregarCitas(ArrayList<Cita> citas){
		
	}
	
	public void agregarCita(Cita cita){
		
	}
	
	public void borrarCita(Cita cita){
		
	}
	
	public void borrarCita(int id){
		
	}
	
	public Cita getCita(int id){
		return null;
	}
	
	public ArrayList<Cita> getCitas(){
		return null;
	}
	
}
