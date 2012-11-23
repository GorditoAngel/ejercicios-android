package com.especialcursos.tema13.intents;

import java.sql.Date;

public class Cita {
	private int _id;
	private String _descripcion;
	private String _lugar;
	private Date _fecha;
	private int _avisar;
	
	public Cita(int id, String _descripcion, String _lugar, Date _fecha, int _avisar) {
		super();
		this._id = id;
		this._descripcion = _descripcion;
		this._lugar = _lugar;
		this._fecha = _fecha;
		this._avisar = _avisar;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_descripcion() {
		return _descripcion;
	}

	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}

	public String get_lugar() {
		return _lugar;
	}

	public void set_lugar(String _lugar) {
		this._lugar = _lugar;
	}

	public Date get_fecha() {
		return _fecha;
	}

	public void set_fecha(Date _fecha) {
		this._fecha = _fecha;
	}

	public int get_avisar() {
		return _avisar;
	}

	public void set_avisar(int _avisar) {
		this._avisar = _avisar;
	}
}
