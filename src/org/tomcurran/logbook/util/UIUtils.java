package org.tomcurran.logbook.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import org.tomcurran.logbook.R;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class UIUtils {


    private final static int DEFAULT_PARSE_TEXTVIEW_INT = 0;

    public static int parseTextViewInt(TextView view) {
        return parseTextViewInt(view, DEFAULT_PARSE_TEXTVIEW_INT);
    }

    public static int parseTextViewInt(TextView view, int alt) {
        String text = view.getText().toString();
        if (TextUtils.isEmpty(text)) {
            return alt;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return alt;
        }
    }


    private final static int DAY_SECONDS = 86400;
    private final static int HOUR_SECONDS = 3600;
    private final static int MINUTES_SECONDS = 60;

    public static String formatDelay(Context context, int totalSeconds) {
        int formatRes;
        int days    = (int) (TimeUnit.SECONDS.toDays(totalSeconds));
        int hours   = (int) (TimeUnit.SECONDS.toHours(totalSeconds) - TimeUnit.DAYS.toHours(days));
        int minutes = (int) (TimeUnit.SECONDS.toMinutes(totalSeconds) - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days));
        int seconds = (int) (totalSeconds - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days));
        if (totalSeconds >= DAY_SECONDS) {
            formatRes = R.string.text_format_days;
        } else if (totalSeconds >= HOUR_SECONDS) {
            formatRes = R.string.text_format_hours;
        } else if (totalSeconds >= MINUTES_SECONDS) {
            formatRes = R.string.text_format_minutes;
        } else {
            formatRes = R.string.text_format_seconds;
        }
        return String.format(context.getString(formatRes),
                seconds,
                minutes,
                hours,
                days);
    }

    public static int roundAltitude(int altitude) {  
        int subtract = altitude % 100;
        int result = altitude - subtract;
        if(subtract >= 500) {
            result += 100;
         }
        return result;
    }

    private static NumberFormat nf = NumberFormat.getIntegerInstance();

    public static String formatAltitude(Context context, int altitude) {
        return nf.format(altitude) + context.getString(R.string.text_format_feet);
    }
}
