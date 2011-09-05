package org.tomcurran.logbook.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class LogbookContract {

    interface PlacesColumns {
        String PLACE_NAME = "place_name";
    }
    
    interface AircraftsColumns {
        String AIRCRAFT_NAME = "aircraft_name";
    }
    
    interface EquipmentColumns {
        String EQUIPMENT_CANOPY_NAME = "equipment_canopy_name";
        String EQUIPMENT_CANOPY_SIZE = "equipment_canopy_size";
    }
    
    interface JumpsColumns {
        String JUMP_NUMBER = "jump_number";
        String JUMP_DATE = "jump_date";
        String JUMP_ALTITUDE = "jump_altitude";
        String JUMP_DELAY = "jump_delay";
        String JUMP_DESCRIPTION = "jump_description";
    }

    public static final String CONTENT_AUTHORITY = "org.tomcurran.providers.logbook";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_PLACES = "places";
    private static final String PATH_AIRCRAFTS = "aircrafts";
    private static final String PATH_EQUIPMENT = "equipment";
    private static final String PATH_JUMPS = "jumps";
    private static final String PATH_STATS = "stats";
    private static final String PATH_LAST_MONTHS = "months";

    public static class Places implements PlacesColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLACES).build();
        public static final Uri CONTENT_STATS_URI = CONTENT_URI.buildUpon().appendPath(PATH_STATS).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.logbook.place";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.logbook.place";
        public static final String DEFAULT_SORT = Places.PLACE_NAME + " COLLATE NOCASE ASC";

        public static Uri buildPlaceUri(String placeId) {
            return CONTENT_URI.buildUpon().appendPath(placeId).build();
        }
        
        public static Uri buildPlaceUri(long placeId) {
            return buildPlaceUri(String.valueOf(placeId));
        }

        public static String getPlaceId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static class Aircrafts implements AircraftsColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon() .appendPath(PATH_AIRCRAFTS).build();
        public static final Uri CONTENT_STATS_URI = CONTENT_URI.buildUpon().appendPath(PATH_STATS).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.logbook.aircraft";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.logbook.aircraft";
        public static final String DEFAULT_SORT = Aircrafts.AIRCRAFT_NAME + " COLLATE NOCASE ASC";

        public static Uri buildAircraftUri(String aircraftId) {
            return CONTENT_URI.buildUpon().appendPath(aircraftId).build();
        }
        
        public static Uri buildAircraftUri(long aircraftId) {
            return buildAircraftUri(String.valueOf(aircraftId));
        }

        public static String getAircraftId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static class Equipment implements EquipmentColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon() .appendPath(PATH_EQUIPMENT).build();
        public static final Uri CONTENT_STATS_URI = CONTENT_URI.buildUpon().appendPath(PATH_STATS).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.logbook.equipment";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.logbook.equipment";
        public static final String DEFAULT_SORT = Equipment.EQUIPMENT_CANOPY_SIZE + " ASC, " + Equipment.EQUIPMENT_CANOPY_NAME + " ASC";

        public static Uri buildEquipmentUri(String equipmentId) {
            return CONTENT_URI.buildUpon().appendPath(equipmentId).build();
        }

        public static Uri buildEquipmentUri(long equipmentId) {
            return buildEquipmentUri(String.valueOf(equipmentId));
        }

        public static String getEquipmentId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static class Jumps implements JumpsColumns, PlacesColumns, AircraftsColumns, EquipmentColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon() .appendPath(PATH_JUMPS).build();
        public static final Uri CONTENT_LAST_MONTHS_URI = CONTENT_URI.buildUpon().appendPath(PATH_LAST_MONTHS).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.logbook.jump";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.logbook.jump";
        public static final String DEFAULT_SORT = Jumps.JUMP_NUMBER + " DESC, " + Jumps.JUMP_DATE + " DESC";
        
        public static final String PLACE_ID = "place_id";
        public static final String AIRCRAFT_ID = "aircraft_id";
        public static final String EQUIPMENT_ID = "equipment_id";

        public static Uri buildJumpUri(String jumpId) {
            return CONTENT_URI.buildUpon().appendPath(jumpId).build();
        }
        
        public static Uri buildJumpUri(long jumpId) {
            return buildJumpUri(String.valueOf(jumpId));
        }

        public static Uri buildLastMonthsUri(String months) {
            return CONTENT_LAST_MONTHS_URI.buildUpon().appendPath(months).build();
        }
        
        public static Uri buildLastMonthsUri(long months) {
            return buildLastMonthsUri(String.valueOf(months));
        }

        public static String getJumpId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getMonths(Uri uri) {
            return uri.getPathSegments().get(2);
        }

    }
    
    private LogbookContract() {
    }
}
