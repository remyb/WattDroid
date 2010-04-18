package edu.hawaii.wattdroid;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Intro screen menu. Currently not being used.
 * 
 */
public class Intro extends Activity implements OnClickListener {
    /**
     * Called when first being created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.intro);
	MediaPlayer mp = MediaPlayer.create(this, R.raw.startup_sound);
	mp.start();
	// Setup Button Press Handlers
	View continueButton = findViewById(R.id.continue_button);
	continueButton.setOnClickListener(this);
	View aboutButton = findViewById(R.id.about_button);
	aboutButton.setOnClickListener(this);
	View exitButton = findViewById(R.id.exit_button);
	exitButton.setOnClickListener(this);
    }

    /**
     * Listeners for clicks on menu buttons.
     */
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.about_button:
	    Intent about = new Intent(this, About.class);
	    startActivity(about);
	    break;
	case R.id.continue_button:
	    Intent all = new Intent(this, WattDroid.class);
	    startActivity(all);
	    break;
	case R.id.exit_button:
	    finish();
	    break;
	}

    }

    /**
     * Gets called after pause.
     */
    @Override
    protected void onResume() {
	super.onResume();
	Music.play(this, R.raw.startup_sound);
    }

    /**
     * Gets called on pause.
     */
    @Override
    protected void onPause() {
	super.onPause();
	Music.stop(this);
    }
}
