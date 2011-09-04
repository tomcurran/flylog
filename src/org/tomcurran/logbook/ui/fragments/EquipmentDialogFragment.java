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

public class EquipmentDialogFragment extends BaseDialogFragment {

	public static final String TAG = "equipment_dialog_fragment";

	public static EquipmentDialogFragment newInstance() {
        return EquipmentDialogFragment.newInstance(0);
    }

	public static EquipmentDialogFragment newInstance(long equipmentId) {
		EquipmentDialogFragment frag = new EquipmentDialogFragment();
        Bundle args = new Bundle();
        args.putLong(BaseColumns._ID, equipmentId);
        frag.setArguments(args);
        return frag;
    }

	public EquipmentDialogFragment() {
		super(R.layout.fragment_dialog_equipment);
	}

	@Override
	public void setupView(Builder builder, View view) {
		if (mRowId == null) {
			builder.setTitle(R.string.dialog_title_equipment_add);
			return;
		}
		
		Cursor equipment = getActivity().getContentResolver().query(
				LogbookContract.Equipment.buildEquipmentUri(mRowId),
				EquipmentQuery.PROJECTION,
				null,
				null,
				LogbookContract.Equipment.DEFAULT_SORT
		);
		if (equipment.moveToFirst()) {
			builder.setTitle(R.string.dialog_title_equipment_edit);
			((EditText) view.findViewById(R.id.dialog_text_equipment_name)).setText(
					equipment.getString(EquipmentQuery.CANOPY_NAME));
			((EditText) view.findViewById(R.id.dialog_text_equipment_size)).setText(
					equipment.getString(EquipmentQuery.CANOPY_SIZE));
		}
	}

	@Override
	public void onPositiveButtonClick(DialogInterface dialog) {
		String equipmentName = ((EditText)((AlertDialog)dialog).findViewById(R.id.dialog_text_equipment_name)).getText().toString();
		String equipmentSize = ((EditText)((AlertDialog)dialog).findViewById(R.id.dialog_text_equipment_size)).getText().toString();
        if (!TextUtils.isEmpty(equipmentName) && !TextUtils.isEmpty(equipmentSize)) {
        	ContentResolver resolver = getActivity().getContentResolver();
        	ContentValues values = new ContentValues();
        	values.put(LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME, equipmentName);
        	values.put(LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE, Integer.valueOf(equipmentSize));
        	if (mRowId != null) {
        		resolver.update(LogbookContract.Equipment.buildEquipmentUri(mRowId), values, null, null);
        	} else {
        		Uri equipmentUri = resolver.insert(LogbookContract.Equipment.CONTENT_URI, values);
        		mRowId = Long.valueOf(LogbookContract.Equipment.getEquipmentId(equipmentUri));
        	}
        	dispatchOnSuccessListener();
        }
	}

	@Override
	public void onNeutralButtonClick(DialogInterface dialog) {
		if (mRowId != null) {
			getActivity().getContentResolver().delete(LogbookContract.Equipment.buildEquipmentUri(mRowId), null, null);
			dispatchOnSuccessListener();
    	}
	}
	private interface EquipmentQuery {

		String[] PROJECTION = {
				LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME,
				LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE
		};

		int CANOPY_NAME = 0;
		int CANOPY_SIZE = 1;
	}
}
