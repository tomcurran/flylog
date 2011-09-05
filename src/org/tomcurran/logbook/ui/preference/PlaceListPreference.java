package org.tomcurran.logbook.ui.preference;

import org.tomcurran.logbook.provider.LogbookContract.Places;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class PlaceListPreference extends ListPreference {

    public static final String DEFAULT = "0";
    private static final String[] PROJECTION = {
        Places.PLACE_NAME,
        Places._ID
    };

    public PlaceListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultValue(DEFAULT);
        setData(context);
    }

    public PlaceListPreference(Context context) {
        super(context, null);
    }

    public void setData(Context context) {
        Activity activity = (Activity) context;
        Cursor data = activity.getContentResolver().query(
                Places.CONTENT_URI,
                PROJECTION,
                null,
                null,
                Places.DEFAULT_SORT
        );
        activity.startManagingCursor(data);
        
        int count = data.getCount();
        CharSequence[] entries = new CharSequence[count];
        CharSequence[] entryValues = new CharSequence[count];

        if (data.moveToFirst()) {
            int nameColumn = data.getColumnIndexOrThrow(Places.PLACE_NAME);
            int idColumn = data.getColumnIndexOrThrow(Places._ID);
            for(int i = 0; i < count; i++) {
                entries[i] = data.getString(nameColumn);
                entryValues[i] = data.getString(idColumn);
                data.moveToNext();
            }
        }

        setEntries(entries);
        setEntryValues(entryValues);
    }

}
