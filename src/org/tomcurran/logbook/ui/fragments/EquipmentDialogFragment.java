package org.tomcurran.logbook.ui.fragments;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.provider.LogbookContract;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class EquipmentDialogFragment extends BaseDialogFragment {

	public static final String TAG = "equipment_dialog_fragment";

	public static EquipmentDialogFragment newInstance() {
        return EquipmentDialogFragment.newInstance(0, "", 0);
    }

	public static EquipmentDialogFragment newInstance(long equipmentId, String canopyName, int canopySize) {
		EquipmentDialogFragment frag = new EquipmentDialogFragment();
        Bundle args = new Bundle();
        args.putLong(LogbookContract.Equipment._ID, equipmentId);
        args.putString(LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME, canopyName);
        args.putInt(LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE, canopySize);
        frag.setArguments(args);
        return frag;
    }

	public EquipmentDialogFragment() {
		super(LogbookContract.Equipment.CONTENT_URI, R.layout.fragment_dialog_equipment);
	}

	@Override
	public void setupView(Builder builder, View view) {
    	if (mState == STATE_INSERT) {
     		builder.setTitle(R.string.dialog_title_equipment_add);
     	} else if (mState == STATE_EDIT) {
    		builder.setTitle(R.string.dialog_title_equipment_edit);
			((EditText) view.findViewById(R.id.dialog_text_equipment_name)).setText(
					getArguments().getString(LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME));
			((EditText) view.findViewById(R.id.dialog_text_equipment_size)).setText(
					String.valueOf(getArguments().getInt(LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE)));
    	}
	}

	protected ContentValues getInputValues(DialogInterface dialog) {
		AlertDialog alertDialog = (AlertDialog)dialog;
		ContentValues values = new ContentValues();
    	values.put(LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME, 
    			((EditText)alertDialog.findViewById(R.id.dialog_text_equipment_name)).getText().toString());
    	values.put(LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE,
    			Integer.valueOf(((EditText)alertDialog.findViewById(R.id.dialog_text_equipment_size)).getText().toString()));
    	return values;
	}

	@Override
	protected boolean validInputValues(DialogInterface dialog) {
		AlertDialog alertDialog = (AlertDialog)dialog;
		if (!TextUtils.isEmpty(((EditText)alertDialog.findViewById(R.id.dialog_text_equipment_name)).getText().toString())
				&& !TextUtils.isEmpty(((EditText)alertDialog.findViewById(R.id.dialog_text_equipment_size)).getText().toString())) {
			return true;
		} else {
			return false;
		}
	}

}
