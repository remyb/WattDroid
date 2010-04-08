package edu.hawaii.wattdroid;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class ParsedExampleDataSet {
    private String extractedString = null;
    private int extractedInt = 0;
    private List<String> sourceList = new ArrayList<String>();

    public String getExtractedString() {
         return extractedString;
    }
    public void setExtractedString(String extractedString) {
    	Log.i("wattdroid", "adding sting " + extractedString);
    	sourceList.add(extractedString.substring(extractedString.lastIndexOf("/")+1));
        this.extractedString = extractedString;
    }
    
    public String[] getAllSources() {
    	return this.sourceList.toArray(new String[0]);
    }
    
    public String[] getAllLocations() {
    	// STUB: Return the list of source names
    	return null;
    }

    public int getExtractedInt() {
         return extractedInt;
    }
    public void setExtractedInt(int extractedInt) {
         this.extractedInt = extractedInt;
    }
    
    public String toString(){
    	String allSources = "";
    	for (String source : sourceList) {
    		allSources += "\n"+ source;
    	}
        return "ExtractedString = " + allSources;
    }
}

