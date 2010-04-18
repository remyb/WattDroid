package edu.hawaii.wattdroid;

import android.app.Activity;
import android.os.Bundle;

/**
 * About Menu Activity Class. Simple activity to display the about dialog.
 */
public class About extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.about);
    }
}