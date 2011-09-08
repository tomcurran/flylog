package org.tomcurran.logbook.ui.preference;

import org.tomcurran.logbook.util.NotifyingAsyncQueryHandler;

import android.content.Context;
import android.database.Cursor;
import android.preference.ListPreference;
import android.provider.BaseColumns;
import android.util.AttributeSet;


public abstract class DatabaseListPreference extends ListPreference implements NotifyingAsyncQueryHandler.AsyncQueryListener {

    protected static final String DEFAULT = "0";
    private NotifyingAsyncQueryHandler mHandler;

    public DatabaseListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultValue(DEFAULT);
        mHandler = new NotifyingAsyncQueryHandler(context.getContentResolver());
        mHandler.setQueryListener(this);
        startQuery(mHandler);
    }

    public DatabaseListPreference(Context context) {
        super(context, null);
    }

    @Override
    public void onQueryComplete(Cursor data) {
        int count = data.getCount();
        CharSequence[] entries = new CharSequence[count];
        CharSequence[] entryValues = new CharSequence[count];

        if (data.moveToFirst()) {
            for(int i = 0; i < count; i++) {
                entries[i] = getEntryData(data);
                entryValues[i] = getEntryValueData(data);
                data.moveToNext();
            }
        }

        setEntries(entries);
        setEntryValues(entryValues);
        data.close();
    }

    public CharSequence getEntryValueData(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(BaseColumns._ID));
    }

    public abstract void startQuery(NotifyingAsyncQueryHandler handler);
    public abstract CharSequence getEntryData(Cursor cursor);
}
