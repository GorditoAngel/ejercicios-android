package com.especialcursos.tema13.intents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;



import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BDCalendario extends SQLiteOpenHelper{
	
	private static final String TAG = BDCalendario.class.getSimpleName();
	
	static private int VERSION = 5;
	
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
			+ CITA_FECHA + " INTEGER, "
			+ CITA_AVISAR + " INTEGER)";

	private Context contexto;
	
	public BDCalendario(Context context){
		super (context, NOMBRE_DB, null, VERSION);
		contexto = context;
	}
	
	public BDCalendario(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Creaci�n de la base de datos y tabla
		db.execSQL(crear_tabla);
		agregarDatosIniciales(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Se elimina la versi�n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TB_CALENDARIO);
        //Se crea la nueva versi�n de la tabla
        db.execSQL(crear_tabla);
        agregarDatosIniciales(db);
		
	}
	
	private void agregarDatosIniciales(SQLiteDatabase db){
		ArrayList<Cita> citas = new ArrayList<Cita>();
		
		citas.add(new Cita(0, "tomar un cafe", "Startbucks de Atocha",
				 new GregorianCalendar(2012,11,9,14,30) , 10));
		citas.add(new Cita(0, "ir al ginmansio", "Odisey Gim",
				 new GregorianCalendar(2012,11,30,19,00) , 0));
		citas.add(new Cita(0, "Clase de estado s�lido", "Facultad de F�sicas",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,3,19,30) , 60));
		citas.add(new Cita(0, "Cita con mi fisioterapeuta", "Getafe",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,10,20,00) , 10));
		citas.add(new Cita(0, "Cita mes 1", "Getafe",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,10,20,00) , 10));
		citas.add(new Cita(0, "Cita mes 2", "Getafe",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,15,20,00) , 10));
		citas.add(new Cita(0, "Cita mes 3", "Getafe",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,1,20,00) , 10));
		citas.add(new Cita(0, "Cita dia 1", "Getafe",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,5,20,01) , 10));
		citas.add(new Cita(0, "Cita dia 2", "Getafe",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,5,5,00) , 10));
		citas.add(new Cita(0, "Cita dia 3", "Getafe",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,5,7,01) , 10));
		citas.add(new Cita(0, "Cita semana 1", "Getafe",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,8,7,01) , 10));
		citas.add(new Cita(0, "Cita semana 2", "Getafe",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,7,7,01) , 10));
		citas.add(new Cita(0, "Cita semana 3", "Getafe",
				 new GregorianCalendar(2012,GregorianCalendar.DECEMBER,6,7,01) , 10));
		
		agregarCitas(citas, db);
	}
	
	private void agregarCitas(ArrayList<Cita> citas, SQLiteDatabase db){
		for (Cita cita : citas) {
			try {
				ContentValues cont = new ContentValues();
				cont.put(CITA_DESCRIPCION, cita.get_descripcion());
				cont.put(CITA_LUGAR, cita.get_lugar());
				cont.put(CITA_AVISAR, cita.get_avisar());
				cont.put(CITA_FECHA, cita.get_fecha().getTimeInMillis());
				long rowId = db.insertOrThrow(TB_CALENDARIO, null, cont);
				if (rowId == -1)
					throw new Exception("problem when write in database");
			} catch (SQLException e) {
				Log.e(TAG, "problem when write in database");
				e.printStackTrace();
			} catch (Exception e) {
				Log.e(TAG, "problem when write in database");
				e.printStackTrace();
			}
		}
	}
	
	//Unos elegantes accesos de lectura y escritura en la BD mediante cursores
	//Cogido de un gran libro: Android Programing Tutorials, 3rd ed
	
	public void insert(String descripcion, String lugar, long fecha, int avisar){
		ContentValues cv = new ContentValues();
		
		cv.put(CITA_DESCRIPCION, descripcion);
		cv.put(CITA_LUGAR, lugar);
		cv.put(CITA_FECHA, fecha);
		cv.put(CITA_AVISAR, avisar);
		
		getWritableDatabase().insert(TB_CALENDARIO, null, cv);
	}
	
	public ArrayList<Cita> getAll(){
		ArrayList<Cita> lista_citas = new ArrayList<Cita>();
		
		Cursor c = getReadableDatabase().query(TB_CALENDARIO,
				new String[] {CITA_ID, CITA_DESCRIPCION, CITA_LUGAR, CITA_FECHA, CITA_AVISAR},
				null, null, null, null, CITA_FECHA);
		//Iteramos a traves de los registros del cursor
		if (c.moveToFirst()) {
			do{
				Cita cita = new Cita(getId(c),getDescripcion(c),getLugar(c),
						getFechaGC(c),getAvisar(c));
				lista_citas.add(cita);
		    }while (c.moveToNext());
		}
		return lista_citas.isEmpty() ? null : lista_citas;
		
//		return getReadableDatabase().rawQuery("SELECT " + CITA_ID + ", " +
//				CITA_DESCRIPCION + ", " +
//				CITA_LUGAR + ", " +
//				CITA_FECHA + ", " +
//				CITA_AVISAR + ", FROM " + TB_CALENDARIO +
//				" ORDER BY " + CITA_FECHA,
//				null);
	}
	
	public ArrayList<Cita> getHoy(){
		ArrayList<Cita> lista_citas = new ArrayList<Cita>();
		
		GregorianCalendar hoy = (GregorianCalendar) GregorianCalendar.getInstance();
		int dia_hoy = hoy.get(GregorianCalendar.DAY_OF_MONTH);
		int mes = hoy.get(GregorianCalendar.MONTH);
		int ano = hoy.get(GregorianCalendar.YEAR);
		
		Cursor c = getReadableDatabase().query(TB_CALENDARIO,
				new String[] {CITA_ID, CITA_DESCRIPCION, CITA_LUGAR, CITA_FECHA, CITA_AVISAR},
				null, null, null, null, CITA_FECHA);
		//Iteramos a traves de los registros del cursor
		if (c.moveToFirst()) {
			do{
				Cita cita = new Cita(getId(c),getDescripcion(c),getLugar(c),
						getFechaGC(c),getAvisar(c));
				if (dia_hoy == cita.get_fecha().get(GregorianCalendar.DAY_OF_MONTH)&&
						mes == cita.get_fecha().get(GregorianCalendar.MONTH) &&
						ano == cita.get_fecha().get(GregorianCalendar.YEAR))
					lista_citas.add(cita);
		    }while (c.moveToNext());
		}
		return lista_citas.isEmpty() ? null : lista_citas;
	}
	
	public ArrayList<Cita> getSemana(){
		ArrayList<Cita> lista_citas = new ArrayList<Cita>();
		
		GregorianCalendar hoy = (GregorianCalendar) GregorianCalendar.getInstance();
		int semana = hoy.get(GregorianCalendar.WEEK_OF_YEAR);
		int mes = hoy.get(GregorianCalendar.MONTH);
		int ano = hoy.get(GregorianCalendar.YEAR);
		
		Cursor c = getReadableDatabase().query(TB_CALENDARIO,
				new String[] {CITA_ID, CITA_DESCRIPCION, CITA_LUGAR, CITA_FECHA, CITA_AVISAR},
				null, null, null, null, CITA_FECHA);
		//Iteramos a traves de los registros del cursor
		if (c.moveToFirst()) {
			do{
				Cita cita = new Cita(getId(c),getDescripcion(c),getLugar(c),
						getFechaGC(c),getAvisar(c));
				if (semana == cita.get_fecha().get(GregorianCalendar.WEEK_OF_YEAR) &&
						mes == cita.get_fecha().get(GregorianCalendar.MONTH) &&
								ano == cita.get_fecha().get(GregorianCalendar.YEAR))
					lista_citas.add(cita);
		    }while (c.moveToNext());
		}
		return lista_citas.isEmpty() ? null : lista_citas;
	}

	public ArrayList<Cita> getMes(){
		ArrayList<Cita> lista_citas = new ArrayList<Cita>();
		
		GregorianCalendar hoy = (GregorianCalendar) GregorianCalendar.getInstance();
		int mes = hoy.get(GregorianCalendar.MONTH);
		int ano = hoy.get(GregorianCalendar.YEAR);
		
		Cursor c = getReadableDatabase().query(TB_CALENDARIO,
				new String[] {CITA_ID, CITA_DESCRIPCION, CITA_LUGAR, CITA_FECHA, CITA_AVISAR},
				null, null, null, null, CITA_FECHA);
		//Iteramos a traves de los registros del cursor
		if (c.moveToFirst()) {
			do{
				Cita cita = new Cita(getId(c),getDescripcion(c),getLugar(c),
						getFechaGC(c),getAvisar(c));
				if (mes == cita.get_fecha().get(GregorianCalendar.MONTH) &&
								ano == cita.get_fecha().get(GregorianCalendar.YEAR))
					lista_citas.add(cita);
		    }while (c.moveToNext());
		}
		return lista_citas.isEmpty() ? null : lista_citas;
	}
	
	public int getId(Cursor c){
		return c.getInt(0);
	}
	
	public String getDescripcion(Cursor c){
		return c.getString(1);
	}
	
	public String getLugar(Cursor c){
		return c.getString(2);
	}
	
	public GregorianCalendar getFechaGC(Cursor c){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(c.getLong(3));
		return calendar;
	}
	
	public long getFecha(Cursor c){
		return c.getLong(3);
	}
	
	public String getFechaStr(Cursor c){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(c.getLong(3));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMMM/yyyy hh:mm:ss");
		return sdf.format(calendar.getTime());
	}
	
	public int getAvisar(Cursor c){
		return c.getInt(4);
	}
	
	public String getAvisarStr(Cursor c){
		Resources res = contexto.getResources();
		String frase = res.getString(R.string.tv_row_avisar);
		String avisar = String.format(frase,c.getInt(4));
		return avisar;
	}
}
