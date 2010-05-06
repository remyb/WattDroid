package edu.hawaii.wattdroid;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Edit Preferences - used to set preferences.
 * 
 *
 */
public class EditPreferences extends PreferenceActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.layout.preferences);
  }
}
