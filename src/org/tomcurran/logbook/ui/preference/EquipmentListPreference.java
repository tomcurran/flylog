package org.tomcurran.logbook.ui.preference;

import org.tomcurran.logbook.provider.LogbookContract.Equipment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class EquipmentListPreference extends ListPreference {

	public static final String DEFAULT = "0";
	private static final String[] PROJECTION = {
		Equipment.EQUIPMENT_CANOPY_NAME,
		Equipment.EQUIPMENT_CANOPY_SIZE,
		Equipment._ID
	};

	public EquipmentListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDefaultValue(DEFAULT);
		setData(context);
	}

	public EquipmentListPreference(Context context) {
		super(context, null);
	}

	public void setData(Context context) {
		Activity activity = (Activity) context;
		Cursor data = activity.getContentResolver().query(
				Equipment.CONTENT_URI,
				PROJECTION,
				null,
				null,
				Equipment.DEFAULT_SORT
		);
		activity.startManagingCursor(data);
		
		int count = data.getCount();
		CharSequence[] entries = new CharSequence[count];
		CharSequence[] entryValues = new CharSequence[count];

		if (data.moveToFirst()) {
			int nameColumn = data.getColumnIndexOrThrow(Equipment.EQUIPMENT_CANOPY_NAME);
			int sizeColumn = data.getColumnIndexOrThrow(Equipment.EQUIPMENT_CANOPY_SIZE);
			int idColumn = data.getColumnIndexOrThrow(Equipment._ID);
			for(int i = 0; i < count; i++) {
				entries[i] = data.getString(nameColumn) + " " + data.getString(sizeColumn);
				entryValues[i] = data.getString(idColumn);
				data.moveToNext();
			}
		}

		setEntries(entries);
		setEntryValues(entryValues);
	}

}
