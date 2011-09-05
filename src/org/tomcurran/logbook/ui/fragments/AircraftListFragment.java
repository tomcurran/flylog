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

public class AircraftListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "aircraft_list_fragment";

    private CursorAdapter mAdapter;
    private BaseDialogFragment.OnSuccessListener mAircraftOnSuccessListener = new BaseDialogFragment.OnSuccessListener() {
        @Override
        public void onSuccess(Long id) {
            getLoaderManager().restartLoader(0, null, AircraftListFragment.this);
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
        activity.getSupportActionBar().setTitle(R.string.title_aircraft_list);
        setEmptyText(getString(R.string.list_emtpy_aircraft));
        mAdapter = new AircraftListAdapter(activity);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
        registerForContextMenu(getListView());
        AircraftDialogFragment dialog = (AircraftDialogFragment)getSupportFragmentManager().findFragmentByTag(AircraftDialogFragment.TAG);
        if (dialog != null) {
            dialog.setOnSuccessListener(mAircraftOnSuccessListener);
        }
    }


    // Options menu

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_list_aircraft, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home: {
            ((BaseActivity)getActivity()).goUp(HomeActivity.class);
            return true;
        }
        case R.id.options_menu_list_aircraft_insert: {
            createAircraft();
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
        final Cursor aircraft = (Cursor) getListAdapter().getItem(position);
        editAircraft(aircraft.getInt(AircraftsQuery._ID), aircraft.getString(AircraftsQuery.NAME));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = (MenuInflater) getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_list_item_aircraft, menu);
        menu.setHeaderTitle(
                 ((TextView) ((AdapterContextMenuInfo) menuInfo)
                         .targetView.findViewById(android.R.id.text1)).getText());
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
        case R.id.context_menu_list_item_aircraft_edit: {
            AdapterContextMenuInfo info = ((AdapterContextMenuInfo)item.getMenuInfo());
            final Cursor aircraft = (Cursor) getListAdapter().getItem(info.position);
            editAircraft(aircraft.getInt(AircraftsQuery._ID), aircraft.getString(AircraftsQuery.NAME));
            return true;
        }
        case R.id.context_menu_list_item_aircraft_delete: {
            deleteAircraft(((AdapterContextMenuInfo)item.getMenuInfo()).id);
            return true;
        }
        default:
            return super.onContextItemSelected(item);
        }
    }


    // helpers

    private void createAircraft() {
        AircraftDialogFragment dialog = AircraftDialogFragment.newInstance();
        dialog.setOnSuccessListener(mAircraftOnSuccessListener);
        dialog.show(getSupportFragmentManager(), AircraftDialogFragment.TAG);
    }

    private void editAircraft(long aircraftId, String aircraftName) {
        AircraftDialogFragment dialog = AircraftDialogFragment.newInstance(aircraftId, aircraftName);
        dialog.setOnSuccessListener(mAircraftOnSuccessListener);
        dialog.show(getSupportFragmentManager(), AircraftDialogFragment.TAG);
    }

    private void deleteAircraft(long aircraftId) {
        getActivity().getContentResolver().delete(LogbookContract.Aircrafts.buildAircraftUri(aircraftId), null, null);
        getLoaderManager().restartLoader(0, null, this);
    }


    // loaders

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                LogbookContract.Aircrafts.CONTENT_URI,
                AircraftsQuery.PROJECTION,
                null,
                null,
                LogbookContract.Aircrafts.DEFAULT_SORT
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

    public static class AircraftListAdapter extends SimpleCursorAdapter {

        private static final int[] TO = {
            android.R.id.text1
        };

        public AircraftListAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1, null, AircraftsQuery.PROJECTION, TO, 0);
        }
    }


    // queries

    private interface AircraftsQuery {

        String[] PROJECTION = {
                LogbookContract.Aircrafts.AIRCRAFT_NAME,
                LogbookContract.Aircrafts._ID
        };

        int NAME = 0;
        int _ID = 1;
    }

}
