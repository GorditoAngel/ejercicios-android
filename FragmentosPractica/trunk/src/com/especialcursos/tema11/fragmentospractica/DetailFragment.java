package com.especialcursos.tema11.fragmentospractica;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {
	
	TextView tv = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.detail_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		tv = (TextView) getView().findViewById(R.id.textView1);
		super.onActivityCreated(savedInstanceState);
	}
	
	public void rellenarTextView(String texto){
		tv.setText(texto);
	}
	
}
