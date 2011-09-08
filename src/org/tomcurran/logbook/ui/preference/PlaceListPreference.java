package org.tomcurran.logbook.ui.preference;

import org.tomcurran.logbook.provider.LogbookContract;
import org.tomcurran.logbook.util.NotifyingAsyncQueryHandler;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;

public class PlaceListPreference extends DatabaseListPreference {

    public PlaceListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaceListPreference(Context context) {
        super(context, null);
    }

    @Override
    public void startQuery(NotifyingAsyncQueryHandler handler) {
        handler.startQuery(
                LogbookContract.Places.CONTENT_URI,
                PlacesQuery.PROJECTION,
                LogbookContract.Places.DEFAULT_SORT
        );
    }

    @Override
    public CharSequence getEntryData(Cursor cursor) {
        return cursor.getString(PlacesQuery.NAME);
    }

    private interface PlacesQuery {

        String[] PROJECTION = {
                LogbookContract.Places.PLACE_NAME,
                LogbookContract.Places._ID
        };

        int NAME = 0;
    }
}