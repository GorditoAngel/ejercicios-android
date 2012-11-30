package com.especialcursos.tema13.intents;

import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

public class CitaHolder {
	private TextView descripcion = null;
	private TextView lugar = null;
	private TextView fecha = null;
	private TextView avisar = null;
	
	CitaHolder(View row){
		descripcion = (TextView) row.findViewById(R.id.tv_row_descripcion);
		lugar = (TextView) row.findViewById(R.id.tv_row_lugar);
		fecha = (TextView) row.findViewById(R.id.tv_row_fecha);
		avisar = (TextView) row.findViewById(R.id.tv_row_avisar);
	}
	
	public void populateFrom(Cursor c, BDCalendario helper){
		descripcion.setText(helper.getDescripcion(c));
		lugar.setText(helper.getLugar(c));
		fecha.setText(helper.getFechaStr(c));
		avisar.setText(helper.getAvisarStr(c));
	}
	
}
