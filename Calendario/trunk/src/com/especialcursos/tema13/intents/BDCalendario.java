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

	private Context contexto;
	
	public BDCalendario(Context context){
		super (context, NOMBRE_DB, null, 1);
		contexto = context;
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
		agregarDatosIniciales(db);
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
				 new GregorianCalendar(2012,11,9,14,30) , 10));
		citas.add(new Cita(0, "ir al ginmansio", "Odisey Gim",
				 new GregorianCalendar(2012,11,30,19,00) , 0));
		citas.add(new Cita(0, "Clase de estado sólido", "Facultad de Físicas",
				 new GregorianCalendar(2012,12,3,19,30) , 60));
		citas.add(new Cita(0, "Cita con mi fisioterapeuta", "Getafe",
				 new GregorianCalendar(2012,12,10,20,00) , 10));
		
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
	
	public Cursor getAll(){
		return getReadableDatabase().rawQuery("SELECT " + CITA_ID + ", " +
				CITA_DESCRIPCION + ", " +
				CITA_LUGAR + ", " +
				CITA_FECHA + ", " +
				CITA_AVISAR + ", FROM " + TB_CALENDARIO +
				" ORDER BY " + CITA_FECHA,
				null);
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
