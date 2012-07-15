package com.especialcursos.ui;

import java.util.Calendar;

public class Dedito {
	private int pointerID;
	private int x_i,y_i,x_f,y_f;
	
	private float x_mov;
	private float y_mov;
	private Calendar instante= null;
	
	private Calendar tiempoInicio = null;
	private Calendar tiempoFinal = null;
	
	public Dedito(int index){
		pointerID = index;
	}
	
	public Dedito(){
	}
	
	public int getID(){
		return pointerID;
	}
	public void setID(int id){
		pointerID = id;
	}

	public int getX_i() {
		return x_i;
	}

	public void setX_i(int x_i) {
		this.x_i = x_i;
	}

	public int getY_i() {
		return y_i;
	}

	public void setY_i(int y_i) {
		this.y_i = y_i;
	}

	public int getX_f() {
		return x_f;
	}

	public void setX_f(int x_f) {
		this.x_f = x_f;
	}

	public int getY_f() {
		return y_f;
	}

	public void setY_f(int y_f) {
		this.y_f = y_f;
	}

	public float getX_mov() {
		return x_mov;
	}

	public void setX_mov(float x_mov) {
		this.x_mov = x_mov;
	}

	public float getY_mov() {
		return y_mov;
	}

	public void setY_mov(float y_mov) {
		this.y_mov = y_mov;
	}

	public Calendar getInstante() {
		return instante;
	}

	public void setInstante(Calendar instante) {
		this.instante = instante;
	}

	public Calendar getTiempoInicio() {
		return tiempoInicio;
	}

	public void setTiempoInicio(Calendar tiempoInicio) {
		this.tiempoInicio = tiempoInicio;
	}

	public Calendar getTiempoFinal() {
		return tiempoFinal;
	}

	public void setTiempoFinal(Calendar tiempoFinal) {
		this.tiempoFinal = tiempoFinal;
	}
}
