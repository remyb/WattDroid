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
  private boolean in_mytag = false;

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
    if (localName.equals("SourceIndex")) {
      this.in_outertag = true;
    }
    else if (localName.equals("SourceRef")) {
      this.in_innertag = true;
      String attrValue = atts.getValue("Href");
      myParsedExampleDataSet.setExtractedString(attrValue);
    }
    else if (localName.equals("Name") || localName.equals("Owner") || localName.equals("Public")
        || localName.equals("Virtual") || localName.equals("Coordinates")
        || localName.equals("Location") || localName.equals("Description")
        || localName.equals("TotalSensorDatas")) {
      Log.d("wattdroid", "I found a Public...");
      this.in_mytag = true;
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
    if (localName.equals("SourceIndex") || localName.equals("SourceSummary")) {
      this.in_outertag = false;
    }
    else if (localName.equals("SourceRef")) {
      this.in_innertag = false;
    }
    else if (localName.equals("Name") || localName.equals("Owner") || localName.equals("Public")
        || localName.equals("Virtual") || localName.equals("Coordinates")
        || localName.equals("Location") || localName.equals("Description")
        || localName.equals("TotalSensorDatas")) {
      this.in_mytag = false;
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
    Log.d("wattdroid", "I found source content to parse...");
    if (this.in_mytag) {
      Log.d("wattdroid", "Found mytag...setting extractedStringInnerContent...");
      myParsedExampleDataSet.setExtractedStringInnerContent(new String(ch, start, length));
    }
  }
}
