package org.tomcurran.logbook.ui.preference;

import org.tomcurran.logbook.provider.LogbookContract;
import org.tomcurran.logbook.util.NotifyingAsyncQueryHandler;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;

public class EquipmentListPreference extends DatabaseListPreference {

    public EquipmentListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EquipmentListPreference(Context context) {
        super(context, null);
    }

    @Override
    public void startQuery(NotifyingAsyncQueryHandler handler) {
        handler.startQuery(
                LogbookContract.Equipment.CONTENT_URI,
                EquipmentQuery.PROJECTION,
                LogbookContract.Equipment.DEFAULT_SORT
        );
    }

    @Override
    public CharSequence getEntryData(Cursor cursor) {
        return cursor.getString(EquipmentQuery.CANOPY_NAME) + " " + cursor.getString(EquipmentQuery.CANOPY_SIZE);
    }

    private interface EquipmentQuery {

        String[] PROJECTION = {
                LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME,
                LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE,
                LogbookContract.Equipment._ID
        };

        int CANOPY_NAME = 0;
        int CANOPY_SIZE = 1;
    }

}