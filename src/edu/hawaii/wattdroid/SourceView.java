package edu.hawaii.wattdroid;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
  private TextView tv;
  private List<String> information = new ArrayList<String>();
  
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
    mRedrawHandler.sleep(1000);
    try {
      /* Create a REST locations we want to load xml-data from. */
      ArrayList<URL> urlList = new ArrayList<URL>();
      urlList.add(new URL("http://server.wattdepot.org:8186/wattdepot/sources/" + source));
      urlList.add(new URL("http://server.wattdepot.org:8186/wattdepot/sources/" + source
          + "/summary"));
      ParsedExampleDataSet parsedExampleDataSet = null;
      String displayStats = new String();
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
        displayStats += parsedExampleDataSet.toString();
        
        information.addAll(parsedExampleDataSet.getSourceList());
      }
      
      /* Set the result to be displayed in our GUI. */
//      tv.setText(displayStats);
      tv.setText("Source: "+ information.get(0)+"\n");
//      tv.append("\n1: "+information.get(1));
//      tv.append("\n2: "+information.get(2));
//      tv.append("\n3: "+information.get(3));
//      tv.append("\n4: "+information.get(4));
      tv.append("\nLocation: "+information.get(5)+"\n");
      tv.append("\nMeter Desc: "+information.get(6)+"\n");
      tv.append("\nReading: "+information.get(7) +" Watts");
      Log.d("wattdroid", "Displaying Source XML");
    }
    catch (Exception e) {
      /* Display any Error to the GUI. */
      tv.setText("Error" + e.getMessage());
      Log.e(MY_DEBUG_TAG, "wattdroid", e);
    }
    
  }

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    setContentView(R.layout.sources);
    this.tv = (TextView) this.findViewById(R.id.sourcetext);
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      source = extras.getString("source");
    }
    updateUI();
  }

  @Override
  public void onStop() {
    super.onStop();
  }

}
