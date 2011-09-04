package org.tomcurran.logbook.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import org.tomcurran.logbook.provider.LogbookContract.Aircrafts;
import org.tomcurran.logbook.provider.LogbookContract.Equipment;
import org.tomcurran.logbook.provider.LogbookContract.Jumps;
import org.tomcurran.logbook.provider.LogbookContract.Places;
import org.tomcurran.logbook.provider.LogbookDatabase.Tables;
import org.tomcurran.logbook.util.SelectionBuilder;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

public class LogbookProvider extends ContentProvider {

	private static final UriMatcher sUriMatcher = buildUriMatcher();
	
	private static final int PLACES = 100;
    private static final int PLACES_ID = 101;
    private static final int PLACES_STATS = 102;
    
    private static final int AIRCRAFTS = 200;
    private static final int AIRCRAFTS_ID = 201;
    private static final int AIRCRAFTS_STATS = 202;
    
    private static final int EQUIPMENT = 300;
    private static final int EQUIPMENT_ID = 301;
    private static final int EQUIPMENT_STATS = 302;
    
    private static final int JUMPS = 400;
    private static final int JUMPS_ID = 401;
    private static final int JUMPS_LAST_MONTHS = 402;

	
	private LogbookDatabase mOpenHelper;
	
	private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = LogbookContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, "places", PLACES);
        matcher.addURI(authority, "places/#", PLACES_ID);
        matcher.addURI(authority, "places/stats", PLACES_STATS);

        matcher.addURI(authority, "aircrafts", AIRCRAFTS);
        matcher.addURI(authority, "aircrafts/#", AIRCRAFTS_ID);
        matcher.addURI(authority, "aircrafts/stats", AIRCRAFTS_STATS);

        matcher.addURI(authority, "equipment", EQUIPMENT);
        matcher.addURI(authority, "equipment/#", EQUIPMENT_ID);
        matcher.addURI(authority, "equipment/stats", EQUIPMENT_STATS);

        matcher.addURI(authority, "jumps", JUMPS);
        matcher.addURI(authority, "jumps/#", JUMPS_ID);
        matcher.addURI(authority, "jumps/months/#", JUMPS_LAST_MONTHS);

        return matcher;
	}

	@Override
	public boolean onCreate() {
		final Context context = getContext();
        mOpenHelper = new LogbookDatabase(context);
        return true;
	}

	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACES:
                return Places.CONTENT_TYPE;
            case PLACES_ID:
                return Places.CONTENT_ITEM_TYPE;
            case PLACES_STATS:
                return Places.CONTENT_TYPE;
            case AIRCRAFTS:
            	return Aircrafts.CONTENT_TYPE;
            case AIRCRAFTS_ID:
            	return Aircrafts.CONTENT_ITEM_TYPE;
            case AIRCRAFTS_STATS:
                return Aircrafts.CONTENT_TYPE;
            case EQUIPMENT:
            	return Equipment.CONTENT_TYPE;
            case EQUIPMENT_ID:
            	return Equipment.CONTENT_ITEM_TYPE;
            case EQUIPMENT_STATS:
                return Equipment.CONTENT_TYPE;
            case JUMPS:
            	return Jumps.CONTENT_TYPE;
            case JUMPS_ID:
            	return Jumps.CONTENT_ITEM_TYPE;
            case JUMPS_LAST_MONTHS:
            	return Jumps.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            default: {
                // Most cases are handled with simple SelectionBuilder
                final SelectionBuilder builder = buildExpandedSelection(uri, match);
                return builder.where(selection, selectionArgs).query(db, projection, sortOrder);
            }
            case PLACES_STATS: {
            	final SelectionBuilder builder = buildExpandedSelection(uri, match);
                return builder.where(selection, selectionArgs)
                		.query(db, projection, Qualified.PLACES_PLACE_ID, null, sortOrder, null);
            }
            case AIRCRAFTS_STATS: {
            	final SelectionBuilder builder = buildExpandedSelection(uri, match);
                return builder.where(selection, selectionArgs)
                		.query(db, projection, Qualified.AIRCRAFTS_AIRCRAFT_ID, null, sortOrder, null);
            }
            case EQUIPMENT_STATS: {
            	final SelectionBuilder builder = buildExpandedSelection(uri, match);
                return builder.where(selection, selectionArgs)
                		.query(db, projection, Qualified.EQUIPMENT_EQUIPMENT_ID, null, sortOrder, null);
            }
        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACES: {
                long place_id = db.insertOrThrow(Tables.PLACES, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Places.buildPlaceUri(String.valueOf(place_id));
            }
            case AIRCRAFTS: {
                long aircraft_id = db.insertOrThrow(Tables.AIRCRAFTS, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Aircrafts.buildAircraftUri(String.valueOf(aircraft_id));
            }
            case EQUIPMENT: {
                long equipment_id = db.insertOrThrow(Tables.EQUIPMENT, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Equipment.buildEquipmentUri(String.valueOf(equipment_id));
            }
            case JUMPS: {
                long jump_id = db.insertOrThrow(Tables.JUMPS, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Jumps.buildJumpUri(String.valueOf(jump_id));
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).update(db, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return retVal;
	}
	
	@Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).delete(db);
        getContext().getContentResolver().notifyChange(uri, null);
        return retVal;
    }
	
	@Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

	private SelectionBuilder buildSimpleSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACES: {
                return builder.table(Tables.PLACES);
            }
            case PLACES_ID: {
                final String placeId = Places.getPlaceId(uri);
                return builder.table(Tables.PLACES).
                		where(Places._ID + "=?", placeId);
            }
            case AIRCRAFTS: {
            	return builder.table(Tables.AIRCRAFTS);
            }
            case AIRCRAFTS_ID: {
            	final String aircraftId = Aircrafts.getAircraftId(uri);
            	return builder.table(Tables.AIRCRAFTS).
            			where(Aircrafts._ID + "=?", aircraftId);
            }
            case EQUIPMENT: {
            	return builder.table(Tables.EQUIPMENT);
            }
            case EQUIPMENT_ID: {
            	final String equipmentId = Equipment.getEquipmentId(uri);
            	return builder.table(Tables.EQUIPMENT).
            			where(Equipment._ID + "=?", equipmentId);
        	}
            case JUMPS: {
            	return builder.table(Tables.JUMPS);
            }
            case JUMPS_ID: {
            	final String jumpId = Jumps.getJumpId(uri);
            	return builder.table(Tables.JUMPS).
            			where(Jumps._ID + "=?", jumpId);
            }
            case JUMPS_LAST_MONTHS: {
            	Calendar calendar = Calendar.getInstance();
	    		calendar.add(Calendar.MONTH, -Integer.valueOf(Jumps.getMonths(uri)));
	    		final String date = String.valueOf(calendar.getTimeInMillis());
            	return builder.table(Tables.JUMPS).
            			where(Jumps.JUMP_DATE + ">=?", date);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
	}
	
	private SelectionBuilder buildExpandedSelection(Uri uri, int match) {
        final SelectionBuilder builder = new SelectionBuilder();
        switch (match) {
            case PLACES: {
            	return builder.table(Tables.PLACES);
            }
            case PLACES_ID: {
                final String placeId = Places.getPlaceId(uri);
                return builder.table(Tables.PLACES)
                        .where(Places._ID + "=?", placeId);
            }
            case PLACES_STATS: {
                return builder.table(Tables.PLACES_JOIN_JUMPS)
						.mapToTable(Places._ID, Tables.PLACES)
		    			.mapToTable(Jumps.PLACE_ID, Tables.JUMPS)
		    			.mapToTable(Jumps.AIRCRAFT_ID, Tables.JUMPS)
		    			.mapToTable(Jumps.EQUIPMENT_ID, Tables.JUMPS);
            }
            case AIRCRAFTS: {
            	return builder.table(Tables.AIRCRAFTS);
            }
            case AIRCRAFTS_ID: {
            	final String aircraftId = Aircrafts.getAircraftId(uri);
            	return builder.table(Tables.AIRCRAFTS).
            			where(Aircrafts._ID + "=?", aircraftId);
            }
            case AIRCRAFTS_STATS: {
                return builder.table(Tables.AIRCRAFTS_JOIN_JUMPS)
						.mapToTable(Aircrafts._ID, Tables.AIRCRAFTS)
		    			.mapToTable(Jumps.PLACE_ID, Tables.JUMPS)
		    			.mapToTable(Jumps.AIRCRAFT_ID, Tables.JUMPS)
		    			.mapToTable(Jumps.EQUIPMENT_ID, Tables.JUMPS);
            }
            case EQUIPMENT: {
            	return builder.table(Tables.EQUIPMENT);
            }
            case EQUIPMENT_ID: {
            	final String equipmentId = Equipment.getEquipmentId(uri);
            	return builder.table(Tables.EQUIPMENT).
            			where(Equipment._ID + "=?", equipmentId);
            }
            case EQUIPMENT_STATS: {
                return builder.table(Tables.EQUIPMENT_JOIN_JUMPS)
						.mapToTable(Equipment._ID, Tables.EQUIPMENT)
		    			.mapToTable(Jumps.PLACE_ID, Tables.JUMPS)
		    			.mapToTable(Jumps.AIRCRAFT_ID, Tables.JUMPS)
		    			.mapToTable(Jumps.EQUIPMENT_ID, Tables.JUMPS);
            }
            case JUMPS: {
            	return builder.table(Tables.JUMPS_JOIN_PLACES_AIRCRAFTS_EQUIPMENT)
    					.mapToTable(Jumps._ID, Tables.JUMPS)
            			.mapToTable(Jumps.PLACE_ID, Tables.JUMPS)
            			.mapToTable(Jumps.AIRCRAFT_ID, Tables.JUMPS)
            			.mapToTable(Jumps.EQUIPMENT_ID, Tables.JUMPS);
            }
            case JUMPS_ID: {
            	final String jumpId = Jumps.getJumpId(uri);
            	return builder.table(Tables.JUMPS_JOIN_PLACES_AIRCRAFTS_EQUIPMENT)
            			.mapToTable(Jumps._ID, Tables.JUMPS)
		    			.mapToTable(Jumps.PLACE_ID, Tables.JUMPS)
		    			.mapToTable(Jumps.AIRCRAFT_ID, Tables.JUMPS)
		    			.mapToTable(Jumps.EQUIPMENT_ID, Tables.JUMPS)
            			.where(Qualified.JUMPS_JUMP_ID + "=?", jumpId);
            }
            case JUMPS_LAST_MONTHS: {
            	Calendar calendar = Calendar.getInstance();
	    		calendar.add(Calendar.MONTH, -Integer.valueOf(Jumps.getMonths(uri)));
	    		final String date = String.valueOf(calendar.getTimeInMillis());
            	return builder.table(Tables.JUMPS_JOIN_PLACES_AIRCRAFTS_EQUIPMENT)
		    			.mapToTable(Jumps._ID, Tables.JUMPS)
		    			.mapToTable(Jumps.PLACE_ID, Tables.JUMPS)
		    			.mapToTable(Jumps.AIRCRAFT_ID, Tables.JUMPS)
		    			.mapToTable(Jumps.EQUIPMENT_ID, Tables.JUMPS)
            			.where(Qualified.JUMPS_JUMP_DATE + ">=?", date);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
	}

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    private interface Qualified {
    	String PLACES_PLACE_ID = Tables.PLACES + "." + Places._ID;
    	String AIRCRAFTS_AIRCRAFT_ID = Tables.AIRCRAFTS + "." + Aircrafts._ID;
    	String EQUIPMENT_EQUIPMENT_ID = Tables.EQUIPMENT + "." + Equipment._ID;
    	String JUMPS_JUMP_ID = Tables.JUMPS + "." + Jumps._ID;
    	String JUMPS_JUMP_DATE = Tables.JUMPS + "." + Jumps.JUMP_DATE;
    }

}
