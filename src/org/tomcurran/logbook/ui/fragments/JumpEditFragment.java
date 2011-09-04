package org.tomcurran.logbook.ui.fragments;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.provider.LogbookContract;
import org.tomcurran.logbook.ui.BaseActivity;
import org.tomcurran.logbook.ui.HomeActivity;
import org.tomcurran.logbook.ui.PreferencesActivity;
import org.tomcurran.logbook.ui.widget.AddSpinner;
import org.tomcurran.logbook.util.DbAdapter;
import org.tomcurran.logbook.util.UIUtils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class JumpEditFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_PLACES = 0;
    private static final int LOADER_AIRCRAFTS = 1;
    private static final int LOADER_EQUIPMENT = 2;

    private Long mRowId;
    private Long mPlaceId;
    private Long mAircraftId;
    private Long mEquipmentId;
    private TextView mJumpNumText;
    private DatePicker mDatePicker;
    private AddSpinner mPlaceSpinner;
    private AddSpinner mAircraftSpinner;
    private AddSpinner mEquipmentSpinner;
    private TextView mAltitudeText;
    private TextView mDelayText;
    private TextView mDescriptionText;
    private Time time;
    private OnDateChangedListener mDateChangedListener = new OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                time.set(dayOfMonth, monthOfYear, year);
            }
    };
    private BaseDialogFragment.OnSuccessListener mPlaceOnSuccessListener = new BaseDialogFragment.OnSuccessListener() {
		@Override
		public void onSuccess(Long id) {
			mPlaceId = id;
			getLoaderManager().restartLoader(LOADER_PLACES, null, JumpEditFragment.this);
		}
	};
    private BaseDialogFragment.OnSuccessListener mAircraftOnSuccessListener = new BaseDialogFragment.OnSuccessListener() {
		@Override
		public void onSuccess(Long id) {
			mAircraftId = id;
			getLoaderManager().restartLoader(LOADER_AIRCRAFTS, null, JumpEditFragment.this);
		}
	};
    private BaseDialogFragment.OnSuccessListener mEquipmentOnSuccessListener = new BaseDialogFragment.OnSuccessListener() {
		@Override
		public void onSuccess(Long id) {
			mEquipmentId = id;
			getLoaderManager().restartLoader(LOADER_EQUIPMENT, null, JumpEditFragment.this);
		}
	};
    private AddSpinner.OnAddClickListener mOnAddListener = new AddSpinner.OnAddClickListener() {
		@Override
		public void onAddClick(Spinner spinner) {
			BaseDialogFragment dialog = null;
			switch (spinner.getId()) {
			case R.id.spinner_edit_jump_place:
				dialog = PlaceDialogFragment.newInstance(0);
				dialog.setOnSuccessListener(mPlaceOnSuccessListener);
				dialog.show(getSupportFragmentManager(), PlaceDialogFragment.TAG);
				break;
			case R.id.spinner_edit_jump_aircraft:
				dialog = AircraftDialogFragment.newInstance(0);
				dialog.setOnSuccessListener(mAircraftOnSuccessListener);
				dialog.show(getSupportFragmentManager(), AircraftDialogFragment.TAG);
				break;
			case R.id.spinner_edit_jump_equipment:
				dialog = EquipmentDialogFragment.newInstance(0);
				dialog.setOnSuccessListener(mEquipmentOnSuccessListener);
				dialog.show(getSupportFragmentManager(), EquipmentDialogFragment.TAG);
				break;
			}
		}
	};


    // life cycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.fragment_edit_jump, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = getActivity();
        ActionBar ab = activity.getSupportActionBar();

        ab.setTitle(R.string.title_create_jump);
        ab.setDisplayHomeAsUpEnabled(true);

        mJumpNumText = (TextView) activity.findViewById(R.id.text_edit_jump_number);
        mDatePicker = (DatePicker) activity.findViewById(R.id.date_edit_jump_date);
        mPlaceSpinner = (AddSpinner) activity.findViewById(R.id.spinner_edit_jump_place);
        mAircraftSpinner = (AddSpinner) activity.findViewById(R.id.spinner_edit_jump_aircraft);
        mEquipmentSpinner = (AddSpinner) activity.findViewById(R.id.spinner_edit_jump_equipment);
        mAltitudeText = (TextView) activity.findViewById(R.id.text_edit_jump_altitude);
        mDelayText = (TextView) activity.findViewById(R.id.text_edit_jump_delay);
        mDescriptionText = (TextView) activity.findViewById(R.id.text_edit_jump_description);

        setupAddSpinner(
        		mPlaceSpinner,
        		R.string.prompt_edit_jump_places,
        		new BaseAdapter(activity, PlacesQuery.PROJECTION),
        		LOADER_PLACES
        );
        
        setupAddSpinner(
        		mAircraftSpinner,
        		R.string.prompt_edit_jump_aircrafts,
        		new BaseAdapter(activity, AircraftsQuery.PROJECTION),
        		LOADER_AIRCRAFTS
        );
        
        setupAddSpinner(
        		mEquipmentSpinner,
        		R.string.prompt_edit_jump_equipment,
        		new EquipmentAdapter(activity),
        		LOADER_EQUIPMENT
        );

        time = new Time();

        if (savedInstanceState == null) {
        	Bundle args = getArguments();
    		long jumpId = args.getLong(LogbookContract.Jumps._ID, 0);
    		mRowId = jumpId != 0 ? jumpId : null;
        } else {
        	mRowId = (Long) savedInstanceState.getSerializable(LogbookContract.Jumps._ID);
        }

        populateFields();
    }

    @Override
    public void onResume() {
    	super.onResume();
        populateFields();
    }

    @Override
    public void onPause() {
    	super.onPause();
        saveState();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(LogbookContract.Jumps._ID, mRowId);
    }


    // options menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	inflater.inflate(R.menu.options_menu_edit_jump, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case android.R.id.home:
			((BaseActivity)getActivity()).goUp(HomeActivity.class);
			return true;
		case R.id.options_menu_edit_jump_delete:
	        FragmentActivity activity = getActivity();
	        saveState();
	        activity.getContentResolver().delete(LogbookContract.Jumps.buildJumpUri(mRowId), null, null);
            activity.finish();
			return false;
		default:
	    	return super.onOptionsItemSelected(item);
		}
    }


    // helpers

    public void populateFields() {
        FragmentActivity activity = getActivity();
    	if (mRowId != null) {
            Cursor jump = activity.getContentResolver().query(
            		LogbookContract.Jumps.buildJumpUri(mRowId),
                    JumpsQuery.PROJECTION,
                    null,
                    null,
                    LogbookContract.Jumps.DEFAULT_SORT
            );
            if (jump.moveToFirst()) {
                activity.getSupportActionBar().setTitle(getString(R.string.title_edit_jump, jump.getInt(JumpsQuery.NUMBER)));
                mJumpNumText.setText(jump.getString(JumpsQuery.NUMBER));
                time.set(jump.getLong(JumpsQuery.DATE));
                mDatePicker.init(time.year, time.month, time.monthDay, mDateChangedListener);
                mPlaceId = jump.getLong(JumpsQuery.PLACE_ID);
                mAircraftId = jump.getLong(JumpsQuery.AIRCRAFT_ID);
                mEquipmentId = jump.getLong(JumpsQuery.EQUIPMENT_ID);                
                mAltitudeText.setText(jump.getString(JumpsQuery.ALTITUDE));
                mDelayText.setText(jump.getString(JumpsQuery.DELAY));
                mDescriptionText.setText(jump.getString(JumpsQuery.DESCRIPTION));
            }
        } else {
        	activity.invalidateOptionsMenu();
        	
        	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        	time.setToNow();
            
        	mJumpNumText.setText(String.valueOf(DbAdapter.getHighestJumpNumber(activity) + 1));
            mDatePicker.init(time.year, time.month, time.monthDay, mDateChangedListener);
            String placeId = prefs.getString(PreferencesActivity.JUMP_PLACE, null);
            mPlaceId = placeId == null ? null : Long.valueOf(placeId);
            String aircraftId = prefs.getString(PreferencesActivity.JUMP_AIRCRAFT, null);
            mAircraftId = aircraftId == null ? null : Long.valueOf(aircraftId);
            String equipmentId = prefs.getString(PreferencesActivity.JUMP_EQUIPMENT, null);
            mEquipmentId = equipmentId == null ? null : Long.valueOf(equipmentId);
            mDelayText.setText(prefs.getString(PreferencesActivity.JUMP_DELAY, ""));
            mAltitudeText.setText(prefs.getString(PreferencesActivity.JUMP_ALTITUDE, ""));
        }
	}

    private void saveState() {
        ContentResolver resolver = getActivity().getContentResolver();
        ContentValues values = new ContentValues();

        values.put(LogbookContract.Jumps.JUMP_NUMBER, UIUtils.parseTextViewInt(mJumpNumText));
        values.put(LogbookContract.Jumps.JUMP_DATE, time.toMillis(false));
        values.put(LogbookContract.Jumps.PLACE_ID, mPlaceId);
        values.put(LogbookContract.Jumps.AIRCRAFT_ID, mAircraftId);
        values.put(LogbookContract.Jumps.EQUIPMENT_ID, mEquipmentId);
        values.put(LogbookContract.Jumps.JUMP_ALTITUDE, UIUtils.parseTextViewInt(mAltitudeText));
        values.put(LogbookContract.Jumps.JUMP_DELAY, UIUtils.parseTextViewInt(mDelayText));
        values.put(LogbookContract.Jumps.JUMP_DESCRIPTION, mDescriptionText.getText().toString());
        
        if (mRowId == null) {
            Uri jump = resolver.insert(LogbookContract.Jumps.CONTENT_URI, values);
            long id = Long.valueOf(LogbookContract.Jumps.getJumpId(jump));
            if (id > 0) {
                mRowId = id;
            }
        } else {
            resolver.update(
            		LogbookContract.Jumps.buildJumpUri(mRowId),
                    values,
                    null,
                    null
            );
        }
    }

    public void setupAddSpinner(AddSpinner spinner, int promtRes, SimpleCursorAdapter adapter, final int loader) {
    	spinner.setPromptId(promtRes);
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(new BaseOnItemSelectedListener());
    	spinner.setOnAddListener(mOnAddListener);
        getLoaderManager().initLoader(loader, null, this);
    }


    // adapters
    
    public static class BaseAdapter extends SimpleCursorAdapter {

        private final static int[] TO = new int[] {
            android.R.id.text1
        };

        public BaseAdapter(Context context, final String[] projection) {
            super(context, android.R.layout.simple_spinner_item, null, projection, BaseAdapter.TO, 0);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

    }

    public static class EquipmentAdapter extends BaseAdapter {
        
        public EquipmentAdapter(Context context) {
            super(context, EquipmentQuery.PROJECTION);
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


    // listeners

    public class BaseOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            switch (parent.getId()) {
            case R.id.spinner_edit_jump_place:
                mPlaceId = id;
                break;
            case R.id.spinner_edit_jump_aircraft:
                mAircraftId = id;
                break;
            case R.id.spinner_edit_jump_equipment:
                mEquipmentId = id;
                break;
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {}
    }


    // loaders

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
        case LOADER_PLACES:
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Places.CONTENT_URI,
                    PlacesQuery.PROJECTION,
                    null,
                    null,
                    LogbookContract.Places.DEFAULT_SORT
            );
        case LOADER_AIRCRAFTS:
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Aircrafts.CONTENT_URI,
                    AircraftsQuery.PROJECTION,
                    null,
                    null,
                    LogbookContract.Aircrafts.DEFAULT_SORT
            );
        case LOADER_EQUIPMENT:
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Equipment.CONTENT_URI,
                    EquipmentQuery.PROJECTION,
                    null,
                    null,
                    LogbookContract.Equipment.DEFAULT_SORT
            );
        default:
            return null;
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
        case LOADER_PLACES:
        	AddSpinner placeSpinner = (AddSpinner)mPlaceSpinner;
        	((SimpleCursorAdapter)placeSpinner.getAdapter()).swapCursor(data);
        	placeSpinner.setSelectionDbRow(mPlaceId, LogbookContract.Places._ID);
            break;
        case LOADER_AIRCRAFTS:
        	AddSpinner aircraftSpinner = (AddSpinner)mAircraftSpinner;
        	((SimpleCursorAdapter)aircraftSpinner.getAdapter()).swapCursor(data);
        	aircraftSpinner.setSelectionDbRow(mAircraftId, LogbookContract.Aircrafts._ID);
            break;
        case LOADER_EQUIPMENT:
        	AddSpinner equipmentSpinner = (AddSpinner)mEquipmentSpinner;
        	((SimpleCursorAdapter)equipmentSpinner.getAdapter()).swapCursor(data);
        	equipmentSpinner.setSelectionDbRow(mEquipmentId, LogbookContract.Equipment._ID);
            break;
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
        case LOADER_PLACES:
        	((SimpleCursorAdapter)mPlaceSpinner.getAdapter()).swapCursor(null);
            break;
        case LOADER_AIRCRAFTS:
        	((SimpleCursorAdapter)mAircraftSpinner.getAdapter()).swapCursor(null);
            break;
        case LOADER_EQUIPMENT:
        	((SimpleCursorAdapter)mEquipmentSpinner.getAdapter()).swapCursor(null);
            break;
        }
    }


    // queries

    private interface JumpsQuery {

		String[] PROJECTION = {
				LogbookContract.Jumps.JUMP_NUMBER,
				LogbookContract.Jumps.JUMP_DATE,
				LogbookContract.Jumps.PLACE_ID,
				LogbookContract.Jumps.AIRCRAFT_ID,
				LogbookContract.Jumps.EQUIPMENT_ID,
				LogbookContract.Jumps.JUMP_ALTITUDE,
				LogbookContract.Jumps.JUMP_DELAY,
				LogbookContract.Jumps.JUMP_DESCRIPTION,
				LogbookContract.Jumps._ID
		};

		int NUMBER = 0;
		int DATE = 1;
		int PLACE_ID = 2;
		int AIRCRAFT_ID = 3;
		int EQUIPMENT_ID = 4;
		int ALTITUDE = 5;
		int DELAY = 6;
		int DESCRIPTION = 7;

	}

    private interface PlacesQuery {

    	String[] PROJECTION = {
    			LogbookContract.Places.PLACE_NAME,
    			LogbookContract.Places._ID
    	};

    }

    private interface AircraftsQuery {

    	String[] PROJECTION = {
    			LogbookContract.Aircrafts.AIRCRAFT_NAME,
    			LogbookContract.Aircrafts._ID
    	};

    }

    private interface EquipmentQuery {

    	String[] PROJECTION = {
    			LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME,
    			LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE,
    			LogbookContract.Equipment._ID
    	};

    	int CANOPY_NAME = 0;
    	int CANOPY_SIZE = 1;
    }

}
