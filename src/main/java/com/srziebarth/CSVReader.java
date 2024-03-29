package com.srziebarth;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.io.CsvMapReader;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;

import com.srziebarth.Voter;

public class CSVReader {
	
	private final String file;
	
	public CSVReader(String filename) {
		this.file = filename;
	}
	
	private static CellProcessor[] GetProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
				new Optional(), // first choice
                new Optional(), // second choice
                new Optional(), // third choice
                new ParseInt() // voter ID
        };
        
        return processors;
	}
	
	public List<Voter> ParseVoters() throws Exception{
		List<Voter> voters = new ArrayList<Voter>();
		
		ICsvMapReader mapReader = null;
        try {
                mapReader = new CsvMapReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
                
                // The header columns are used as the keys to the map
                final String[] header = mapReader.getHeader(true);
                final CellProcessor[] processors = GetProcessors();
                
                Map<String, Object> votesMap;
                Map<String, Candidate> candidatesMap = new HashMap<>();
                
                while( (votesMap = mapReader.read(header, processors)) != null ) {
                	Candidate firstChoice;
                	String firstChoiceName = (String) votesMap.get("First Choice");
                	if (firstChoiceName == null) {
                		firstChoice = null;
                	}
                	else if (candidatesMap.containsKey(firstChoiceName)) {
                		firstChoice = candidatesMap.get(firstChoiceName);
                	}
                	else {
                		firstChoice = new Candidate(firstChoiceName);
                		candidatesMap.put(firstChoiceName, firstChoice);
                	}
                	
                	Candidate secondChoice;
                	String secondChoiceName = (String) votesMap.get("Second Choice");
                	if (secondChoiceName == null) {
                		secondChoice = null;
                	}
                	else if (candidatesMap.containsKey(secondChoiceName)) {
                		secondChoice = candidatesMap.get(secondChoiceName);
                	}
                	else {
                		secondChoice = new Candidate(secondChoiceName);
                		candidatesMap.put(secondChoiceName, secondChoice);
                	}
                	
                	Candidate thirdChoice;
                	String thirdChoiceName = (String) votesMap.get("Third Choice");
                	if (thirdChoiceName == null) {
                		thirdChoice = null;
                	}
                	else if (candidatesMap.containsKey(thirdChoiceName)) {
                		thirdChoice = candidatesMap.get(thirdChoiceName);
                	}
                	else {
                		thirdChoice = new Candidate(thirdChoiceName);
                		candidatesMap.put(thirdChoiceName, thirdChoice);
                	}
                	
                	
                    Voter vote = new Voter((int) votesMap.get("VoterID"), Arrays.asList(firstChoice, secondChoice, thirdChoice));
                    voters.add(vote);
                }
                
        }
        finally {
                if( mapReader != null ) {
                        mapReader.close();
                }
        }
        
        return voters;
	}

}
