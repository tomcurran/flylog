package org.tomcurran.logbook.util;

import org.tomcurran.logbook.provider.LogbookContract;

import android.content.Context;
import android.database.Cursor;

public class DbAdapter {

	public static final String[] SELECTION_ARG_ZERO = { "0" };

	public static int getHighestJumpNumber(Context context) {
    	final Cursor cursor = context.getContentResolver().query(
                LogbookContract.Jumps.CONTENT_URI,
                HighestNumberQuery.PROJECTION,
                HighestNumberQuery.SELECTION,
                SELECTION_ARG_ZERO,
                LogbookContract.Jumps.DEFAULT_SORT
        );
    	int highestJumpNumber = cursor.moveToFirst() ? cursor.getInt(0) : 0;
    	cursor.close();
    	return highestJumpNumber;
    }

	private interface HighestNumberQuery {

		String[] PROJECTION = {
				"max(" + LogbookContract.Jumps.JUMP_NUMBER + ")"
		};

		String SELECTION = LogbookContract.Jumps.JUMP_NUMBER + ">?";
	}

}