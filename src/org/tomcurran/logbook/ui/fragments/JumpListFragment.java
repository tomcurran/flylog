package org.tomcurran.logbook.ui.fragments;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.provider.LogbookContract;
import org.tomcurran.logbook.ui.PreferencesActivity;
import org.tomcurran.logbook.util.UIUtils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

public class JumpListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>  {

    public static final String TAG = "jump_list_fragment";

    private static final int ACTIVITY_INSERT = 0;
    private static final int ACTIVITY_EDIT = 1;

    private CursorAdapter mAdapter;


    // life cycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_jumps, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        activity.getSupportActionBar().setTitle(R.string.title_jumps_list);
        registerForContextMenu(getListView());
        mAdapter = new JumpsListAdapter(activity);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getLoaderManager().restartLoader(0, null, this);
    }


    // Options menu

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_list_jumps, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.options_menu_list_jumps_insert: {
            createJump();
            return true;
        }
        case R.id.options_menu_list_jumps_preferences: {
            startActivity(new Intent(getActivity(), PreferencesActivity.class));
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
        editJump(id);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = (MenuInflater) getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_list_item_jumps, menu);
        
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        Cursor jump = (Cursor) getListAdapter().getItem(info.position);
        menu.setHeaderTitle(getString(
                R.string.context_menu_list_item_jumps_title,
                jump.getInt(JumpsQuery.NUMBER)));
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
        case R.id.context_menu_list_item_jumps_edit: {
            editJump(((AdapterContextMenuInfo)item.getMenuInfo()).id);
            return true;
        }
        case R.id.context_menu_list_item_jumps_delete: {
            deleteJump(((AdapterContextMenuInfo)item.getMenuInfo()).id);
            return true;
        }
        default:
            return super.onContextItemSelected(item);
        }
    }


    // helpers

    private void createJump() {
        final Uri jumpUri = LogbookContract.Jumps.CONTENT_URI;
        final Intent intent = new Intent(Intent.ACTION_INSERT, jumpUri);
        startActivityForResult(intent, ACTIVITY_INSERT);
    }

    private void editJump(long jumpId) {
        final Uri jumpUri = LogbookContract.Jumps.buildJumpUri(jumpId);
        final Intent intent = new Intent(Intent.ACTION_EDIT, jumpUri);
        startActivityForResult(intent, ACTIVITY_EDIT);
    }

    private void deleteJump(long jumpId) {
        getActivity().getContentResolver().delete(LogbookContract.Jumps.buildJumpUri(jumpId), null, null);
        getLoaderManager().restartLoader(0, null, this);
    }


    // loaders

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                LogbookContract.Jumps.CONTENT_URI,
                JumpsQuery.PROJECTION,
                null,
                null,
                LogbookContract.Jumps.DEFAULT_SORT
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

    public static class JumpsListAdapter extends SimpleCursorAdapter {

        private static final int[] TO = {
            R.id.list_item_jumps_number,
            R.id.list_item_jumps_date,
            R.id.list_item_jumps_place,
            R.id.list_item_jumps_aircraft,
            R.id.list_item_jumps_altitude,
            R.id.list_item_jumps_description
        };

        private String mDateFormat;

        public JumpsListAdapter(Context context) {
            super(context, R.layout.list_item_jumps, null, JumpsQuery.PROJECTION, TO, 0);
            mDateFormat = context.getString(R.string.text_format_list_jumps_date);
        }

        @Override
        public void setViewText(TextView v, String text) {
            switch (v.getId()) {
            case R.id.list_item_jumps_date: {
                v.setText(DateFormat.format(mDateFormat, Long.valueOf(text)));
                break;
            }
            case R.id.list_item_jumps_altitude: {
                v.setText(UIUtils.formatAltitude(v.getContext(),
                        UIUtils.roundAltitude(Integer.valueOf(text))));
                break;
            }
            default:
                super.setViewText(v, text);
            }
        }

    }


    // queries

    private interface JumpsQuery {

        String[] PROJECTION = {
                LogbookContract.Jumps.JUMP_NUMBER,
                LogbookContract.Jumps.JUMP_DATE,
                LogbookContract.Jumps.PLACE_NAME,
                LogbookContract.Jumps.AIRCRAFT_NAME,
                LogbookContract.Jumps.JUMP_ALTITUDE,
                LogbookContract.Jumps.JUMP_DESCRIPTION,
                LogbookContract.Jumps._ID
        };

        int NUMBER = 0;
        int DATE = 1;
        int ALTITUDE = 4;

    }

}
