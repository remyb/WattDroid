package edu.hawaii.wattdroid;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;


public class SourceView extends Activity {
	private final String MY_DEBUG_TAG = "wattdroid"; 

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
         super.onCreate(icicle);


         /* Create a new TextView to display the parsingresult later. */
         TextView tv = new TextView(this);
         Bundle extras = getIntent().getExtras(); 
         String source = "";
         if(extras != null) {
        	 source = extras.getString("source");
         }
    	
         try {
              /* Create a URL we want to load some xml-data from. */
        	 
              URL url = new URL("http://server.wattdepot.org:8182/wattdepot/sources/"+source);

              /* Get a SAXParser from the SAXPArserFactory. */
              SAXParserFactory spf = SAXParserFactory.newInstance();
              SAXParser sp = spf.newSAXParser();

              /* Get the XMLReader of the SAXParser we created. */
              XMLReader xr = sp.getXMLReader();
              /* Create a new ContentHandler and apply it to the XML-Reader*/
              ExampleHandler myExampleHandler = new ExampleHandler();
              xr.setContentHandler(myExampleHandler);
              
              /* Parse the xml-data from our URL. */
              xr.parse(new InputSource(url.openStream()));
              /* Parsing has finished. */

              /* Our ExampleHandler now provides the parsed data to us. */
              ParsedExampleDataSet parsedExampleDataSet =
                                            myExampleHandler.getParsedData();

              /* Set the result to be displayed in our GUI. */
              //tv.setText(parsedExampleDataSet.toString());
              //String[] sources = parsedExampleDataSet.getAllSources();
              Log.d("wattdroid", "Displaying Source XML");
              tv.setText(parsedExampleDataSet.toString()); 
              
         } catch (Exception e) {
              /* Display any Error to the GUI. */
              tv.setText("Whoops! WattDroid made a booboo!: " + e.getMessage());
              Log.e(MY_DEBUG_TAG, "wattdroid", e);
         }
         /* Display the TextView. */
         this.setContentView(tv);
    }
}
