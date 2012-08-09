package com.especialcursos.tema11.fragmentospractica;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListaFragment extends ListFragment implements LoaderCallbacks<Cursor> {

	private ListAdapter mAdapter = null;
	
	public interface OnElementoSelectedListener {
		public void onElementoSelected(long idCancion, String nombre);
	}
	
	OnElementoSelectedListener mOnElementoSelectedListener;
	
	
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

	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		((SimpleCursorAdapter) mAdapter).swapCursor(arg1);
		setListAdapter(mAdapter);
	}

	public void onLoaderReset(Loader<Cursor> arg0) {
		((SimpleCursorAdapter) mAdapter).swapCursor(null);	
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		// Se invoca cuando el fragmento se añade a la actividad
		super.onAttach(activity);
		
		try{
			mOnElementoSelectedListener = (OnElementoSelectedListener) activity;
		}catch (ClassCastException e){
			throw new ClassCastException(
					String.format(getString(R.string.interfaz_no_implementada),activity.toString()));
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		String[] from = { MediaStore.MediaColumns.TITLE,
				MediaStore.MediaColumns.MIME_TYPE,
				MediaStore.MediaColumns.DATE_ADDED,
				MediaStore.MediaColumns.SIZE };
		
		int[] to = { android.R.id.text1, android.R.id.text2};
				
		mAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_1,
				null,from,to,0);
		getLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cr = (Cursor) mAdapter.getItem(position);
		mOnElementoSelectedListener.onElementoSelected(id, cr.getString(1));
	}
}
