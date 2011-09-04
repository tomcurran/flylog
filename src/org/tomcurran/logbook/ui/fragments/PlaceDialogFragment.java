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

public class PlaceDialogFragment extends BaseDialogFragment {

	public static final String TAG = "place_dialog_fragment";

	public static PlaceDialogFragment newInstance() {
        return PlaceDialogFragment.newInstance(0, "");
    }

	public static PlaceDialogFragment newInstance(long placeId, String placeName) {
		PlaceDialogFragment frag = new PlaceDialogFragment();
        Bundle args = new Bundle();
        args.putLong(LogbookContract.Places._ID, placeId);
        args.putString(LogbookContract.Places.PLACE_NAME, placeName);
        frag.setArguments(args);
        return frag;
    }

	public PlaceDialogFragment() {
		super(LogbookContract.Places.CONTENT_URI);
	}

	@Override
	public void setupView(Builder builder, View view) {
		if (mState == BaseDialogFragment.STATE_INSERT) {
			builder.setTitle(R.string.dialog_title_place_add);
		} else if (mState == BaseDialogFragment.STATE_EDIT) {
			builder.setTitle(R.string.dialog_title_place_edit);
			((EditText) view.findViewById(R.id.dialog_text)).setText(
					getArguments().getString(LogbookContract.Places.PLACE_NAME));
		}
	}

	protected ContentValues getInputValues(DialogInterface dialog) {
		ContentValues values = new ContentValues();
		values.put(LogbookContract.Places.PLACE_NAME,
				((EditText)((AlertDialog)dialog).findViewById(R.id.dialog_text)).getText().toString());
		return values;
	}

}
