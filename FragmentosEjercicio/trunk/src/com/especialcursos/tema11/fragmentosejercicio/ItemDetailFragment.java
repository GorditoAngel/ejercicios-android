package com.especialcursos.tema11.fragmentosejercicio;

import com.especialcursos.tema11.fragmentosejercicio.dummy.DummyContent;

import android.content.CursorLoader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
        }
        return rootView;
    }
}
