package com.srziebarth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.srziebarth.Election;
import com.srziebarth.Candidate;

public class ElectionToFile {
	
	private final Election election;
	private final File file;
	
	public ElectionToFile(Election election, File file) {
		this.election = election;
		this.file = file;
	}
	
	public void write() throws IOException {
		FileWriter writer = new FileWriter(file);
		List<Map<Candidate, Integer>> results = election.GetResults();
		
		writer.write("winner," + election.GetWinner().GetName()+"\n");
		
		for (Map<Candidate, Integer> round : results) {
			writer.write("Round " + results.indexOf(round)+1 + ",\n");
			round.forEach((candidate, votes) -> {
				try {
					writer.write(candidate.GetName() + "," + votes + "\n");
				} catch (IOException election) {
					election.printStackTrace();
				}
			});
		}
		
		writer.close();
	}


}
