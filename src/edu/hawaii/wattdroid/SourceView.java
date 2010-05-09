package edu.hawaii.wattdroid;

import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

/**
 * Sourceview - displays sources in a list context. Uses the list adaptor and presents list of xml
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
   */
  private void updateUI() {
    // mRedrawHandler.sleep(Integer.getInteger(delay) * 1000);
    mRedrawHandler.sleep(10000);
    try {
      /* Create a REST locations we want to load xml-data from. */
      ArrayList<URL> urlList = new ArrayList<URL>();
      urlList.add(new URL("http://server.wattdepot.org:8186/wattdepot/sources/" + source));
      urlList.add(new URL("http://server.wattdepot.org:8186/wattdepot/sources/" + source
          + "/sensordata/latest" + "/summary"));
      ParsedExampleDataSet parsedExampleDataSet = null;
      /* Loop through both urls to get information and append to displayStats */
      for (URL url : urlList) {

        /* Get a SAXParser from the SAXPArserFactory. */
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();

        /* Get the XMLReader of the SAXParser we created. */
        XMLReader xr = sp.getXMLReader();

        /* Create a new ContentHandler and apply it to the XML-Reader */
        ExampleHandler myExampleHandler = new ExampleHandler();
        xr.setContentHandler(myExampleHandler);

        /* Parse the xml-data from our URL. */
        xr.parse(new InputSource(url.openStream()));

        /* Our ExampleHandler now provides the parsed data to us. */
        parsedExampleDataSet = myExampleHandler.getParsedData();

        /* Set the result to be displayed in our GUI. */
        if (parsedExampleDataSet.getName().toString() != null) {
          name.setText(parsedExampleDataSet.getName().toString());
        }
        if (parsedExampleDataSet.getLocation().toString() != null) {
          location.setText(parsedExampleDataSet.getLocation().toString());
        }
        if (parsedExampleDataSet.getDescription().toString() != null) {
          meter.setText(parsedExampleDataSet.getDescription().toString());
        }
        if (parsedExampleDataSet.getTotalSensorData().toString() != null) {
          reading.setText(parsedExampleDataSet.getTotalSensorData());
        }
        else {
          reading.setText("XML in different format Fix");
        }
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
    
    map.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
          Toast.makeText(getBaseContext(), 
                  "Display Google Maps", 
                  Toast.LENGTH_SHORT).show();
      }
    });
    
    if (extras != null) {
      source = extras.getString("source");
      // delay = extras.getInt("delay");
    }
    updateUI();
  }

  @Override
  public void onStop() {
    super.onStop();
  }
}
