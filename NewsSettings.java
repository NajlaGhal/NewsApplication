package com.example.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class NewsSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.set_main);


            ListPreference section = (ListPreference) findPreference(getString(R.string.section_key));

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

            // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
            String sectionPref = sharedPrefs.getString(
                    getString(R.string.section_key),
                    getString(R.string.section_default));

            String[] values = getResources().getStringArray(R.array.sectionValues);

            for (int i = 0; i < values.length; i++) {
                if ( sectionPref.equals(values[i])){
                    section.setValueIndex(i);
                }
            }


           bindPreferenceSummaryToValue(section);
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "section");
            onPreferenceChange(preference, preferenceString);
        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String stringValue = o.toString();
            String stringEntry = "";
            String[] values = getResources().getStringArray(R.array.sectionValues);
            String[] entries = getResources().getStringArray(R.array.sectionEntries);

            for (int i = 0; i < values.length; i++) {
                if ( stringValue.equals(values[i])){
                    stringEntry = entries[i];
                }
            }
            preference.setSummary(stringEntry);
            return true;
        }
    }
}
