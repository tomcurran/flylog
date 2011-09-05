package org.tomcurran.logbook.test;

import org.tomcurran.logbook.provider.LogbookContract;
import org.tomcurran.logbook.provider.LogbookDatabase;
import org.tomcurran.logbook.provider.LogbookProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.text.format.Time;

public class LogbookProviderTest extends ProviderTestCase2<LogbookProvider> {

    private static final Uri INVALID_PLACES_URI = Uri.withAppendedPath(LogbookContract.Places.CONTENT_URI, "invalid");
    private static final Uri INVALID_AIRCRAFTS_URI = Uri.withAppendedPath(LogbookContract.Aircrafts.CONTENT_URI, "invalid");
    private static final Uri INVALID_EQUIPMENT_URI = Uri.withAppendedPath(LogbookContract.Equipment.CONTENT_URI, "invalid");
    private static final Uri INVALID_JUMPS_URI = Uri.withAppendedPath(LogbookContract.Jumps.CONTENT_URI, "invalid");
    private static final PlaceInfo[] TEST_PLACES = {
            new PlaceInfo("place0"),
            new PlaceInfo("place1"),
            new PlaceInfo("place2"),
            new PlaceInfo("place3"),
            new PlaceInfo("place4")
    };
    private static final AircraftInfo[] TEST_AIRCRAFTS = {
            new AircraftInfo("aircraft0"),
            new AircraftInfo("aircraft1"),
            new AircraftInfo("aircraft2"),
            new AircraftInfo("aircraft3"),
            new AircraftInfo("aircraft4")
    };
    private static final EquipmentInfo[] TEST_EQUIPMENT = {
            new EquipmentInfo("equipment0", 280),
            new EquipmentInfo("equipment1", 220),
            new EquipmentInfo("equipment2", 210),
            new EquipmentInfo("equipment3", 190),
            new EquipmentInfo("equipment4", 170)
    };
    private static Time sTime = new Time();
    private static final JumpInfo[] TEST_JUMPS = {
            new JumpInfo(1, 1, 1, 0, sTime.toMillis(false), 2200, 5, "description0"),
            new JumpInfo(2, 2, 2, 1, sTime.toMillis(false), 2200, 6, "description1"),
            new JumpInfo(3, 3, 3, 2, sTime.toMillis(false), 2200, 7, "description2"),
            new JumpInfo(4, 4, 4, 3, sTime.toMillis(false), 2200, 6, "description3"),
            new JumpInfo(5, 5, 5, 4, sTime.toMillis(false), 2200, 5, "description4")
    };

    private ContentResolver mResolver;
    private SQLiteDatabase mDb;
 
    public LogbookProviderTest() {
        super(LogbookProvider.class, LogbookContract.CONTENT_AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        mResolver = getMockContentResolver();
        mDb = new LogbookDatabase(getMockContext()).getWritableDatabase();
        sTime.set(29, 6 - 1, 2011);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private void insertData() {
        mDb.beginTransaction();
        try {
            for (int index = 0; index < TEST_PLACES.length; index++) {
                mDb.insertOrThrow(
                    "places", // TODO don't do this?
                    LogbookContract.Places.PLACE_NAME,
                    TEST_PLACES[index].getContentValues()
                );
            }
            
            for (int index = 0; index < TEST_AIRCRAFTS.length; index++) {
                mDb.insertOrThrow(
                    "aircrafts", // TODO don't do this?
                    LogbookContract.Aircrafts.AIRCRAFT_NAME,
                    TEST_AIRCRAFTS[index].getContentValues()
                );
            }
            
            for (int index = 0; index < TEST_EQUIPMENT.length; index++) {
                mDb.insertOrThrow(
                    "equipment", // TODO don't do this?
                    LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME,
                    TEST_EQUIPMENT[index].getContentValues()
                );
            }
            
            for (int index = 0; index < TEST_JUMPS.length; index++) {
                mDb.insertOrThrow(
                    "jumps", // TODO don't do this?
                    LogbookContract.Jumps.JUMP_DESCRIPTION,
                    TEST_JUMPS[index].getContentValues()
                );
            }

            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }

    }
    
    public void testUriAndGetType() {
        final String uriId = "1";

        // places
        assertEquals(LogbookContract.Places.CONTENT_TYPE, mResolver.getType(LogbookContract.Places.CONTENT_URI));
        assertEquals(LogbookContract.Places.CONTENT_ITEM_TYPE, mResolver.getType(LogbookContract.Places.buildPlaceUri(uriId)));
        mResolver.getType(INVALID_PLACES_URI);

        // aircrafts
        assertEquals(LogbookContract.Aircrafts.CONTENT_TYPE, mResolver.getType(LogbookContract.Aircrafts.CONTENT_URI));
        assertEquals(LogbookContract.Aircrafts.CONTENT_ITEM_TYPE, mResolver.getType(LogbookContract.Aircrafts.buildAircraftUri(uriId)));
        mResolver.getType(INVALID_AIRCRAFTS_URI);

        // equipment
        assertEquals(LogbookContract.Equipment.CONTENT_TYPE, mResolver.getType(LogbookContract.Equipment.CONTENT_URI));
        assertEquals(LogbookContract.Equipment.CONTENT_ITEM_TYPE, mResolver.getType(LogbookContract.Equipment.buildEquipmentUri(uriId)));
        mResolver.getType(INVALID_EQUIPMENT_URI);

        // jumps
        assertEquals(LogbookContract.Jumps.CONTENT_TYPE, mResolver.getType(LogbookContract.Jumps.CONTENT_URI));
        assertEquals(LogbookContract.Jumps.CONTENT_ITEM_TYPE, mResolver.getType(LogbookContract.Jumps.buildJumpUri(uriId)));
        mResolver.getType(INVALID_JUMPS_URI);

    }

    public void testQueriesOnPlacesUri() {
        _testQueriesOnURI(
                LogbookContract.Places.CONTENT_URI,
                new String[] { LogbookContract.Places.PLACE_NAME },
                LogbookContract.Places.PLACE_NAME + "=?",
                LogbookContract.Places.PLACE_NAME + "=? OR " + LogbookContract.Places.PLACE_NAME + "=? OR " + LogbookContract.Places.PLACE_NAME + "=?",
                new String[] { "place0", "place1", "place2" },
                LogbookContract.Places.DEFAULT_SORT,
                TEST_PLACES
        );
    }
    
    public void testQueriesOnPlaceIdUri() {
          final String SELECTION_COLUMNS = LogbookContract.Places.PLACE_NAME + "=?";
          final String[] SELECTION_ARGS = { "place1" };
          final String SORT_ORDER = LogbookContract.Places.DEFAULT_SORT;
          final String[] PLACE_ID_PROJECTION = {
               LogbookContract.Places._ID,
               LogbookContract.Places.PLACE_NAME
          };

          Uri placeIdUri = LogbookContract.Places.buildPlaceUri("1");
          Cursor cursor = mResolver.query(
              placeIdUri,
              null,
              null,
              null,
              null
          );
          assertEquals(0,cursor.getCount());

          insertData();
          cursor = mResolver.query(
              LogbookContract.Places.CONTENT_URI,
              PLACE_ID_PROJECTION, 
              SELECTION_COLUMNS,
              SELECTION_ARGS,
              SORT_ORDER
          );

          assertEquals(1, cursor.getCount());
          assertTrue(cursor.moveToFirst());

          String inputPlaceId = cursor.getString(0);
          placeIdUri = LogbookContract.Places.buildPlaceUri(inputPlaceId);
          cursor = mResolver.query(placeIdUri,
                  PLACE_ID_PROJECTION,
                  SELECTION_COLUMNS,
                  SELECTION_ARGS,
                  SORT_ORDER
          );
          assertEquals(1, cursor.getCount());
          assertTrue(cursor.moveToFirst());
          assertEquals(inputPlaceId, cursor.getString(0));
    }

    public void testPlacesInserts() {
        PlaceInfo place = new PlaceInfo(
                "place100"
        );

        Uri rowUri = mResolver.insert(
                LogbookContract.Places.CONTENT_URI,
                place.getContentValues()
        );

        long placeId = Long.parseLong(LogbookContract.Places.getPlaceId(rowUri));

        Cursor cursor = mResolver.query(
                LogbookContract.Places.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(1, cursor.getCount());
        assertTrue(cursor.moveToFirst());
        assertEquals(place.place_name, cursor.getString(cursor.getColumnIndex(LogbookContract.Places.PLACE_NAME)));

        ContentValues values = place.getContentValues();
        values.put(LogbookContract.Places._ID, placeId);
        try {
            rowUri = mResolver.insert(LogbookContract.Places.CONTENT_URI, values);
            fail("Expected insert failure for existing record but insert succeeded.");
        } catch (Exception e) {
            // succeeded, so do nothing.
        }
    }

    public void testPlacesDeletes() {
        _testDeletes(
                LogbookContract.Places.CONTENT_URI,
                LogbookContract.Places.PLACE_NAME + "=?",
                new String[] { "place0" }
        );
    }
    
    public void testPlacesUpdates() {
        ContentValues values = new ContentValues();
        values.put(LogbookContract.Places.PLACE_NAME, "place99");
        _testUpdates(
                LogbookContract.Places.CONTENT_URI,
                LogbookContract.Places.PLACE_NAME + "=?",
                new String[] { "place1" },
                values
        );
    }

    public void testQueriesOnAircraftsUri() {
        _testQueriesOnURI(
                LogbookContract.Aircrafts.CONTENT_URI,
                new String[] { LogbookContract.Aircrafts.AIRCRAFT_NAME },
                LogbookContract.Aircrafts.AIRCRAFT_NAME + "=?",
                LogbookContract.Aircrafts.AIRCRAFT_NAME + "=? OR " + LogbookContract.Aircrafts.AIRCRAFT_NAME + "=? OR " + LogbookContract.Aircrafts.AIRCRAFT_NAME + "=?",
                new String[] { "aircraft0", "aircraft1", "aircraft2" },
                LogbookContract.Aircrafts.DEFAULT_SORT,
                TEST_AIRCRAFTS
        );
    }
    
    public void testQueriesOnAircraftIdUri() {
          final String SELECTION_COLUMNS = LogbookContract.Aircrafts.AIRCRAFT_NAME + "=?";
          final String[] SELECTION_ARGS = { "aircraft1" };
          final String SORT_ORDER = LogbookContract.Aircrafts.DEFAULT_SORT;
          final String[] AIRCRAFT_ID_PROJECTION = {
                  LogbookContract.Aircrafts._ID,
                  LogbookContract.Aircrafts.AIRCRAFT_NAME
          };

          Uri aircraftIdUri = LogbookContract.Aircrafts.buildAircraftUri("1");
          Cursor cursor = mResolver.query(
              aircraftIdUri,
              null,
              null,
              null,
              null
          );
          assertEquals(0,cursor.getCount());

          insertData();
          cursor = mResolver.query(
                  LogbookContract.Aircrafts.CONTENT_URI,
                  AIRCRAFT_ID_PROJECTION, 
                  SELECTION_COLUMNS,
                  SELECTION_ARGS,
                  SORT_ORDER
          );

          assertEquals(1, cursor.getCount());
          assertTrue(cursor.moveToFirst());

          String inputAircraftId = cursor.getString(0);
          aircraftIdUri = LogbookContract.Aircrafts.buildAircraftUri(inputAircraftId);
          cursor = mResolver.query(aircraftIdUri,
                  AIRCRAFT_ID_PROJECTION,
                  SELECTION_COLUMNS,
                  SELECTION_ARGS,
                  SORT_ORDER
          );
          assertEquals(1, cursor.getCount());
          assertTrue(cursor.moveToFirst());
          assertEquals(inputAircraftId, cursor.getString(0));
    }
    
    public void testAircraftsInserts() {
        AircraftInfo aircraft = new AircraftInfo("aircraft99");
        Uri rowUri = mResolver.insert(
                LogbookContract.Aircrafts.CONTENT_URI,
                aircraft.getContentValues());

        long aircraftId = Long.valueOf(LogbookContract.Aircrafts.getAircraftId(rowUri));

        Cursor cursor = mResolver.query(
                LogbookContract.Aircrafts.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(1, cursor.getCount());
        assertTrue(cursor.moveToFirst());
        assertEquals(aircraft.aircraft_name, cursor.getString(cursor.getColumnIndex(LogbookContract.Aircrafts.AIRCRAFT_NAME)));

        ContentValues values = aircraft.getContentValues();
        values.put(LogbookContract.Aircrafts._ID, (int) aircraftId);
        try {
            rowUri = mResolver.insert(LogbookContract.Aircrafts.CONTENT_URI, values);
            fail("Expected insert failure for existing record but insert succeeded.");
        } catch (Exception e) {
            // succeeded, so do nothing.
        }
    }

    public void testAircraftsDeletes() {
        _testDeletes(
                LogbookContract.Aircrafts.CONTENT_URI,
                LogbookContract.Aircrafts.AIRCRAFT_NAME + "=?",
                new String[] { "aircraft0" }
        );
    }

    public void testAircraftsUpdates() {
        ContentValues values = new ContentValues();
        values.put(LogbookContract.Aircrafts.AIRCRAFT_NAME, "aircraft99");
        _testUpdates(
                LogbookContract.Aircrafts.CONTENT_URI,
                LogbookContract.Aircrafts.AIRCRAFT_NAME + "=?",
                new String[] { "aircraft1" },
                values
        );
    }

    public void testQueriesOnEquipmentUri() {
        _testQueriesOnURI(
                LogbookContract.Equipment.CONTENT_URI,
                new String[] { LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME, LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE },
                LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME + "=?",
                LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME + "=? OR " + LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME + "=? OR " + LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME + "=?",
                new String[] { "equipment2", "equipment1", "equipment0" },
                LogbookContract.Equipment.DEFAULT_SORT,
                TEST_EQUIPMENT
        );
    }
    
    public void testQueriesOnEquipmentIdUri() {
          final String SELECTION_COLUMNS = LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME + "=?";
          final String[] SELECTION_ARGS = { "equipment1" };
          final String SORT_ORDER = LogbookContract.Equipment.DEFAULT_SORT;
          final String[] EQUIPMENT_ID_PROJECTION = {
                  LogbookContract.Equipment._ID,
                  LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME,
                  LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE
          };

          Uri equipmentIdUri = LogbookContract.Equipment.buildEquipmentUri("1");
          
          Cursor cursor = mResolver.query(
              equipmentIdUri,
              null,
              null,
              null,
              null
          );
          assertEquals(0,cursor.getCount());

          insertData();
          cursor = mResolver.query(
                  LogbookContract.Equipment.CONTENT_URI,
                  EQUIPMENT_ID_PROJECTION, 
                  SELECTION_COLUMNS,
                  SELECTION_ARGS,
                  SORT_ORDER
          );

          assertEquals(1, cursor.getCount());
          assertTrue(cursor.moveToFirst());

          String inputEquipmentId = cursor.getString(0);
          equipmentIdUri = LogbookContract.Equipment.buildEquipmentUri(inputEquipmentId);
          cursor = mResolver.query(
                  equipmentIdUri,
                  EQUIPMENT_ID_PROJECTION,
                  SELECTION_COLUMNS,
                  SELECTION_ARGS,
                  SORT_ORDER
          );
          assertEquals(1, cursor.getCount());
          assertTrue(cursor.moveToFirst());
          assertEquals(inputEquipmentId, cursor.getString(0));
    }
    
    public void testEquipmentInserts() {
        EquipmentInfo equipment = new EquipmentInfo("equipment99", 280);
        Uri rowUri = mResolver.insert(
                LogbookContract.Equipment.CONTENT_URI,
                equipment.getContentValues()
        );

        long equipmentId = Long.valueOf(LogbookContract.Equipment.getEquipmentId(rowUri));

        Cursor cursor = mResolver.query(
                LogbookContract.Equipment.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(1, cursor.getCount());
        assertTrue(cursor.moveToFirst());
        assertEquals(equipment.equipment_canopy_name, cursor.getString(cursor.getColumnIndex(LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME)));
        assertEquals(equipment.equipment_canopy_size, cursor.getInt(cursor.getColumnIndex(LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE)));

        ContentValues values = equipment.getContentValues();
        values.put(LogbookContract.Equipment._ID, (int) equipmentId);
        try {
            rowUri = mResolver.insert(LogbookContract.Equipment.CONTENT_URI, values);
            fail("Expected insert failure for existing record but insert succeeded.");
        } catch (Exception e) {
            // succeeded, so do nothing.
        }
    }

    public void testEquipmentDeletes() {
        _testDeletes(
                LogbookContract.Equipment.CONTENT_URI,
                LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME + "=?",
                new String[] { "equipment0" }
        );
    }

    public void testEquipmentUpdates() {
        ContentValues values = new ContentValues();
        values.put(LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME, "equipment99");
        values.put(LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE, 280);
        _testUpdates(
                LogbookContract.Equipment.CONTENT_URI,
                LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME + "=?",
                new String[] { "equipment1" },
                values
        );
    }

    public void testQueriesOnJumpsUri() {
        _testQueriesOnURI(
                LogbookContract.Jumps.CONTENT_URI,
                new String[] {
                        LogbookContract.Jumps.JUMP_NUMBER,
                        LogbookContract.Jumps.JUMP_DESCRIPTION,
                        LogbookContract.Places.PLACE_NAME,
                        LogbookContract.Aircrafts.AIRCRAFT_NAME,
                        LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME,
                        LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE
                },
                LogbookContract.Jumps.JUMP_NUMBER + "=?",
                LogbookContract.Jumps.JUMP_NUMBER + "=? OR " + LogbookContract.Jumps.JUMP_NUMBER + "=? OR " + LogbookContract.Jumps.JUMP_NUMBER + "=?",
                new String[] { "2", "1", "0" },
                LogbookContract.Jumps.DEFAULT_SORT,
                TEST_JUMPS
        );
    }

    public void testQueriesOnJumpIdUri() {
          final String SELECTION_COLUMNS = LogbookContract.Jumps.JUMP_NUMBER + "=?";
          final String[] SELECTION_ARGS = { "1" };
          final String SORT_ORDER = LogbookContract.Jumps.DEFAULT_SORT;
          final String[] JUMP_ID_PROJECTION = {
                  LogbookContract.Jumps._ID,
                  LogbookContract.Jumps.JUMP_NUMBER,
                  LogbookContract.Jumps.JUMP_DESCRIPTION,
                  LogbookContract.Places.PLACE_NAME,
                  LogbookContract.Aircrafts.AIRCRAFT_NAME,
                  LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME,
                  LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE
          };

          Uri jumpIdUri = LogbookContract.Jumps.buildJumpUri("1");
          
          Cursor cursor = mResolver.query(
              jumpIdUri,
              null,
              null,
              null,
              null
          );
          assertEquals(0,cursor.getCount());

          insertData();
          cursor = mResolver.query(
                  LogbookContract.Jumps.CONTENT_URI,
                  JUMP_ID_PROJECTION, 
                  SELECTION_COLUMNS,
                  SELECTION_ARGS,
                  SORT_ORDER
          );

          assertEquals(1, cursor.getCount());
          assertTrue(cursor.moveToFirst());

          String inputEquipmentId = cursor.getString(0);
          jumpIdUri = LogbookContract.Jumps.buildJumpUri(inputEquipmentId);
          cursor = mResolver.query(
                  jumpIdUri,
                  JUMP_ID_PROJECTION,
                  SELECTION_COLUMNS,
                  SELECTION_ARGS,
                  SORT_ORDER
          );
          assertEquals(1, cursor.getCount());
          assertTrue(cursor.moveToFirst());
          assertEquals(inputEquipmentId, cursor.getString(0));
    }

    public void testJumpsInserts() {
        JumpInfo jump = new JumpInfo(1, 2, 3, 99, sTime.toMillis(false), 2200, 5, "description99");
        Uri rowUri = mResolver.insert(
                LogbookContract.Jumps.CONTENT_URI,
                jump.getContentValues()
        );

        long jumpId = Long.valueOf(LogbookContract.Jumps.getJumpId(rowUri));

        Cursor cursor = mResolver.query(
                LogbookContract.Jumps.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(1, cursor.getCount());
        assertTrue(cursor.moveToFirst());

        ContentValues values = jump.getContentValues();
        assertEquals((int)values.getAsInteger(LogbookContract.Jumps.PLACE_ID), cursor.getInt(cursor.getColumnIndexOrThrow(LogbookContract.Jumps.PLACE_ID)));
        assertEquals((int)values.getAsInteger(LogbookContract.Jumps.AIRCRAFT_ID), cursor.getInt(cursor.getColumnIndexOrThrow(LogbookContract.Jumps.AIRCRAFT_ID)));
        assertEquals((int)values.getAsInteger(LogbookContract.Jumps.EQUIPMENT_ID), cursor.getInt(cursor.getColumnIndexOrThrow(LogbookContract.Jumps.EQUIPMENT_ID)));
        assertEquals((int)values.getAsInteger(LogbookContract.Jumps.JUMP_NUMBER), cursor.getInt(cursor.getColumnIndexOrThrow(LogbookContract.Jumps.JUMP_NUMBER)));
        assertEquals(values.getAsString(LogbookContract.Jumps.JUMP_DESCRIPTION), cursor.getString(cursor.getColumnIndexOrThrow(LogbookContract.Jumps.JUMP_DESCRIPTION)));

        values.put(LogbookContract.Jumps._ID, (int) jumpId);
        try {
            rowUri = mResolver.insert(LogbookContract.Jumps.CONTENT_URI, values);
            fail("Expected insert failure for existing record but insert succeeded.");
        } catch (Exception e) {
            // succeeded, so do nothing.
        }
    }

    public void testJumpsDeletes() {
        _testDeletes(
                LogbookContract.Jumps.CONTENT_URI,
                LogbookContract.Jumps.JUMP_NUMBER + "=?",
                new String[] { "0" }
        );
    }

    public void testJumpsUpdates() {
        ContentValues values = new ContentValues();
        values.put(LogbookContract.Jumps.PLACE_ID, 5);
        values.put(LogbookContract.Jumps.AIRCRAFT_ID, 5);
        values.put(LogbookContract.Jumps.EQUIPMENT_ID, 5);
        values.put(LogbookContract.Jumps.JUMP_NUMBER, 6);
        values.put(LogbookContract.Jumps.JUMP_DESCRIPTION, "desctiption6");
        _testUpdates(
                LogbookContract.Jumps.CONTENT_URI,
                LogbookContract.Jumps.JUMP_NUMBER + "=?",
                new String[] { "0" },
                values
        );
    }

    private void _testQueriesOnURI(final Uri contentUri, final String[] projection, final String nameSelection, final String selectionColumns, final String[] selectionArgs, final String sortOrder, final Object[] testObjects) {
        Cursor cursor;
        Cursor projectionCursor;
        
        cursor = mResolver.query(
                contentUri,
                null,
                null,
                null,
                null
        );
        assertEquals(0, cursor.getCount());
        
        insertData();
        
        cursor = mResolver.query(
                contentUri,
                null,
                null,
                null,
                null
        );
        assertEquals(testObjects.length, cursor.getCount());
        
        projectionCursor = mResolver.query(
                contentUri,
                projection,
                null,
                null,
                null
        );
        assertEquals(projection.length, projectionCursor.getColumnCount());
        assertEquals(projection[0], projectionCursor.getColumnName(0));
        
        projectionCursor = mResolver.query(
                contentUri,
                projection,
                selectionColumns,
                selectionArgs,
                sortOrder
        );
        assertEquals(selectionArgs.length, projectionCursor.getCount());
        int index = 0;
        while (projectionCursor.moveToNext()) {
            assertEquals(selectionArgs[index], projectionCursor.getString(0));
            index++;
        }
        assertEquals(selectionArgs.length, index);
    }

    private void _testDeletes(final Uri contentUri, final String selectionColumns, final String[] selectionArgs) {
        int rowsDeleted = mResolver.delete(
                contentUri,
                selectionColumns,
                selectionArgs
        );
        assertEquals(0, rowsDeleted);

        insertData();
        rowsDeleted = mResolver.delete(
                contentUri,
                selectionColumns,
                selectionArgs
        );
        assertEquals(1, rowsDeleted);

        Cursor cursor = mResolver.query(
                contentUri,
                null,
                selectionColumns,
                selectionArgs,
                null
        );
        assertEquals(0, cursor.getCount());
    }
    
    private void _testUpdates(final Uri contentUri, final String selectionColumns, final String[] selectionArgs, ContentValues values) {        
        int rowsUpdated = mResolver.update(
                contentUri,
                values,
                selectionColumns,
                selectionArgs
        );
        assertEquals(0, rowsUpdated);

        insertData();
        rowsUpdated = mResolver.update(
                contentUri,
                values,
                selectionColumns,
                selectionArgs
        );
        assertEquals(1, rowsUpdated);
    }

    private static class PlaceInfo {
        String place_name;
        
        public PlaceInfo(String name) {
            this.place_name = name;
        }
        
        public ContentValues getContentValues() {
            ContentValues v = new ContentValues();
            v.put(LogbookContract.Places.PLACE_NAME, place_name);
            return v;
        }
    }

    private static class AircraftInfo {
        String aircraft_name;
        
        public AircraftInfo(String name) {
            this.aircraft_name = name;
        }
        
        public ContentValues getContentValues() {
            ContentValues v = new ContentValues();
            v.put(LogbookContract.Aircrafts.AIRCRAFT_NAME, aircraft_name);
            return v;
        }
    }

    private static class EquipmentInfo {
        String equipment_canopy_name;
        int equipment_canopy_size;
        
        public EquipmentInfo(String name, int size) {
            this.equipment_canopy_name = name;
            this.equipment_canopy_size = size;
        }
        
        public ContentValues getContentValues() {
            ContentValues v = new ContentValues();
            v.put(LogbookContract.Equipment.EQUIPMENT_CANOPY_NAME, equipment_canopy_name);
            v.put(LogbookContract.Equipment.EQUIPMENT_CANOPY_SIZE, equipment_canopy_size);
            return v;
        }
    }

    private static class JumpInfo {
        ContentValues contentValues;
        
        public JumpInfo(int place, int aircraft, int equipment, int number, long date, int altitude, int delay, String description) {
            contentValues = new ContentValues();
            contentValues.put(LogbookContract.Jumps.PLACE_ID, place);
            contentValues.put(LogbookContract.Jumps.AIRCRAFT_ID, aircraft);
            contentValues.put(LogbookContract.Jumps.EQUIPMENT_ID, equipment);
            contentValues.put(LogbookContract.Jumps.JUMP_NUMBER, number);
            contentValues.put(LogbookContract.Jumps.JUMP_DATE, date);
            contentValues.put(LogbookContract.Jumps.JUMP_ALTITUDE,altitude);
            contentValues.put(LogbookContract.Jumps.JUMP_DELAY, delay);
            contentValues.put(LogbookContract.Jumps.JUMP_DESCRIPTION, description);
        }
        
        public ContentValues getContentValues() {
            return contentValues;
        }
    }
}
