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
        		rootView = inflater.inflate(R.layout.control_spinner, container, false);
        		Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_detail);
        		// Create an ArrayAdapter using the string array and a default spinner layout
        		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
        		        R.array.fisicos_array, android.R.layout.simple_spinner_item);
        		// Specify the layout to use when the list of choices appears
        		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		// Apply the adapter to the spinner
        		spinner.setAdapter(adapter);
        	}else if (mItem.content.equals("List View")){
        		rootView = inflater.inflate(R.layout.control_listview, container, false);
        		ListView list = (ListView) rootView.findViewById(R.id.list_view_detail);
        		// Create an ArrayAdapter using the string array and a default spinner layout
        		ListAdapter<CharSequence> adapter = ListAdapter.createFromResource(getActivity(),
        		        R.array.fisicos_array, android.R.layout.simple_spinner_item);
        		// Specify the layout to use when the list of choices appears
        		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		// Apply the adapter to the spinner
        		list.setAdapter(adapter);
        	}else{
        		rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        		((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
        	}
        }
        return rootView;
    }
}
