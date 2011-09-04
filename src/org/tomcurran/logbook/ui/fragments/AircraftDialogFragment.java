package org.tomcurran.logbook.ui.fragments;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.provider.LogbookContract;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class AircraftDialogFragment extends BaseDialogFragment {

	public static final String TAG = "aircraft_dialog_fragment";

	public static AircraftDialogFragment newInstance() {
        return AircraftDialogFragment.newInstance(0);
    }

	public static AircraftDialogFragment newInstance(long aircraftId) {
		AircraftDialogFragment frag = new AircraftDialogFragment();
        Bundle args = new Bundle();
        args.putLong(BaseColumns._ID, aircraftId);
        frag.setArguments(args);
        return frag;
    }

	@Override
	public void setupView(Builder builder, View view) {
		if (mRowId == null) {
			builder.setTitle(R.string.dialog_title_aircraft_add);
			return;
		}
		
		Cursor aircraft = getActivity().getContentResolver().query(
				LogbookContract.Aircrafts.buildAircraftUri(mRowId),
				AircraftsQuery.PROJECTION,
				null,
				null,
				LogbookContract.Aircrafts.DEFAULT_SORT
		);
		if (aircraft.moveToFirst()) {
			builder.setTitle(R.string.dialog_title_aircraft_edit);
			((EditText) view.findViewById(R.id.dialog_text)).setText(
					aircraft.getString(AircraftsQuery.AIRCRAFT_NAME));
		}
	}

	@Override
	public void onPositiveButtonClick(DialogInterface dialog) {
		String aircraftName = ((EditText)((AlertDialog)dialog).findViewById(R.id.dialog_text)).getText().toString();
        if (!TextUtils.isEmpty(aircraftName)) {
        	ContentResolver resolver = getActivity().getContentResolver();
        	ContentValues values = new ContentValues();
        	values.put(LogbookContract.Aircrafts.AIRCRAFT_NAME, aircraftName);
        	if (mRowId != null) {
        		resolver.update(LogbookContract.Aircrafts.buildAircraftUri(mRowId), values, null, null);
        	} else {
        		Uri aircraftUri = resolver.insert(LogbookContract.Aircrafts.CONTENT_URI, values);
        		mRowId = Long.valueOf(LogbookContract.Aircrafts.getAircraftId(aircraftUri));
        	}
        	dispatchOnSuccessListener();
        }
	}

	@Override
	public void onNeutralButtonClick(DialogInterface dialog) {
		if (mRowId != null) {
			getActivity().getContentResolver().delete(LogbookContract.Aircrafts.buildAircraftUri(mRowId), null, null);
			dispatchOnSuccessListener();
    	}
	}
	private interface AircraftsQuery {

		String[] PROJECTION = {
				LogbookContract.Aircrafts.AIRCRAFT_NAME
		};

		int AIRCRAFT_NAME = 0;

	}
}
