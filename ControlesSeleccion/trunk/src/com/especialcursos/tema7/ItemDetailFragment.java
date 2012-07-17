package com.especialcursos.tema7;

import com.especialcursos.tema7.dummy.DummyContent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    DummyContent.DummyItem mItem;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	//TODO Creoq eu aqui es donde hay que seleccionar lso layouts
        View rootView = new View(getActivity());
        if (mItem != null) {
        	 
        	//TODO Creoq eu aqui es donde hay que seleccionar lso layouts
        	if (mItem.content.equals("Spinner")){
        		//Especificamos el root view y lo inflamos
        		rootView = inflater.inflate(R.layout.control_spinner, container, false);
        		//Crea Spinner
        		Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_detail);
        		//ArrayAdapter a partir de las lista de resources y con el comodin sinple_spinner_item
        		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
        		        R.array.fisicos_array, android.R.layout.simple_spinner_item);
        		//Especificar el layout para mostrar la lista de opciones.
        		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		//aplicar el adapter
        		spinner.setAdapter(adapter);
        	}else if (mItem.content.equals("List View")){
        		rootView = inflater.inflate(R.layout.control_listview, container, false);
        		//Crea List view y adapter a partir de lis-view_detail y fisicos_array
        		//He utilizado como view de cada item simple_list_item_1
        		ListView list = (ListView) rootView.findViewById(R.id.list_view_detail);
        		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
        		        R.array.fisicos_array, android.R.layout.simple_list_item_1);
        		//aplicar el adapter
        		list.setAdapter(adapter);
        	}else if (mItem.content.equals("Grid View")){
        		rootView = inflater.inflate(R.layout.control_gridview, container, false);
        		//Crea Grid view y adapter a partir de grid_view_detail y fisicos_array
        		//He utilizado como textview de cada item simple_list_item_1
        		GridView grilla = (GridView) rootView.findViewById(R.id.grid_view_detail);
        		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
        		        R.array.fisicos_array, android.R.layout.simple_list_item_1);
        		//aplicar el adapter
        		grilla.setAdapter(adapter);
        	}else{
        		rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        		((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
        	}
        }
        return rootView;
    }
}
