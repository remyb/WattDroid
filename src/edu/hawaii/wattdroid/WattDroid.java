package edu.hawaii.wattdroid;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView; //import android.widget.Toast;
import android.widget.Toast; //import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * WattDroid - Main WattDroid activity.
 * 
 * @author Remy Baumgarten
 * @author Kevin Chiogioji
 * 
 */
public class WattDroid extends ListActivity {
  /**
   * Debug tag for log writing.
   */
  private final String MY_DEBUG_TAG = "wattdroid";
  private static final int EDIT_ID = Menu.FIRST + 2;
  private String text = null;
  private String list = null;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    /* Create a new TextView to display the parsingresult later. */
    TextView tv = new TextView(this);
    // text = (TextView) findViewById(R.string.text);
    // list = (TextView) findViewById(R.string.list);
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    text = prefs.getString("text", "<undefined>");
    list = prefs.getString("list", "<undefined>");

    try {
      /* Create a URL we want to load some xml-data from. */
      Log.e("wattdroid", "URL from Prefs is: " + text.toString());

      Toast
          .makeText(getApplicationContext(), prefs.getString("text", "<unset>"), Toast.LENGTH_LONG)
          .show();
      if (text.compareTo("<undefined>") == 0) {
        Toast.makeText(getApplicationContext(), "Please set a URL in the Preferences",
            Toast.LENGTH_SHORT).show();
        Intent prefActivity = new Intent(this, EditPreferences.class);
        startActivity(prefActivity);
      }
      // Test URL: http://server.wattdepot.org:8182/wattdepot/sources.xml
      URL url = new URL(text);

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
      /* Parsing has finished. */

      /* Our ExampleHandler now provides the parsed data to us. */
      ParsedExampleDataSet parsedExampleDataSet = myExampleHandler.getParsedData();

      /* Set the result to be displayed in our GUI. */
      // tv.setText(parsedExampleDataSet.toString());
      String[] sources = parsedExampleDataSet.getAllSources();
      Log.d("wattdroid", "I just placed sources into an array");
      setListAdapter(new ArrayAdapter<String>(this, R.layout.item, sources));

      /* Following is for Toast temp spot, change this to use new activity */
      ListView lv = getListView();
      lv.setTextFilterEnabled(true);
      lv.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          Intent sourceview = new Intent(view.getContext(), SourceView.class);
          sourceview.putExtra("source", ((TextView) view).getText());
          startActivity(sourceview);
        }
      });

    }
    catch (Exception e) {
      Toast.makeText(getApplicationContext(), "Please set a URL in the Preferences",
          Toast.LENGTH_SHORT).show();
      Intent prefActivity = new Intent(this, EditPreferences.class);
      startActivity(prefActivity);
      /* Display any Error to the GUI. Will expand soon */
      // tv.setText("Please check your URL and Internet Connection and try again");
      // this.setContentView(tv);
      Log.e(MY_DEBUG_TAG, "wattdroid", e);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(Menu.NONE, EDIT_ID, Menu.NONE, "Edit Prefs").setIcon(R.drawable.misc)
        .setAlphabeticShortcut('e');

    return (super.onCreateOptionsMenu(menu));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case EDIT_ID:
      startActivity(new Intent(this, EditPreferences.class));
      return (true);
    }
    return (super.onOptionsItemSelected(item));
  }

  @Override
  public void onResume() {
    super.onResume();

    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

    text = prefs.getString("text", "<unset>");
    list = prefs.getString("list", "<unset>");
  }


}
