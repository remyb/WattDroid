package edu.hawaii.wattdroid;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * ListView  is a ViewGroup  that creates a list of scrollable items.
 * The list items are automatically inserted to the list using a ListAdapter.
 * http://developer.android.com/resources/tutorials/views/hello-listview.html
 * 
 */
public class WattDroid extends Activity {
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.main);
	  Log.i("wattdroid", "Started wattdroid");
	  URL url = null;
	  try {
		url = new URL("http://server.wattdepot.org:8182/wattdepot/sources");
		Log.i("wattdroid", "success!");
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		/* Get the XMLReader of the SAXParser we created. */
	    XMLReader xr = sp.getXMLReader();
		
	    // removed content handler
	    
		Log.v("debugging","Hello World");
		/* Parse the xml-data from our URL */
		InputStream mystream = url.openStream();
		InputSource is = new InputSource(mystream);
		//xr.parse(is);
		/* Parsing has finished. */
		Log.i("wattdroid",is.toString());
		Log.i("wattdroid", "completed");

	  } catch (MalformedURLException e) {
		e.printStackTrace();
	  } catch (ParserConfigurationException e) {
		e.printStackTrace();
	  } catch (SAXException e) {
		e.printStackTrace();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
      
      

	  


	}
	
	

}