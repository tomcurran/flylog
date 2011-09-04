package org.tomcurran.logbook.provider;

import org.tomcurran.logbook.provider.LogbookContract.Aircrafts;
import org.tomcurran.logbook.provider.LogbookContract.AircraftsColumns;
import org.tomcurran.logbook.provider.LogbookContract.Equipment;
import org.tomcurran.logbook.provider.LogbookContract.EquipmentColumns;
import org.tomcurran.logbook.provider.LogbookContract.Jumps;
import org.tomcurran.logbook.provider.LogbookContract.JumpsColumns;
import org.tomcurran.logbook.provider.LogbookContract.Places;
import org.tomcurran.logbook.provider.LogbookContract.PlacesColumns;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class LogbookDatabase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "logbook.db";
	
	private static final int VER_INIT = 1;
	
	private static final int DATABASE_VERSION = VER_INIT;
	
	interface Tables {
		String PLACES = "places";
		String AIRCRAFTS = "aircrafts";
		String EQUIPMENT = "equipment";
		String JUMPS = "jumps";
		
		String JUMPS_JOIN_PLACES_AIRCRAFTS_EQUIPMENT = "jumps "
				+ "LEFT OUTER JOIN places ON jumps.place_id=places._id "
				+ "LEFT OUTER JOIN aircrafts ON jumps.aircraft_id=aircrafts._id "
				+ "LEFT OUTER JOIN equipment ON jumps.equipment_id=equipment._id";
		String PLACES_JOIN_JUMPS = "places "
				+ "LEFT OUTER JOIN jumps ON places._id=jumps.place_id";
		String AIRCRAFTS_JOIN_JUMPS = "aircrafts "
				+ "LEFT OUTER JOIN jumps ON aircrafts._id=jumps.aircraft_id";
		String EQUIPMENT_JOIN_JUMPS = "equipment "
				+ "LEFT OUTER JOIN jumps ON equipment._id=jumps.equipment_id";
	}
	
	private interface References {
		String PLACE_ID = "REFERENCES " + Tables.PLACES + "(" + Places._ID + ")";
		String AIRCRAFT_ID = "REFERENCES " + Tables.AIRCRAFTS + "(" + Aircrafts._ID + ")";
		String EQUIPMENT_ID = "REFERENCES " + Tables.EQUIPMENT + "(" + Equipment._ID + ")";
	}
	
	public LogbookDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + Tables.PLACES + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ PlacesColumns.PLACE_NAME + " TEXT NOT NULL)"
		);

		db.execSQL("CREATE TABLE " + Tables.AIRCRAFTS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ AircraftsColumns.AIRCRAFT_NAME + " TEXT NOT NULL)"
		);

		db.execSQL("CREATE TABLE " + Tables.EQUIPMENT + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ EquipmentColumns.EQUIPMENT_CANOPY_NAME + " TEXT NOT NULL,"
				+ EquipmentColumns.EQUIPMENT_CANOPY_SIZE + " INTEGER NOT NULL)"
		);
		
		db.execSQL("CREATE TABLE " + Tables.JUMPS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Jumps.PLACE_ID + " INTEGER " + References.PLACE_ID + ","
				+ Jumps.AIRCRAFT_ID + " INTEGER " + References.AIRCRAFT_ID + ","
				+ Jumps.EQUIPMENT_ID + " INTEGER " + References.EQUIPMENT_ID + ","
				+ JumpsColumns.JUMP_NUMBER + " INTEGER NOT NULL,"
				+ JumpsColumns.JUMP_DATE + " INTEGER NOT NULL,"
				+ JumpsColumns.JUMP_ALTITUDE + " INTEGER NOT NULL,"
				+ JumpsColumns.JUMP_DELAY + " INTEGER NOT NULL,"
				+ JumpsColumns.JUMP_DESCRIPTION + " TEXT NOT NULL)"
		);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		int version = oldVersion;

//        switch (version) {
//            case VER_LAUNCH:
//				version = VER_nextcase...
//        }

		if (version != DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + Tables.PLACES);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.AIRCRAFTS);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.EQUIPMENT);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.JUMPS);
            
            onCreate(db);
		}
	}

}
