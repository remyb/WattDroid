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
  private String name = null;
  private String location = null;
  private String description = null;
  private String totalSensorData = null;
  private String coords = null;
  
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
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setLocation(String location) {
    this.location = location;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public void setTotalSensorData(String totalSensorData) {
    this.totalSensorData = totalSensorData;
  }
  
  public void setCoords(String coords) {
    this.coords = coords;
  }
  
  public String getName() {
    return name;
  }
  
  public String getLocation() {
    return this.location;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public String getTotalSensorData() {
    return this.totalSensorData;
  }

  public String getCoords() {
    return this.coords;
  }
  /**
   * Returns the source list.
   * 
   * @return list<string> source information
   */
  
  public List<String> getSourceList() {
    return this.sourceList;  
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
