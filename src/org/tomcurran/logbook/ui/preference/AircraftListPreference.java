package org.tomcurran.logbook.ui.preference;

import org.tomcurran.logbook.provider.LogbookContract;
import org.tomcurran.logbook.util.NotifyingAsyncQueryHandler;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;

public class AircraftListPreference extends DatabaseListPreference {

    public AircraftListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AircraftListPreference(Context context) {
        super(context, null);
    }

    @Override
    public void startQuery(NotifyingAsyncQueryHandler handler) {
        handler.startQuery(
                LogbookContract.Aircrafts.CONTENT_URI,
                AircraftsQuery.PROJECTION,
                LogbookContract.Aircrafts.DEFAULT_SORT
        );
    }

    @Override
    public CharSequence getEntryData(Cursor cursor) {
        return cursor.getString(AircraftsQuery.NAME);
    }

    private interface AircraftsQuery {

        String[] PROJECTION = {
                LogbookContract.Aircrafts.AIRCRAFT_NAME,
                LogbookContract.Aircrafts._ID
        };

        int NAME = 0;
    }

}