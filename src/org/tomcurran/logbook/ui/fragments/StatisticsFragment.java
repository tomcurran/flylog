package org.tomcurran.logbook.ui.fragments;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.provider.LogbookContract;
import org.tomcurran.logbook.util.DbAdapter;
import org.tomcurran.logbook.util.UIUtils;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatisticsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "statistics_fragment";

    private static final int LOADER_MONTHS6_COUNT  = 0;
    private static final int LOADER_MONTHS12_COUNT = 1;
    private static final int LOADER_MONTHS18_COUNT = 2;
    private static final int LOADER_MONTHS24_COUNT = 3;
    private static final int LOADER_ALTITUDES      = 4;
    private static final int LOADER_DELAYS         = 5;
    private static final int LOADER_PLACES         = 6;
    private static final int LOADER_EQUIPMENT      = 7;
    private static final int LOADER_AIRCRAFTS      = 8;

    private Cursor mMonths6Cursor;
    private Cursor mMonths12Cursor;
    private Cursor mMonths18Cursor;
    private Cursor mMonths24Cursor;
    private Cursor mAltitudesCursor;
    private Cursor mDelaysCursor;
    private Cursor mPlacesCursor;
    private Cursor mEquipmentCursor;
    private Cursor mAircraftsCursor;

    // life cycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_MONTHS6_COUNT,  null, this);
        loaderManager.initLoader(LOADER_MONTHS12_COUNT, null, this);
        loaderManager.initLoader(LOADER_MONTHS18_COUNT, null, this);
        loaderManager.initLoader(LOADER_MONTHS24_COUNT, null, this);
        loaderManager.initLoader(LOADER_ALTITUDES,      null, this);
        loaderManager.initLoader(LOADER_DELAYS,         null, this);
        loaderManager.initLoader(LOADER_PLACES,         null, this);
        loaderManager.initLoader(LOADER_EQUIPMENT,      null, this);
        loaderManager.initLoader(LOADER_AIRCRAFTS,      null, this);
    }


    // loaders

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
        case LOADER_MONTHS6_COUNT:            
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Jumps.buildLastMonthsUri(6),
                    MonthsQuery.PROJECTION,
                    null,
                    null,
                    LogbookContract.Jumps.DEFAULT_SORT
            );
        case LOADER_MONTHS12_COUNT:            
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Jumps.buildLastMonthsUri(12),
                    MonthsQuery.PROJECTION,
                    null,
                    null,
                    LogbookContract.Jumps.DEFAULT_SORT
            );
        case LOADER_MONTHS18_COUNT:            
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Jumps.buildLastMonthsUri(18),
                    MonthsQuery.PROJECTION,
                    null,
                    null,
                    LogbookContract.Jumps.DEFAULT_SORT
            );
        case LOADER_MONTHS24_COUNT:            
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Jumps.buildLastMonthsUri(24),
                    MonthsQuery.PROJECTION,
                    null,
                    null,
                    LogbookContract.Jumps.DEFAULT_SORT
            );
        case LOADER_ALTITUDES:
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Jumps.CONTENT_URI,
                    AltitudesQuery.PROJECTION,
                    AltitudesQuery.SELECTION,
                    DbAdapter.SELECTION_ARG_ZERO,
                    LogbookContract.Jumps.DEFAULT_SORT
            );
        case LOADER_DELAYS:
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Jumps.CONTENT_URI,
                    DelaysQuery.PROJECTION,
                    DelaysQuery.SELECTION,
                    DbAdapter.SELECTION_ARG_ZERO,
                    LogbookContract.Jumps.DEFAULT_SORT
            );
        case LOADER_PLACES:
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Places.CONTENT_STATS_URI,
                    PlacesQuery.PROJECTION,
                    null,
                    null,
                    PlacesQuery.SORT
            );
        case LOADER_EQUIPMENT:
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Equipment.CONTENT_STATS_URI,
                    EquipmentQuery.PROJECTION,
                    null,
                    null,
                    EquipmentQuery.SORT
            );
        case LOADER_AIRCRAFTS:
            return new CursorLoader(
                    getActivity(),
                    LogbookContract.Aircrafts.CONTENT_STATS_URI,
                    AircraftsQuery.PROJECTION,
                    null,
                    null,
                    AircraftsQuery.SORT
            );
        default:
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
        case LOADER_MONTHS6_COUNT:
            mMonths6Cursor = data;
            loadMonths6();
            break;
        case LOADER_MONTHS12_COUNT:
            mMonths12Cursor = data;
            loadMonths12();
            break;
        case LOADER_MONTHS18_COUNT:
            mMonths18Cursor = data;
            loadMonths18();
            break;
        case LOADER_MONTHS24_COUNT:
            mMonths24Cursor = data;
            loadMonths24();
            break;
        case LOADER_ALTITUDES:
            mAltitudesCursor = data;
            loadAltitudes();
            break;
        case LOADER_DELAYS:
            mDelaysCursor = data;
            loadDelays();
            break;
        case LOADER_PLACES:
            mPlacesCursor = data;
            loadPlaces();
            break;
        case LOADER_EQUIPMENT:
            mEquipmentCursor = data;
            loadEquipment();
            break;
        case LOADER_AIRCRAFTS:
            mAircraftsCursor = data;
            loadAircrafts();
            break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
        case LOADER_MONTHS6_COUNT:
            mMonths6Cursor = null;
            break;
        case LOADER_MONTHS12_COUNT:
            mMonths12Cursor = null;
            break;
        case LOADER_MONTHS18_COUNT:
            mMonths18Cursor = null;
            break;
        case LOADER_MONTHS24_COUNT:
            mMonths24Cursor = null;
            break;
        case LOADER_ALTITUDES:
            mAltitudesCursor = null;
            break;
        case LOADER_DELAYS:
            mDelaysCursor = null;
            break;
        case LOADER_PLACES:
            mPlacesCursor = null;
            break;
        case LOADER_EQUIPMENT:
            mEquipmentCursor = null;
            break;
        case LOADER_AIRCRAFTS:
            mAircraftsCursor = null;
            break;
        }
    }

    private void loadMonths6() {
        Cursor data = mMonths6Cursor;
        Activity activity = getActivity();
        int count = 0;
        if (data != null && data.moveToFirst()) {
            count = data.getInt(MonthsQuery.COUNT);
        }
        ((TextView) activity.findViewById(R.id.text_stat_jump_numbers_6))
                .setText(getString(R.string.text_stat_jump_numbers, 6, count));
    }

    private void loadMonths12() {
        Cursor data = mMonths12Cursor;
        Activity activity = getActivity();
        int count = 0;
        if (data != null && data.moveToFirst()) {
            count = data.getInt(MonthsQuery.COUNT);
        }
        ((TextView) activity.findViewById(R.id.text_stat_jump_numbers_12))
                .setText(getString(R.string.text_stat_jump_numbers, 12, count));
    }

    private void loadMonths18() {
        Cursor data = mMonths18Cursor;
        Activity activity = getActivity();
        int count = 0;
        if (data != null && data.moveToFirst()) {
            count = data.getInt(MonthsQuery.COUNT);
        }
        ((TextView) activity.findViewById(R.id.text_stat_jump_numbers_18))
                .setText(getString(R.string.text_stat_jump_numbers, 18, count));
    }

    private void loadMonths24() {
        Cursor data = mMonths24Cursor;
        Activity activity = getActivity();
        int count = 0;
        if (data != null && data.moveToFirst()) {
            count = data.getInt(MonthsQuery.COUNT);
        }
        ((TextView) activity.findViewById(R.id.text_stat_jump_numbers_24))
                .setText(getString(R.string.text_stat_jump_numbers, 24, count));
    }

    private void loadAltitudes() {
        Cursor data = mAltitudesCursor;
        Activity activity = getActivity();
        int highestAltitude = 0;
        int averageAltitude = 0;
        int lowestAltitude = 0;
        if (data != null && data.moveToFirst()) {
            highestAltitude = data.getInt(AltitudesQuery.MAX);
            averageAltitude = data.getInt(AltitudesQuery.AVG);
            lowestAltitude = data.getInt(AltitudesQuery.MIN);
        }
        ((TextView) activity.findViewById(R.id.text_stat_altitude))
                .setText(getString(R.string.text_stat_altitude,
                    UIUtils.formatAltitude(activity, highestAltitude),
                    UIUtils.formatAltitude(activity, averageAltitude),
                    UIUtils.formatAltitude(activity, lowestAltitude)));
    }

    private void loadDelays() {
        Cursor data = mDelaysCursor;
        Activity activity = getActivity();
        int longestDelay = 0;
        int totalDelay = 0;
        if (data != null && data.moveToFirst()) {
            longestDelay = data.getInt(DelaysQuery.MAX);
            totalDelay = data.getInt(DelaysQuery.SUM);
        }
        ((TextView) activity.findViewById(R.id.text_stat_delay))
                .setText(getString(R.string.text_stat_delay,
                    UIUtils.formatDelay(activity, longestDelay),
                    UIUtils.formatDelay(activity, totalDelay)));
    }

    private void loadPlaces() {
        Cursor data = mPlacesCursor;
        TextView placesText = (TextView) getActivity().findViewById(R.id.text_stat_places);
        if (data != null && data.moveToFirst()) {
            placesText.setText("");
            do {
                placesText.append(getString(R.string.text_stat_places,
                        data.getString(PlacesQuery.NAME),
                        data.getInt(PlacesQuery.COUNT)));
            } while (data.moveToNext());
        } else {
            placesText.setText(getString(R.string.text_stat_places_empty));
        }
    }

    private void loadEquipment() {
        Cursor data = mEquipmentCursor;
        TextView equipmentText = (TextView) getActivity().findViewById(R.id.text_stat_equipment);
        if (data != null && data.moveToFirst()) {
            equipmentText.setText("");
            do {
                equipmentText.append(getString(R.string.text_stat_equipment,
                        data.getString(EquipmentQuery.CANOPY_NAME),
                        data.getInt(EquipmentQuery.CANOPY_SIZE),
                        data.getInt(EquipmentQuery.COUNT)));
            } while (data.moveToNext());
        } else {
            equipmentText.setText(getString(R.string.text_stat_equipment_empty));
        }
    }

    private void loadAircrafts() {
        Cursor data = mAircraftsCursor;
        TextView aircraftsText = (TextView) getActivity().findViewById(R.id.text_stat_aircrafts);
        if (data != null && data.moveToFirst()) {
            aircraftsText.setText("");
            do {
                aircraftsText.append(getString(R.string.text_stat_aircrafts,
                        data.getString(AircraftsQuery.NAME),
                        data.getInt(AircraftsQuery.COUNT)));
            } while (data.moveToNext());
        } else {
            aircraftsText.setText(getString(R.string.text_stat_aircrafts_empty));
        }
    }


    // queries

    private interface PlacesQuery {

        String[] PROJECTION = {
                "count(" + LogbookContract.Jumps.JUMP_NUMBER + ")",
                LogbookContract.Places.PLACE_NAME,
                LogbookContract.Places._ID
        };

        String SORT = "count(" + LogbookContract.Jumps.JUMP_NUMBER + ") DESC";

        int COUNT = 0;
        int NAME = 1;

    }

    private interface AircraftsQuery {

        String[] PROJECTION = {
                "count(" + LogbookContract.Jumps.JUMP_NUMBER + ")",
                LogbookContract.Aircrafts.AIRCRAFT_NAME,
                LogbookContract.Aircrafts._ID
        };

        String SORT = "count(" + LogbookContract.Jumps.JUMP_NUMBER + ") DESC";

        int COUNT = 0;
        int NAME = 1;

    }

    private interface EquipmentQuery {

        String[] PROJECTION = {
                "count(" + LogbookContract.Jumps.JUMP_NUMBER + ")",
                LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME,
                LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE,
                LogbookContract.Equipment._ID
        };

        String SORT = "count(" + LogbookContract.Jumps.JUMP_NUMBER + ") DESC";

        int COUNT = 0;
        int CANOPY_NAME = 1;
        int CANOPY_SIZE = 2;
    }

    private interface AltitudesQuery {

        String[] PROJECTION = {
                "max(" + LogbookContract.Jumps.JUMP_ALTITUDE + ")",
                "avg(" + LogbookContract.Jumps.JUMP_ALTITUDE + ")",
                "min(" + LogbookContract.Jumps.JUMP_ALTITUDE + ")"
        };

        String SELECTION = LogbookContract.Jumps.JUMP_ALTITUDE + ">?";

        int MAX = 0;
        int AVG = 1;
        int MIN = 2;
    }

    private interface DelaysQuery {

        String[] PROJECTION = {
                "max(" + LogbookContract.Jumps.JUMP_DELAY + ")",
                "sum(" + LogbookContract.Jumps.JUMP_DELAY + ")"
        };

        String SELECTION = LogbookContract.Jumps.JUMP_DELAY + ">?";

        int MAX = 0;
        int SUM = 1;
    }

    private interface MonthsQuery {

        String[] PROJECTION = {
                "count(*)"
        };

        int COUNT = 0;
    }

}
