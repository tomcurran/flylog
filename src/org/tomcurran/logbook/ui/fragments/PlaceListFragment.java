package org.tomcurran.logbook.ui.fragments;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.provider.LogbookContract;
import org.tomcurran.logbook.ui.BaseActivity;
import org.tomcurran.logbook.ui.HomeActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuInflater;
import android.support.v4.view.MenuItem;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

public class PlaceListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "place_list_fragment";

    private CursorAdapter mAdapter;
    private BaseDialogFragment.OnSuccessListener mPlaceOnSuccessListener = new BaseDialogFragment.OnSuccessListener() {
        @Override
        public void onSuccess(Long id) {
            getLoaderManager().restartLoader(0, null, PlaceListFragment.this);
        }
    };

    // life cycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        activity.getSupportActionBar().setTitle(R.string.title_places_list);
        setEmptyText(getString(R.string.list_emtpy_places));
        mAdapter = new PlacesListAdapter(activity);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
        registerForContextMenu(getListView());
        PlaceDialogFragment dialog = (PlaceDialogFragment)getSupportFragmentManager().findFragmentByTag(PlaceDialogFragment.TAG);
        if (dialog != null) {
            dialog.setOnSuccessListener(mPlaceOnSuccessListener);
        }
    }


    // Options menu

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_list_places, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home: {
            ((BaseActivity)getActivity()).goUp(HomeActivity.class);
            return true;
        }
        case R.id.options_menu_list_places_insert: {
            createPlace();
            return true;
        }
        default:
            return super.onOptionsItemSelected(item);
        }
    }


    // Context menu

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        final Cursor place = (Cursor) getListAdapter().getItem(position);
        editPlace(place.getInt(PlacesQuery._ID), place.getString(PlacesQuery.NAME));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = (MenuInflater) getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_list_item_places, menu);
        menu.setHeaderTitle(
                 ((TextView) ((AdapterContextMenuInfo) menuInfo)
                         .targetView.findViewById(android.R.id.text1)).getText());
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
        case R.id.context_menu_list_item_places_edit: {
            AdapterContextMenuInfo info = ((AdapterContextMenuInfo)item.getMenuInfo());
            Cursor place = (Cursor) getListAdapter().getItem(info.position);
            editPlace(place.getInt(PlacesQuery._ID), place.getString(PlacesQuery.NAME));
            return true;
        }
        case R.id.context_menu_list_item_places_delete: {
            deletePlace(((AdapterContextMenuInfo)item.getMenuInfo()).id);
            return true;
        }
        default:
            return super.onContextItemSelected(item);
        }
    }


    // helpers

    private void createPlace() {
        BaseDialogFragment dialog = PlaceDialogFragment.newInstance();
        dialog.setOnSuccessListener(mPlaceOnSuccessListener);
        dialog.show(getSupportFragmentManager(), PlaceDialogFragment.TAG);
    }

    private void editPlace(long placeId, String placeName) {
        BaseDialogFragment dialog = PlaceDialogFragment.newInstance(placeId, placeName);
        dialog.setOnSuccessListener(mPlaceOnSuccessListener);
        dialog.show(getSupportFragmentManager(), PlaceDialogFragment.TAG);
    }

    private void deletePlace(long placeId) {
        getActivity().getContentResolver().delete(LogbookContract.Places.buildPlaceUri(placeId), null, null);
        getLoaderManager().restartLoader(0, null, this);
    }


    // loaders

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                LogbookContract.Places.CONTENT_URI,
                PlacesQuery.PROJECTION,
                null,
                null,
                LogbookContract.Places.DEFAULT_SORT
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    // list adapter

    public static class PlacesListAdapter extends SimpleCursorAdapter {

        private static final int[] TO = {
            android.R.id.text1
        };

        public PlacesListAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1, null, PlacesQuery.PROJECTION, TO, 0);
        }
    }


    // queries

    private interface PlacesQuery {

        String[] PROJECTION = {
                LogbookContract.Places.PLACE_NAME,
                LogbookContract.Places._ID
        };

        int NAME = 0;
        int _ID = 1;

    }

}
