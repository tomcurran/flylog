package org.tomcurran.logbook.ui.fragments;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.provider.LogbookContract;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AircraftDialogFragment extends BaseDialogFragment {

	public static final String TAG = "aircraft_dialog_fragment";

	public static AircraftDialogFragment newInstance() {
        return AircraftDialogFragment.newInstance(0, "");
    }

	public static AircraftDialogFragment newInstance(long aircraftId, String aircraftName) {
		AircraftDialogFragment frag = new AircraftDialogFragment();
        Bundle args = new Bundle();
        args.putLong(LogbookContract.Aircrafts._ID, aircraftId);
        args.putString(LogbookContract.Aircrafts.AIRCRAFT_NAME, aircraftName);
        frag.setArguments(args);
        return frag;
    }

	public AircraftDialogFragment() {
		super(LogbookContract.Aircrafts.CONTENT_URI);
	}

	@Override
	public void setupView(Builder builder, View view) {
		if (mState == BaseDialogFragment.STATE_INSERT) {
			builder.setTitle(R.string.dialog_title_aircraft_add);
		} else if (mState == BaseDialogFragment.STATE_EDIT) {
			builder.setTitle(R.string.dialog_title_aircraft_edit);
			((EditText) view.findViewById(R.id.dialog_text)).setText(
					getArguments().getString(LogbookContract.Aircrafts.AIRCRAFT_NAME));
		}
	}

	protected ContentValues getInputValues(DialogInterface dialog) {
		ContentValues values = new ContentValues();
		values.put(LogbookContract.Aircrafts.AIRCRAFT_NAME,
				((EditText)((AlertDialog)dialog).findViewById(R.id.dialog_text)).getText().toString());
		return values;
	}

}
