package org.tomcurran.logbook.util;

import org.tomcurran.logbook.provider.LogbookContract.Aircrafts;
import org.tomcurran.logbook.provider.LogbookContract.Equipment;
import org.tomcurran.logbook.provider.LogbookContract.Jumps;
import org.tomcurran.logbook.provider.LogbookContract.Places;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.text.format.Time;

public class TestData {

	private ContentResolver mResolver;

	private static final PlaceInfo[] PLACES = {
			new PlaceInfo("Strathallan"),
			new PlaceInfo("Black Knights"),
			new PlaceInfo("Wild Geese"),
			new PlaceInfo("Perris")
	};

	private static final AircraftInfo[] AIRCRAFTS = {
			new AircraftInfo("C206"),
			new AircraftInfo("Porter"),
			new AircraftInfo("C208"),
			new AircraftInfo("Twin Otter"),
			new AircraftInfo("Skyvan"),
			new AircraftInfo("Baloon")
	};

	private static final EquipmentInfo[] EQUIPMENT = {
			new EquipmentInfo("Manta",      280),
			new EquipmentInfo("Maveron",    240),
			new EquipmentInfo("PD",         210),
			new EquipmentInfo("Balance",    210),
			new EquipmentInfo("Fury",       220),
			new EquipmentInfo("Spectre",    190),
			new EquipmentInfo("Triathalon", 175),
			new EquipmentInfo("Sabre 2",    190)
	};

	private static final ContentValues[] JUMPS = {
			newJump(  0,     0,  0, 1970,  1,  1, "USED TO MATCH INDEXS TO JUMP NUMBER delete"),
			newJump(  1,  3500,  0, 2009, 11, 28, "Good exit, position & count\n(just get head further back)"),
			newJump(  2,  3500,  0, 2010,  2, 21, "Good exit, position & count\nBOC DP's next"),
			newJump(  3,  3500,  0, 2010,  3, 13, "Good setup, turned 90 right on exit, did get DP"),
			newJump(  4,  3500,  0, 2010,  3, 14, "Good setup, exit good & on heading. Fumbled D/P handle but got it on 5 seconds"),
			newJump(  5,  3500,  0, 2010,  3, 21, "Exit OK but didn't arch\nFumbled D/P but when got it good grasp, pull & recovery"),
			newJump(  6,  3500,  0, 2010,  3, 28, "Turned slight to right on eixt. Upper body good but dropped knees\nGood grasp, pull & recovery"),
			newJump(  7,  3500,  0, 2010,  4,  9, "G.A.T.W"),
			newJump(  8,  3500,  0, 2010,  4,  9, "Very good D.P. + ?\nNo ?"),
			newJump(  9,  3500,  0, 2010,  4,  9, "Great D/P.\nJust slight flat.\nC/C O.K.\nJust arch more!"),
			newJump( 10,  3500,  0, 2010,  4, 10, "Excellent D/P.\nJust scccchloooon down!\nGood C/C.\nCleared for F/F."),
			newJump( 11,  3500,  0, 2010,  4, 11, "Perfect D.R.C.P"),
			newJump( 12,  3500,  0, 2010,  4, 12, "Perfect D/P\nThat's all I've got to say about that!"),
			newJump( 13,  3500,  0, 2010,  5,  3, "Good exit into stable spread + arch, V.G reach, pull + recovery\nOn heading through out\nGATW\n3rd good DRCP + cleared for 3 second delay"),
			newJump( 14,  4200,  3, 2010,  5,  4, "Lost exit\nOn back on pull\nLook forward and up on exit\nTry 3 sec again"),
			newJump( 15,  4200,  3, 2010,  5,  8, "Perfect exit & deployment\nWell done"),
			newJump( 16,  4200,  3, 2010,  5,  8, "V.G.A.T.W\n5 sec next\nWell done"),
			newJump( 17,  4200,  5, 2010,  5,  8, "Perfect 5s delay\n/v.V.V.V.V.G.A.T.W\n10s next!"),
			newJump( 18,  4500, 10, 2010,  5,  8, "Perfect 10! V.V.V.V.G.A.T.W\n1 more!"),
			newJump( 19,  4500, 10, 2010,  5,  9, "And again...\nPerfect 10!!\n15s next!"),
			newJump( 20,  6000, 15, 2010,  5,  9, "Excellent 15 sec delay\n15 sec + alti next"),
			newJump( 21,  6000, 15, 2010,  5,  9, "Good exit & fallaway.\nSaw student check alti on way down. Good count\nPull on alti next!\nWell done!"),
			newJump( 22,  5500, 15, 2010,  5, 16, "Perfect straight delay.\nExcellet altitude awareness.\nTurns next!"),
			newJump( 23, 10000, 35, 2010,  5, 16, "Excellent exit, fallaway, right & left 360 turns\nGood alti awareness\nUnstable exit next"),
			newJump( 24,  9000, 30, 2010,  5, 22, "Very unstable exit!\nExcellent arch to recover after 5 seconds\nExcellent alti awareness, pull, recovery + canopy control.\nBackloops nexy"),
			newJump( 25,  8500, 25, 2010,  5, 23, "V. good exit & fallaway\n2 excellent bacloops\nGood alti awareness & good deployment at correct height + good c/c\nDive exit next"),
			newJump( 26, 10000, 35, 2010,  5, 30, "Good position in door & good dive except didn't duck legs up enough. Went into very steep & flipped over but otherise very controlled & stable throughout.\nGood canopy control - just watch flare\nDive exit + track on next jump"),
			newJump( 27, 10000, 35, 2010,  5, 31, "Perfect dive exit!\nExcellent heading control, and altitude awareness.\n2 very steep tracks.\n Godd pull recovery\nExcellent c/c.\nGo into track slower, to gain forward movement.\nTrack again next"),
			newJump( 28, 10000, 35, 2010,  5, 31, "Good exit\n2 excellent tracks\nExcellent A/A, pull + recovery\nTrack turns next!"),
			newJump( 29, 12500, 50, 2010,  8,  4, "Excellent exit! 2 excellent track turns to left + right!\nExcellent A/A, pull + c/c\nCat 8 next!"),
			newJump( 30,  8000, 25, 2010,  8,  4, "Exit from 8000ft due to cloud.\nAll manoeuvres completed, good throughout, and good alti awareness.\nWell done"),
			newJump( 31,  8000, 25, 2010,  8,  5, ""),
			newJump( 32,  6000, 15, 2010,  8,  7, ""),
			newJump( 33, 10000, 35, 2010,  8,  7, ""),
			newJump( 34, 13000, 60, 2010,  8, 17, ""),
			newJump( 35, 13000, 60, 2010,  8, 18, ""),
			newJump( 36, 13000, 60, 2010,  8, 19, ""),
			newJump( 37,  7800, 29, 2010, 10,  2, ""),
			newJump( 38,  2500,  3, 2010, 10, 10, ""),
			newJump( 39,  4580, 15, 2010, 10, 16, ""),
			newJump( 40,  4820, 16, 2010, 10, 16, ""),
			newJump( 41,  4860, 17, 2010, 10, 23, ""),
			newJump( 42,  5080, 18, 2010, 11, 27, ""),
			newJump( 43,  4860, 15, 2011,  1, 22, ""),
			newJump( 44,  6230, 22, 2011,  1, 23, ""),
			newJump( 45,  4860, 15, 2011,  1, 23, ""),
			newJump( 46,  4500, 11, 2011,  1, 29, ""),
			newJump( 47,  3210,  7, 2011,  1, 29, ""),
			newJump( 48,  4900, 18, 2011,  2,  5, ""),
			newJump( 49,  4370, 16, 2011,  2, 12, ""),
			newJump( 50,  8910, 34, 2011,  2, 26, ""),
			newJump( 51,  7000, 24, 2011,  2, 26, ""),
			newJump( 52, 10000, 35, 2011,  2, 27, ""),
			newJump( 53,  3140,  8, 2011,  3, 26, ""),
			newJump( 54,  4270, 13, 2011,  3, 26, ""),
			newJump( 55,  4990, 18, 2011,  3, 26, ""),
			newJump( 56,  2500,  3, 2011,  3, 26, ""),
			newJump( 57,  9480, 38, 2011,  3, 27, ""),
			newJump( 58, 10240, 44, 2011,  3, 27, ""),
			newJump( 59,  9730, 45, 2011,  3, 27, ""),
			newJump( 60, 12520, 57, 2011,  4,  1, ""),
			newJump( 61, 12700, 59, 2011,  4,  1, ""),
			newJump( 62, 12460, 55, 2011,  4,  1, ""),
			newJump( 63, 12680, 59, 2011,  4,  2, ""),
			newJump( 64, 12690, 59, 2011,  4,  2, ""),
			newJump( 65, 12700, 58, 2011,  4,  2, ""),
			newJump( 66, 12740, 58, 2011,  4,  2, ""),
			newJump( 67, 12520, 55, 2011,  4,  2, ""),
			newJump( 68,  5260, 19, 2011,  4,  3, ""),
			newJump( 69,  6890, 25, 2011,  4,  4, ""),
			newJump( 70, 12770, 61, 2011,  4,  4, ""),
			newJump( 71, 12850, 59, 2011,  4,  4, ""),
			newJump( 72, 12690, 60, 2011,  4,  4, ""),
			newJump( 73, 12650, 60, 2011,  4,  5, ""),
			newJump( 74, 12780, 59, 2011,  4,  5, ""),
			newJump( 75, 12460, 59, 2011,  4,  5, ""),
			newJump( 76, 12640, 59, 2011,  4,  5, ""),
			newJump( 77, 12850, 60, 2011,  4,  5, ""),
			newJump( 78, 13200, 62, 2011,  4,  9, ""),
			newJump( 79, 12620, 52, 2011,  4,  9, ""),
			newJump( 80, 12880, 60, 2011,  4,  9, ""),
			newJump( 81, 12560, 63, 2011,  4, 10, ""),
			newJump( 82, 12370, 59, 2011,  4, 10, ""),
			newJump( 83, 12470, 57, 2011,  4, 10, ""),
			newJump( 84, 12660, 61, 2011,  4, 10, ""),
			newJump( 85, 12700, 59, 2011,  4, 11, ""),
			newJump( 86, 12640, 58, 2011,  4, 11, ""),
			newJump( 87, 12780, 59, 2011,  4, 11, ""),
			newJump( 88, 12690, 61, 2011,  4, 11, ""),
			newJump( 89, 13920, 64, 2011,  4, 11, ""),
			newJump( 90, 12740, 58, 2011,  4, 12, ""),
			newJump( 91, 12680, 55, 2011,  4, 12, ""),
			newJump( 92, 18720, 90, 2011,  4, 14, ""),
			newJump( 93, 12820, 59, 2011,  4, 12, ""),
			newJump( 94, 12450, 58, 2011,  4, 12, ""),
			newJump( 95, 12590, 59, 2011,  4, 13, ""),
			newJump( 96, 12320, 56, 2011,  4, 13, ""),
			newJump( 97, 12760, 59, 2011,  4, 13, ""),
			newJump( 98,  4710, 16, 2011,  4, 24, ""),
			newJump( 99,  4160,  8, 2011,  4, 25, "chilled out"),
			newJump(100,  8060, 30, 2011,  5,  1, "few barrol rolls & backloops & turns\npuled high to try out new canopy\nlots of fun\nlanded in pit"),
			newJump(101,  9570, 40, 2011,  5,  1, "same as #100\nlanded hanger side of pit\nmake sure not to"),
			newJump(102,  5030, 18, 2011,  5,  1, "practice jump master & spotting\nspun about\nlanded in pit"),
			newJump(103,  9040, 38, 2011,  5,  1, "tracked"),
			newJump(104,  4070, 12, 2011,  5,  1, "rolled out plane"),
			newJump(105,  7360, 28, 2011,  5, 30, "good party night before so just relaxed\nmajor chill out, backloop for good measure\ntried different ways of turning"),
			newJump(106,  9820, 43, 2011,  5, 30, "two way with kelly\nneed to make better use of deep brakes if far out on deployment"),
			newJump(107,  2130,  7, 2011,  6,  4, "hop'n'pop"),
			newJump(108,  2400,  7, 2011,  6,  4, "hop'n'pop"),
			newJump(109,  4030, 11, 2011,  6,  4, "hop'n'pop"),
			newJump(110,  2370,  7, 2011,  6,  4, "hop'n'pop"),
			newJump(111,  2850,  8, 2011,  6,  5, "hop'n'pop"),
			newJump(112,  4090, 14, 2011,  6,  5, "hop'n'pop"),
			newJump(113,  4670, 16, 2011,  6, 25, "backloop that didn't go so well\nthen turned to check out others in freefall"),
			newJump(114,  3880, 12, 2011,  6, 25, "backloop exit turned into a barrel roll"),
			newJump(115,  9810, 47, 2011,  6, 25, "5 way speed star unlinked exit with john, sandy, jonny, kieran"),
			newJump(116,  2200,  5, 2011,  6, 26, "hop'n'pop"),
			newJump(117,  4870, 18, 2011,  7,  2, "3 way. Lost grips on exit"),
			newJump(118,  9590, 43, 2011,  7,  3, "2 way with sandy.\nDave filming"),
			newJump(119,  9960, 46, 2011,  7,  3, "3 way with sandy & gus. Got first two points good. Then gus forgot the third point and it sort of went tits up"),
			newJump(120,  9380, 44, 2011,  7,  3, "5 way with sandy, kelly, gus, steve"),
			newJump(121,  8980, 40, 2011,  7, 23, "spotting practical. Tracked"),
			newJump(122,  9400, 40, 2011,  7, 23, "3 way fs with sandy, gus. First two points good, then sandy got away from us"),
			newJump(123,  9550, 40, 2011,  7, 23, "4 way tracking dive with sandy, gus, steve. Gus went wrong way, rest of us stayed in the same vacinity"),
			newJump(124,  9880, 45, 2011,  7, 30, "4 way fs with alan, jo, ian\nBuilt star, broke apart, then build star again"),
			newJump(125,  4710, 16, 2011,  7, 30, "spotted, backloop"),
			newJump(126,  5000,  3, 2011,  8,  1, "first crew, two good docks, two spirals"),
			newJump(127,  6000,  3, 2011,  8,  2, "2 way crew, receiving"),
			newJump(128,  7000,  3, 2011,  8,  3, "3 way crew, docking third"),
			newJump(129,  7000,  3, 2011,  8,  3, "4 way crew, docking fourth"),
			newJump(130,  10000, 3, 2011,  8,  3, "9 way crew attempt\nDocked second, built 4 stack, paul got off top, piloted for the rest of the jump, mangage to get 6 in stack"),
			newJump(131,  10000, 3, 2011,  8,  3, "8 way crew attempt\nDocked third\nManaged to get 7 in the stack"),
			newJump(132,  7000,  3, 2011,  8,  4, "6 way crew attempt"),
			newJump(133,  7000,  3, 2011,  8,  4, "6 way crew attempt, clouds"),
			newJump(134, 11200, 54, 2011,  8,  5, "Carols 3 point 3 way, phil coaching\n2 points, really close to last point"),
			newJump(135,  8000,  3, 2011,  8,  5, "8 way crew\nDocked fourth"),
			newJump(136, 11360, 53, 2011,  8,  5, "Carols second 3 point 3 way, phil coaching\n5 points"),
			newJump(137, 10000,  3, 2011,  8,  5, "8 way crew\nDocked fourth instead of fifth because people weren't filling their slots"),
			newJump(138, 12500, 60, 2011,  8,  5, "Carols 4 point 3 way, greg coach\n4 points"),
			newJump(139, 13680, 60, 2011,  8,  5, "9 way tracking\nMega fun\nWas slightly behind"),
			newJump(140, 10140, 47, 2011,  8,  6, "Carols 4 way attempt"),
			newJump(141, 10360, 49, 2011,  8,  6, "Carols second 4 way attempt. Exit funnelled"),
			newJump(142, 12930, 59, 2011,  9,  3, "2 way fs with john\nUnlinked exit, john floating, me diving\nDid alternate 90, 180, 360 turns and backloop"),
			newJump(143, 13110, 59, 2011,  9,  3, "2 way fs with john\nUnlinked exit, john diving, me floating\nSpun each other about\nTried back tracking")
	};

	private static ContentValues newJump(int number, int altitude, int delay, int jYear, int jMonth, int jDay, String description) {
			ContentValues values = new ContentValues();
			values.put(Jumps.PLACE_ID, 0);
			values.put(Jumps.AIRCRAFT_ID, 0);
			values.put(Jumps.EQUIPMENT_ID, 0);
			values.put(Jumps.JUMP_NUMBER, number);
			Time time = new Time();
			time.set(jDay, jMonth - 1, jYear);
			values.put(Jumps.JUMP_DATE, time.toMillis(false));
			values.put(Jumps.JUMP_ALTITUDE, altitude);
			values.put(Jumps.JUMP_DELAY, delay);
			values.put(Jumps.JUMP_DESCRIPTION, description);
			return values;
	}

	private static void setJump(ContentValues values, Uri placeUri, Uri aircraftUri, Uri equipmentUri) {
		values.put(Jumps.PLACE_ID, Integer.valueOf(Places.getPlaceId(placeUri)));
		values.put(Jumps.AIRCRAFT_ID, Integer.valueOf(Aircrafts.getAircraftId(aircraftUri)));
		values.put(Jumps.EQUIPMENT_ID, Integer.valueOf(Equipment.getEquipmentId(equipmentUri)));
	}

	public void insert() {
		ContentResolver res = mResolver;

		Uri SPC    = res.insert(Places.CONTENT_URI, PLACES[0].getContentValues());
		Uri BKPC   = res.insert(Places.CONTENT_URI, PLACES[1].getContentValues());
		Uri Geese  = res.insert(Places.CONTENT_URI, PLACES[2].getContentValues());
		Uri Perris = res.insert(Places.CONTENT_URI, PLACES[3].getContentValues());

		Uri C206   = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[0].getContentValues());
		Uri Porter = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[1].getContentValues());
		Uri C208   = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[2].getContentValues());
		Uri TwinOt = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[3].getContentValues());
		Uri Skyvan = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[4].getContentValues());
		Uri Baloon = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[5].getContentValues());

		Uri Manta   = res.insert(Equipment.CONTENT_URI, EQUIPMENT[0].getContentValues());
		Uri Maveron = res.insert(Equipment.CONTENT_URI, EQUIPMENT[1].getContentValues());
		Uri PD      = res.insert(Equipment.CONTENT_URI, EQUIPMENT[2].getContentValues());
		Uri Balance = res.insert(Equipment.CONTENT_URI, EQUIPMENT[3].getContentValues());
		Uri Fury    = res.insert(Equipment.CONTENT_URI, EQUIPMENT[4].getContentValues());
		Uri Spectre = res.insert(Equipment.CONTENT_URI, EQUIPMENT[5].getContentValues());
		Uri Triatha = res.insert(Equipment.CONTENT_URI, EQUIPMENT[6].getContentValues());
		Uri Sabre2  = res.insert(Equipment.CONTENT_URI, EQUIPMENT[7].getContentValues());

		setJump(JUMPS[1  ], SPC,    C206,   Manta);
		setJump(JUMPS[2  ], SPC,    C206,   Manta);
		setJump(JUMPS[3  ], SPC,    C206,   Manta);
		setJump(JUMPS[4  ], SPC,    C206,   Manta);
		setJump(JUMPS[5  ], SPC,    C206,   Manta);
		setJump(JUMPS[6  ], SPC,    C206,   Manta);
		setJump(JUMPS[7  ], SPC,    C206,   Manta);
		setJump(JUMPS[8  ], SPC,    C206,   Manta);
		setJump(JUMPS[9  ], SPC,    C206,   Manta);
		setJump(JUMPS[10 ], SPC,    C206,   Manta);
		setJump(JUMPS[11 ], SPC,    C206,   Manta);
		setJump(JUMPS[12 ], SPC,    C206,   Manta);
		setJump(JUMPS[13 ], BKPC,   Porter, Manta);
		setJump(JUMPS[14 ], BKPC,   Porter, Manta);
		setJump(JUMPS[15 ], SPC,    C206,   Manta);
		setJump(JUMPS[16 ], SPC,    C206,   Manta);
		setJump(JUMPS[17 ], SPC,    C206,   Manta);
		setJump(JUMPS[18 ], SPC,    C206,   Manta);
		setJump(JUMPS[19 ], SPC,    C206,   Manta);
		setJump(JUMPS[20 ], SPC,    C206,   Manta);
		setJump(JUMPS[21 ], SPC,    C206,   Manta);
		setJump(JUMPS[22 ], SPC,    C206,   Manta);
		setJump(JUMPS[23 ], SPC,    C206,   Manta);
		setJump(JUMPS[24 ], SPC,    C206,   Manta);
		setJump(JUMPS[25 ], SPC,    C206,   Manta);
		setJump(JUMPS[26 ], SPC,    C206,   Manta);
		setJump(JUMPS[27 ], SPC,    C206,   Manta);
		setJump(JUMPS[28 ], SPC,    C206,   Manta);
		setJump(JUMPS[29 ], SPC,    Porter, Manta);
		setJump(JUMPS[30 ], SPC,    C206,   Manta);
		setJump(JUMPS[31 ], SPC,    C206,   Manta);
		setJump(JUMPS[32 ], SPC,    C206,   Manta);
		setJump(JUMPS[33 ], SPC,    C206,   Manta);
		setJump(JUMPS[34 ], Geese,  C208,   Maveron);
		setJump(JUMPS[35 ], Geese,  C208,   Maveron);
		setJump(JUMPS[36 ], Geese,  C208,   PD);
		setJump(JUMPS[37 ], SPC,    C206,   Manta);
		setJump(JUMPS[38 ], SPC,    C206,   Manta);
		setJump(JUMPS[39 ], SPC,    C206,   Manta);
		setJump(JUMPS[40 ], SPC,    C206,   Manta);
		setJump(JUMPS[41 ], SPC,    C206,   Manta);
		setJump(JUMPS[42 ], SPC,    C206,   Manta);
		setJump(JUMPS[43 ], SPC,    C206,   Manta);
		setJump(JUMPS[44 ], SPC,    C206,   Manta);
		setJump(JUMPS[45 ], SPC,    C206,   Manta);
		setJump(JUMPS[46 ], SPC,    C206,   Manta);
		setJump(JUMPS[47 ], SPC,    C206,   Manta);
		setJump(JUMPS[48 ], SPC,    C206,   Manta);
		setJump(JUMPS[49 ], SPC,    C206,   Manta);
		setJump(JUMPS[50 ], SPC,    C206,   Manta);
		setJump(JUMPS[51 ], SPC,    C206,   Manta);
		setJump(JUMPS[52 ], SPC,    C206,   Manta);
		setJump(JUMPS[53 ], SPC,    C206,   Balance);
		setJump(JUMPS[54 ], SPC,    C206,   Balance);
		setJump(JUMPS[55 ], SPC,    C206,   Fury);
		setJump(JUMPS[56 ], SPC,    C206,   Balance);
		setJump(JUMPS[57 ], SPC,    C206,   Balance);
		setJump(JUMPS[58 ], SPC,    C206,   Balance);
		setJump(JUMPS[59 ], SPC,    C206,   Balance);
		setJump(JUMPS[60 ], Perris, TwinOt, Balance);
		setJump(JUMPS[61 ], Perris, TwinOt, Balance);
		setJump(JUMPS[62 ], Perris, TwinOt, Balance);
		setJump(JUMPS[63 ], Perris, TwinOt, Balance);
		setJump(JUMPS[64 ], Perris, Skyvan, Balance);
		setJump(JUMPS[65 ], Perris, Skyvan, Balance);
		setJump(JUMPS[66 ], Perris, TwinOt, Balance);
		setJump(JUMPS[67 ], Perris, TwinOt, Balance);
		setJump(JUMPS[68 ], Perris, TwinOt, Balance);
		setJump(JUMPS[69 ], Perris, Baloon, Balance);
		setJump(JUMPS[70 ], Perris, TwinOt, Balance);
		setJump(JUMPS[71 ], Perris, Skyvan, Balance);
		setJump(JUMPS[72 ], Perris, TwinOt, Balance);
		setJump(JUMPS[73 ], Perris, TwinOt, Balance);
		setJump(JUMPS[74 ], Perris, TwinOt, Balance);
		setJump(JUMPS[75 ], Perris, TwinOt, Balance);
		setJump(JUMPS[76 ], Perris, TwinOt, Balance);
		setJump(JUMPS[77 ], Perris, TwinOt, Balance);
		setJump(JUMPS[78 ], Perris, TwinOt, Balance);
		setJump(JUMPS[79 ], Perris, Skyvan, Balance);
		setJump(JUMPS[80 ], Perris, Skyvan, Balance);
		setJump(JUMPS[81 ], Perris, Skyvan, Balance);
		setJump(JUMPS[82 ], Perris, TwinOt, Balance);
		setJump(JUMPS[83 ], Perris, Skyvan, Balance);
		setJump(JUMPS[84 ], Perris, TwinOt, Balance);
		setJump(JUMPS[85 ], Perris, TwinOt, Balance);
		setJump(JUMPS[86 ], Perris, Skyvan, Balance);
		setJump(JUMPS[87 ], Perris, Skyvan, Balance);
		setJump(JUMPS[88 ], Perris, TwinOt, Balance);
		setJump(JUMPS[89 ], Perris, TwinOt, Balance);
		setJump(JUMPS[90 ], Perris, TwinOt, Balance);
		setJump(JUMPS[91 ], Perris, Skyvan, Balance);
		setJump(JUMPS[92 ], Perris, Skyvan, Balance);
		setJump(JUMPS[93 ], Perris, TwinOt, Balance);
		setJump(JUMPS[94 ], Perris, TwinOt, Balance);
		setJump(JUMPS[95 ], Perris, TwinOt, Balance);
		setJump(JUMPS[96 ], Perris, TwinOt, Balance);
		setJump(JUMPS[97 ], Perris, TwinOt, Balance);
		setJump(JUMPS[98 ], SPC,    C206,   Balance);
		setJump(JUMPS[99 ], SPC,    C206,   Fury);
		setJump(JUMPS[100], SPC,    C206,   Spectre);
		setJump(JUMPS[101], SPC,    C206,   Spectre);
		setJump(JUMPS[102], SPC,    C206,   Spectre);
		setJump(JUMPS[103], SPC,    C206,   Spectre);
		setJump(JUMPS[104], SPC,    C206,   Spectre);
		setJump(JUMPS[105], SPC,    C206,   Spectre);
		setJump(JUMPS[106], SPC,    C206,   Spectre);
		setJump(JUMPS[107], SPC,    C206,   Spectre);
		setJump(JUMPS[108], SPC,    C206,   Spectre);
		setJump(JUMPS[109], SPC,    C206,   Spectre);
		setJump(JUMPS[110], SPC,    C206,   Spectre);
		setJump(JUMPS[111], SPC,    C206,   Spectre);
		setJump(JUMPS[112], SPC,    C206,   Spectre);
		setJump(JUMPS[113], SPC,    C206,   Spectre);
		setJump(JUMPS[114], SPC,    C206,   Spectre);
		setJump(JUMPS[115], SPC,    C206,   Spectre);
		setJump(JUMPS[116], SPC,    C206,   Spectre);
		setJump(JUMPS[117], SPC,    C206,   Spectre);
		setJump(JUMPS[118], SPC,    C206,   Spectre);
		setJump(JUMPS[119], SPC,    C206,   Spectre);
		setJump(JUMPS[120], SPC,    C206,   Spectre);
		setJump(JUMPS[121], SPC,    C206,   Spectre);
		setJump(JUMPS[122], SPC,    C206,   Spectre);
		setJump(JUMPS[123], SPC,    C206,   Spectre);
		setJump(JUMPS[124], SPC,    C206,   Spectre);
		setJump(JUMPS[125], SPC,    C206,   Spectre);
		setJump(JUMPS[126], SPC,    Porter, Triatha);
		setJump(JUMPS[127], SPC,    Porter, Triatha);
		setJump(JUMPS[128], SPC,    Porter, Triatha);
		setJump(JUMPS[129], SPC,    Porter, Triatha);
		setJump(JUMPS[130], SPC,    Porter, Triatha);
		setJump(JUMPS[131], SPC,    Porter, Triatha);
		setJump(JUMPS[132], SPC,    Porter, Triatha);
		setJump(JUMPS[133], SPC,    Porter, Triatha);
		setJump(JUMPS[134], SPC,    Porter, Spectre);
		setJump(JUMPS[135], SPC,    Porter, Triatha);
		setJump(JUMPS[136], SPC,    Porter, Spectre);
		setJump(JUMPS[137], SPC,    Porter, Triatha);
		setJump(JUMPS[138], SPC,    Porter, Spectre);
		setJump(JUMPS[139], SPC,    Porter, Spectre);
		setJump(JUMPS[139], SPC,    Porter, Spectre);
		setJump(JUMPS[140], SPC,    C206,   Spectre);
		setJump(JUMPS[141], SPC,    C206,   Spectre);
		setJump(JUMPS[142], Geese,  C208,   Sabre2);
		setJump(JUMPS[143], Geese,  C208,   Sabre2);

		res.bulkInsert(Jumps.CONTENT_URI, JUMPS);
	}

	public TestData(Activity activity) {
		mResolver = activity.getContentResolver();
		delete();
		insert();
	}

	public void delete() {
		ContentResolver resolver = mResolver;
		resolver.delete(Jumps.CONTENT_URI, null, null);
		resolver.delete(Places.CONTENT_URI, null, null);
		resolver.delete(Aircrafts.CONTENT_URI, null, null);
		resolver.delete(Equipment.CONTENT_URI, null, null);
	}

	private static class PlaceInfo {

		ContentValues values;
		
		public PlaceInfo(String name) {
			values = new ContentValues();
			values.put(Places.PLACE_NAME, name);
		}
		
		public ContentValues getContentValues() {
			return values;
		}
	}

	private static class AircraftInfo {

		ContentValues values;
		
		public AircraftInfo(String name) {
			values = new ContentValues();
			values.put(Aircrafts.AIRCRAFT_NAME, name);
		}
		
		public ContentValues getContentValues() {
			return values;
		}
	}

	private static class EquipmentInfo {

		ContentValues values;

		public EquipmentInfo(String name, int size) {
			values = new ContentValues();
			values.put(Equipment.EQUIPMENT_CANOPY_NAME, name);
			values.put(Equipment.EQUIPMENT_CANOPY_SIZE, size);
		}
		
		public ContentValues getContentValues() {
			return values;
		}
	}

}
