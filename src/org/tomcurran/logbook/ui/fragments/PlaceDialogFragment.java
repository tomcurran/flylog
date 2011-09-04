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

public class PlaceDialogFragment extends BaseDialogFragment {

	public static final String TAG = "place_dialog_fragment";

	public static PlaceDialogFragment newInstance() {
        return PlaceDialogFragment.newInstance(0);
    }

	public static PlaceDialogFragment newInstance(long placeId) {
		PlaceDialogFragment frag = new PlaceDialogFragment();
        Bundle args = new Bundle();
        args.putLong(BaseColumns._ID, placeId);
        frag.setArguments(args);
        return frag;
    }

	@Override
	public void setupView(Builder builder, View view) {
		if (mRowId == null) {
			builder.setTitle(R.string.dialog_title_place_add);
			return;
		}
		
		Cursor place = getActivity().getContentResolver().query(
				LogbookContract.Places.buildPlaceUri(mRowId),
				PlacesQuery.PROJECTION,
				null,
				null,
				LogbookContract.Places.DEFAULT_SORT
		);
		if (place.moveToFirst()) {
			builder.setTitle(R.string.dialog_title_place_edit);
			((EditText) view.findViewById(R.id.dialog_text)).setText(
					place.getString(PlacesQuery.PLACE_NAME));
		}
	}

	@Override
	public void onPositiveButtonClick(DialogInterface dialog) {
		String placeName = ((EditText)((AlertDialog)dialog).findViewById(R.id.dialog_text)).getText().toString();
        if (!TextUtils.isEmpty(placeName)) {
        	ContentResolver resolver = getActivity().getContentResolver();
        	ContentValues values = new ContentValues();
        	values.put(LogbookContract.Places.PLACE_NAME, placeName);
        	if (mRowId != null) {
        		resolver.update(LogbookContract.Places.buildPlaceUri(mRowId), values, null, null);
        	} else {
        		Uri placeUri = resolver.insert(LogbookContract.Places.CONTENT_URI, values);
        		mRowId = Long.valueOf(LogbookContract.Places.getPlaceId(placeUri));
        	}
        	dispatchOnSuccessListener();
        }
	}

	@Override
	public void onNeutralButtonClick(DialogInterface dialog) {
		if (mRowId != null) {
			getActivity().getContentResolver().delete(LogbookContract.Places.buildPlaceUri(mRowId), null, null);
			dispatchOnSuccessListener();
    	}
	}

	private interface PlacesQuery {

		String[] PROJECTION = {
				LogbookContract.Places.PLACE_NAME
		};

		int PLACE_NAME = 0;

	}

}
