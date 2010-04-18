package edu.hawaii.wattdroid;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

/**
 * ParsedExample - Receives extracted contents of XML from Example Handler and processes for the
 * view.
 * 
 */
public class ParsedExampleDataSet {
  /* Data Containers */
  private String extractedString = null;
  private int extractedInt = 0;
  private List<String> sourceList = new ArrayList<String>();

  /**
   * Getter.
   * 
   * @return string that was extracted.
   */
  public String getExtractedString() {
    return extractedString;
  }

  /**
   * Setter.
   * 
   * @param extractedString
   */
  public void setExtractedString(String extractedString) {
    Log.i("wattdroid", "adding sting " + extractedString);
    sourceList.add(extractedString.substring(extractedString.lastIndexOf("/") + 1));
    this.extractedString = extractedString;
  }

  /**
   * Setter - for inner content.
   * 
   * @param extractedString
   */
  public void setExtractedStringInnerContent(String extractedString) {
    Log.i("wattdroid", "adding sting from content " + extractedString);
    sourceList.add(extractedString);
    // this.extractedString = extractedString;
  }

  /**
   * Provides a list of all sources.
   * 
   * @return list of source
   */
  public String[] getAllSources() {
    return this.sourceList.toArray(new String[0]);
  }

  /**
   * Provides a list of locations.
   * 
   * @return
   */
  public String[] getAllLocations() {
    // STUB: Return the list of source names
    return null;
  }

  /**
   * Getter.
   * 
   * @return the extracted int
   */
  public int getExtractedInt() {
    return extractedInt;
  }

  /**
   * Setter.
   * 
   * @param extractedInt
   */
  public void setExtractedInt(int extractedInt) {
    this.extractedInt = extractedInt;
  }

  /**
   * For all sources to string.
   * 
   * @return the sources as one string
   */
  public String toString() {
    String allSources = "";
    for (String source : sourceList) {
      allSources += "\n" + source;
      Log.d("wattdroid", "returning source..." + source);
    }
    return allSources;
  }
}
