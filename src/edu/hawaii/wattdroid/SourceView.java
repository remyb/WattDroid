package edu.hawaii.wattdroid;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

/**
 * Sourceview - displays sources in a list context. Uses the list adaptor and presents list of XML
 * sources from a wattdepot server.
 * 
 */
public class SourceView extends Activity {

  /**
   * Debug tag for log writing.
   */
  private final String MY_DEBUG_TAG = "wattdroid";
  private String source = null;
  // private Integer delay = null;
  private TextView reading;
  private TextView name;
  private TextView location;
  private TextView meter;
  private String coords;
  private Button map;

  /** Need handler for call-backs to the UI thread **/
  private RefreshHandler mRedrawHandler = new RefreshHandler();

  /**
   * Inner class of Sourceview thats purpose is to handle messages from threads and act upon them,
   * ie calling updateUI
   */
  class RefreshHandler extends Handler {
    /**
     * handleMessage - This is called in order to update the UI in a thread.
     * 
     * @param msg - an empty message that triggers the update
     */
    @Override
    public void handleMessage(Message msg) {
      SourceView.this.updateUI();
    }

    /**
     * Delays refresh for the param amount of time in ms.
     * 
     * @param delayMillis - num of ms to delay
     */
    public void sleep(long delayMillis) {
      this.removeMessages(0);
      if (!isFinishing()) {
        sendMessageDelayed(obtainMessage(0), delayMillis);
      }
    }
  };

  /**
   * updateUI - The background Thread that processes new XML information to display in the textview.
   * 
   * @TODO - duplicate code, break it up
   */
  private void updateUI() {
    // mRedrawHandler.sleep(Integer.getInteger(delay) * 1000);
    mRedrawHandler.sleep(10000);
    try {
      /* Create a REST locations we want to load xml-data from. */
      URL summaryURL = new URL("http://server.wattdepot.org:8186/wattdepot/sources/" + source);

      ParsedExampleDataSet summaryData = null;
      /* Loop through both urls to get information and append to displayStats */

      /* Get a SAXParser from the SAXPArserFactory. */
      SAXParserFactory saxSummaryParser = SAXParserFactory.newInstance();
      SAXParser sp1 = saxSummaryParser.newSAXParser();

      /* Get the XMLReader of the SAXParser we created. */
      XMLReader xr = sp1.getXMLReader();

      /* Create a new ContentHandler and apply it to the XML-Reader */
      ExampleHandler myExampleHandler = new ExampleHandler();
      xr.setContentHandler(myExampleHandler);

      /* Parse the xml-data from our URL. */
      xr.parse(new InputSource(summaryURL.openStream()));

      /* Our ExampleHandler now provides the parsed data to us. */
      summaryData = myExampleHandler.getParsedData();

      /* Set the result to be displayed in our GUI. */
      if (summaryData.getName().toString() != null) {
        name.setText(summaryData.getName().toString());
      }
      if (summaryData.getLocation().toString() != null) {
        location.setText(summaryData.getLocation().toString());
      }
      if (summaryData.getDescription().toString() != null) {
        meter.setText(summaryData.getDescription().toString());
      }
      /* Set Coordinates */
      if (summaryData.getCoords().toString() != null) {
        this.coords = summaryData.getCoords().toString();
      }
    }
    catch (Exception e) {
      Log.e(MY_DEBUG_TAG, "wattdroid", e);
    }

    try {
      /* Create a REST locations we want to load xml-data from. */
      URL powerURL =
          new URL("http://server.wattdepot.org:8186/wattdepot/sources/" + source
              + "/sensordata/latest" + "/summary");
      ParsedExampleDataSet parsedExampleDataSet = null;
      /* Loop through both urls to get information and append to displayStats */

      /* Get a SAXParser from the SAXPArserFactory. */
      SAXParserFactory spf = SAXParserFactory.newInstance();
      SAXParser sp = spf.newSAXParser();

      /* Get the XMLReader of the SAXParser we created. */
      XMLReader xr = sp.getXMLReader();

      /* Create a new ContentHandler and apply it to the XML-Reader */
      ExampleHandler myExampleHandler = new ExampleHandler();
      xr.setContentHandler(myExampleHandler);

      /* Parse the xml-data from our URL. */
      xr.parse(new InputSource(powerURL.openStream()));

      /* Our ExampleHandler now provides the parsed data to us. */
      parsedExampleDataSet = myExampleHandler.getParsedData();

      /* Set the result to be displayed in our GUI. */
      if (parsedExampleDataSet.getTotalSensorData().toString() != null) {
        reading.setText(parsedExampleDataSet.getTotalSensorData() + " watts");
      }
    }
    catch (Exception e) {
      Log.e(MY_DEBUG_TAG, "wattdroid", e);
    }
  }

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    setContentView(R.layout.sources);
    this.reading = (TextView) this.findViewById(R.id.energyreading);
    this.name = (TextView) this.findViewById(R.id.sourcename);
    this.location = (TextView) this.findViewById(R.id.location);
    this.meter = (TextView) this.findViewById(R.id.meterinfo);
    this.map = (Button) this.findViewById(R.id.map);

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      source = extras.getString("source");
      // delay = extras.getInt("delay");
    }

    /*
     * Geo Location URLS are not available on emulators by default to enable create a new AVD with
     * the commmand: android create avd -n my_androidMAPS -t 3
     */
    map.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if (coords.equals("0,0,0")) {
          Toast.makeText(getBaseContext(),
              "Server does not contain data for this location (0,0,0)", Toast.LENGTH_LONG).show();
        }
        else {
          String uri = "geo:" + coords;
          startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
        }
      }
    });
    updateUI();
  }

  @Override
  public void onStop() {
    super.onStop();
  }
}
