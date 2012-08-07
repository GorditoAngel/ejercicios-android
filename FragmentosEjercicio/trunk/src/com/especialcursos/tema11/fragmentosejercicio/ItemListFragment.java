package com.especialcursos.tema11.fragmentosejercicio;

import com.especialcursos.tema11.fragmentosejercicio.dummy.DummyContent;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.app.LoaderManager;


public class ItemListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final int ID_LOADER = 2001;

    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;
    
    
    private SimpleCursorAdapter mAdapter;

    public interface Callbacks {

        public void onItemSelected(String id, String name);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        
        public void onItemSelected(String id, String name) {
        }
    };

    public ItemListFragment() {
    }
    
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String[] columnas = { MediaStore.MediaColumns.TITLE,
        		MediaStore.MediaColumns.MIME_TYPE,
        		MediaStore.MediaColumns.DATE_ADDED,
        		MediaStore.MediaColumns.SIZE };
        int[] contenedor_items = { android.R.id.text1,
        		android.R.id.text2};
        
        mAdapter = new SimpleCursorAdapter(getActivity(),
        		android.R.layout.simple_list_item_1,
        		null,
        		columnas,
                contenedor_items,0);
        
        setListAdapter(mAdapter);
        
        //No moestramos la lista de momento
        setListShown(false);
        
        //prepara el loader
        getLoaderManager().initLoader(ID_LOADER, null, (LoaderCallbacks<Cursor>) this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState
                .containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(Long.toString(id), listView.getItemAtPosition(position).toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }



	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
		String[] proyeccion = { MediaStore.MediaColumns._ID,
		MediaStore.MediaColumns.TITLE,
		MediaStore.MediaColumns.MIME_TYPE,
		MediaStore.MediaColumns.DATE_ADDED,
		MediaStore.MediaColumns.SIZE};
		String select = "";
		String ordenacion = MediaStore.MediaColumns.TITLE + " DESC";
		return new CursorLoader(getActivity(), uri, proyeccion, select, null,
				ordenacion);
	}



	public void onLoadFinished(Loader<Cursor> loader, Cursor datos) {
		mAdapter.swapCursor(datos);
		
		//muestra la lista
		if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
	}



	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
		
	}
}
