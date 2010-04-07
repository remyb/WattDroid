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
    	sourceList.add(extractedString);
        this.extractedString = extractedString;
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

