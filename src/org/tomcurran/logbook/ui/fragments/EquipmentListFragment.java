package org.tomcurran.logbook.ui.fragments;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.provider.LogbookContract;
import org.tomcurran.logbook.util.NotifyingAsyncQueryHandler;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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

public class EquipmentListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>, BaseDialogFragment.BaseDialogSuccessListener, NotifyingAsyncQueryHandler.AsyncDeleteListener {

    public static final String TAG = "equipment_list_fragment";

    private CursorAdapter mAdapter;
    private NotifyingAsyncQueryHandler mHandler;

    // life cycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mHandler = new NotifyingAsyncQueryHandler(getActivity().getContentResolver());
        mHandler.setDeleteListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(getString(R.string.list_emtpy_equipment));
        mAdapter = new EquipmentListAdapter(getActivity());
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
        registerForContextMenu(getListView());
        EquipmentDialogFragment dialog = (EquipmentDialogFragment)getSupportFragmentManager().findFragmentByTag(EquipmentDialogFragment.TAG);
        if (dialog != null) {
            dialog.setOnSuccessListener(this);
        }
    }


    // Options menu

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_list_equipment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.options_menu_list_equipment_insert: {
            createEquipment();
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
        editEquipment((Cursor) getListAdapter().getItem(position));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = (MenuInflater) getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_list_item_equipment, menu);
        menu.setHeaderTitle(
                 ((TextView) ((AdapterContextMenuInfo) menuInfo)
                         .targetView.findViewById(android.R.id.text1)).getText());
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
        case R.id.context_menu_list_item_equipment_edit: {
            editEquipment((Cursor) getListAdapter().getItem(((AdapterContextMenuInfo)item.getMenuInfo()).position));
            return true;
        }
        case R.id.context_menu_list_item_equipment_delete: {
            deleteEquipment(((AdapterContextMenuInfo)item.getMenuInfo()).id);
            return true;
        }
        default:
            return super.onContextItemSelected(item);
        }
    }


    // helpers

    private void createEquipment() {
        EquipmentDialogFragment.newInstance()
                .setOnSuccessListener(this)
                .show(getSupportFragmentManager(), EquipmentDialogFragment.TAG);
    }

    private void editEquipment(final Cursor equipment) {
        EquipmentDialogFragment.newInstance(
                equipment.getInt(EquipmentQuery._ID),
                equipment.getString(EquipmentQuery.CANOPY_NAME),
                equipment.getInt(EquipmentQuery.CANOPY_SIZE))
                    .setOnSuccessListener(this)
                    .show(getSupportFragmentManager(), EquipmentDialogFragment.TAG);
    }

    private void deleteEquipment(long equipmentId) {
        mHandler.startDelete(LogbookContract.Equipment.buildEquipmentUri(equipmentId));
    }


    // loaders

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                LogbookContract.Equipment.CONTENT_URI,
                EquipmentQuery.PROJECTION,
                null,
                null,
                LogbookContract.Equipment.DEFAULT_SORT
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

    public static class EquipmentListAdapter extends SimpleCursorAdapter {

        private static final int[] TO = {
            android.R.id.text1
        };

        public EquipmentListAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1, null, EquipmentQuery.PROJECTION, TO, 0);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                holder.text = (TextView) view.findViewById(android.R.id.text1);
                view.setTag(holder);
            }

            holder.text.setText(
                    context.getString(R.string.text_list_item_equipment,
                            cursor.getString(EquipmentQuery.CANOPY_NAME),
                            cursor.getInt(EquipmentQuery.CANOPY_SIZE))
            );
        }

        static class ViewHolder {
            TextView text;
        }

    }


    // queries

    private interface EquipmentQuery {

        String[] PROJECTION = {
                LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME,
                LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE,
                LogbookContract.Equipment._ID
        };

        int CANOPY_NAME = 0;
        int CANOPY_SIZE = 1;
        int _ID = 2;
    }

    @Override
    public void onDeleteComplete(int result) {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onDialogSuccess(Long rowId) {
        getLoaderManager().restartLoader(0, null, this);
    }

}