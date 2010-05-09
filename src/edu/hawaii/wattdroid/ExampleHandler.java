package edu.hawaii.wattdroid;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;

/**
 * Handler for XML - this is called by SAX. This is used to handle and traverse/grep certain
 * elements/content in an XML file.
 * 
 */
public class ExampleHandler extends DefaultHandler {

  /**
   * Switch fields - used to check location of xml
   */
  private boolean in_outertag = false;
  private boolean in_innertag = false;
  private boolean name = false;
  private boolean location = false;
  private boolean description = false;
  private boolean timestamp = false;
  private boolean foundValue = false;
  private boolean coords = false;
  private boolean foundKey = false;
  private boolean foundConsumedKey = false;

  /**
   * The parsed data.
   */
  private ParsedExampleDataSet myParsedExampleDataSet = new ParsedExampleDataSet();

  /**
   * Getter - parsed data.
   * 
   * @return the extracted data
   */
  public ParsedExampleDataSet getParsedData() {
    return this.myParsedExampleDataSet;
  }

  /**
   * Called when new XML is loaded.
   */
  @Override
  public void startDocument() throws SAXException {
    this.myParsedExampleDataSet = new ParsedExampleDataSet();
  }

  /**
   * Called when xml ends.
   */
  @Override
  public void endDocument() throws SAXException {
    // Nothing to do
  }

  /**
   * Called when opening tags like: <tag> provide attributes.
   * 
   * @param namespaceURI - namespace
   * @param localName - xml tag
   * @param atts - attributes
   * 
   */
  @Override
  public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
      throws SAXException {
    if (localName.equals("SourceIndex") || localName.equals("SensorData")) {
      this.in_outertag = true;
    }
    else if (localName.equals("SourceRef")) {
      this.in_innertag = true;
      String attrValue = atts.getValue("Href");
      myParsedExampleDataSet.setExtractedString(attrValue);
    }
    else if (localName.equals("Name")) {
      name = true;
    }
    else if (localName.equals("Location")) {
      location = true;
    }
    else if (localName.equals("Description")) {
      description = true;
    }
    else if (localName.equals("Coordinates")) {
      coords = true;
    }
    else if (localName.equals("Key")) {
      Log.d("wattdroid", "I Found a Key");
      foundKey = true;
    }
    else if (localName.equals("Value")) {
      Log.d("wattdroid", "I Found a Value");
      foundValue = true;
    }
  }

  /**
   * Called on closing tags like: </tag>.
   * 
   * @param namespaceURI - namespace
   * @param localName - xml tag
   * @param atts = attributes
   */
  @Override
  public void endElement(String namespaceURI, String localName, String qName) throws SAXException {

    if (localName.equals("SourceIndex") || localName.equals("SensorData")) {
      this.in_outertag = false;
    }
    else if (localName.equals("SourceRef")) {
      this.in_innertag = false;
    }
    else if (localName.equals("Name")) {
      name = false;
    }
    else if (localName.equals("Location")) {
      location = false;
    }
    else if (localName.equals("Description")) {
      description = false;
    }
    else if (localName.equals("Coordinates")) {
      coords = false;
    }
    else if (localName.equals("Key")) {
      foundKey = false;
      Log.d("wattdroid", "I Dismissed a Key");
    }
    else if (localName.equals("Value")) {
      foundValue = false;
      Log.d("wattdroid", "I Dismissed a Value");
    }
  }

  /**
   * Called on the following structure: <tag>characters</tag>.
   * 
   * @param ch - characters in tag
   * @param start - where to start
   * @param length - how many to copy
   */
  @Override
  public void characters(char ch[], int start, int length) {
    String test = new String(ch, start, length);
    if (in_innertag) {
      myParsedExampleDataSet.setExtractedStringInnerContent(new String(ch, start, length));
    }
    else if (name) {
      myParsedExampleDataSet.setName(new String(ch, start, length));
      Log.d("wattdroid", "I set the name " + test);
    }
    else if (location) {
      myParsedExampleDataSet.setLocation(new String(ch, start, length));
      Log.d("wattdroid", "I set the location " + test);
    }
    else if (description) {
      myParsedExampleDataSet.setDescription(new String(ch, start, length));
      Log.d("wattdroid", "I set the desc " + test);
    }
    else if (coords) {
      myParsedExampleDataSet.setCoords(new String(ch, start, length)); // set coords
      Log.d("wattdroid", "I set the coords " + test);
    }
    else if (foundKey && test.equals("powerConsumed")) {
      Log.d("wattdroid", "foundKey is True and powerConsumed is Current Tag");
      foundConsumedKey = true;
    }
    else if (foundValue && foundConsumedKey) {
      myParsedExampleDataSet.setTotalSensorData(new String(ch, start, length));
      foundConsumedKey = false;
      Log.d("wattdroid", "****I set the sensordata " + test);
    }
  }
}
