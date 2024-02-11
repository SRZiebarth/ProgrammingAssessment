package com.srziebarth;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.srziebarth.Candidate;
import com.srziebarth.Voter;

public class Election {
	
	private final List<Voter> voters;
	private final Map<String, Candidate> candidates;
	
	private final List<Map<Candidate, Integer>> resultVotes;
	private Candidate resultWinner;
	
	public Election() {
		this(new ArrayList<Voter>());
	}
	
	public Election(List<Voter> voters) {
		this.voters = voters;
		this.candidates = new HashMap<String, Candidate>();
		this.resultVotes = new ArrayList<Map<Candidate, Integer>>();
	}
	
	public List<Map<Candidate, Integer>> getResults() {
		return resultVotes;
	}
	
	public Candidate getWinner() {
		if (resultWinner != null) {
			return resultWinner;
		} else {
			throw new IllegalStateException("Election must be computed first.");
		}
	}

	public void addVoters(List<Voter> voters) {
		for (Voter vote : voters) {
			// Consolidate candidate objects
			// Ensures that there is only one candidate object per candidate instead of 100s of identical objects
			List<Candidate> ch = vote.GetChoices();			
			for (int i = 0; i < ch.size(); i++) {
				Candidate c = ch.get(i);
				if (this.candidates.containsKey(c.GetName().toUpperCase())) {
					ch.set(i, candidates.get(c.GetName().toUpperCase()));
				} else {
					candidates.put(c.GetName().toUpperCase(), c);
				}
			}
			
			this.voters.add(vote);
		}
	}
}
