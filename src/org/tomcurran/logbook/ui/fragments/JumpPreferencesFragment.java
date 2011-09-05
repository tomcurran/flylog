package org.tomcurran.logbook.ui.fragments;

import org.tomcurran.logbook.R;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.widget.Toast;

public class JumpPreferencesFragment extends PreferenceFragment {

    public static final String JUMP_DELAY     = "default_jump_delay";
    public static final String JUMP_ALTITUDE  = "default_jump_altitude";
    public static final String JUMP_PLACE     = "default_place";
    public static final String JUMP_AIRCRAFT  = "default_aircraft";
    public static final String JUMP_EQUIPMENT = "default_equipment";

    private static final String NUMBERIC_REGEX = "\\d*";

    private Preference.OnPreferenceChangeListener numberCheckListener = new OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String newValueString = String.valueOf(newValue);
            if (!TextUtils.isEmpty(newValueString)  &&  newValueString.matches(NUMBERIC_REGEX)) {
                return true;
            } else {
                Toast.makeText(JumpPreferencesFragment.this.getActivity(), R.string.preference_jump_invalid_number, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceScreen root = getPreferenceScreen();
        ((EditTextPreference) root.findPreference(JUMP_ALTITUDE)).setOnPreferenceChangeListener(numberCheckListener);
        ((EditTextPreference) root.findPreference(JUMP_DELAY)).setOnPreferenceChangeListener(numberCheckListener);
    }

}
