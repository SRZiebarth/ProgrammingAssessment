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
	
	public List<Map<Candidate, Integer>> GetResults() {
		return resultVotes;
	}
	
	public Candidate GetWinner() {
		if (resultWinner != null) {
			return resultWinner;
		} else {
			throw new IllegalStateException("Please run the Election first.");
		}
	}

	public void addVoters(List<Voter> voters) {
		for (Voter vote : voters) {
			// Consolidate candidate objects
			// Ensures that there is only one candidate object per candidate instead of hundreds of identical objects
			List<Candidate> choices = vote.GetChoices();			
			for (int i = 0; i < choices.size(); i++) {
				Candidate candidate = choices.get(i);
				if (candidate == null) {
					continue;
				}
				if (this.candidates.containsKey(candidate.GetName().toUpperCase())) {
					choices.set(i, candidates.get(candidate.GetName().toUpperCase()));
				} else {
					candidates.put(candidate.GetName().toUpperCase(), candidate);
				}
			}
			
			this.voters.add(vote);
		}
	}
	
	public void compute() {
		List<Candidate> remaining = new ArrayList<>(candidates.values());
		
		for (int round = 0; round < 3; round++) {
			System.out.println();
			System.out.println("Round: " + (round + 1));
			if (remaining.size() <= 1) {
				resultWinner = remaining.get(0);
				break;
			}
			
			// Create votes map
			Map<Candidate, Integer> votes = new HashMap<>();
			remaining.forEach((candidate) -> {
				votes.put(candidate, 0);
			});
			
			// Count votes
			final int[] totalVotesThisRound = new int[1]; // Lambda below can only work with final values
			for (Voter voter : voters) {
				for (int i = 0; i < round+1; i++) {
					if (remaining.contains(voter.GetChoices().get(i))) {
						totalVotesThisRound[0]++;
						int voteCount = votes.get(voter.GetChoices().get(i));
						votes.put(voter.GetChoices().get(i), voteCount + 1);
						break;
					}
				}
			}
			
			// Save votes map as a result for this round
			resultVotes.add(round, votes);
			
			// Print each candidate with their votes
			votes.forEach((candidate, voteCount) -> {
				System.out.println(candidate.GetName() + ": " + voteCount);
			});
			
			// Check for winner
			votes.forEach((candidate, voteCount) -> {
				if (voteCount >= (totalVotesThisRound[0] / 2)) {
					resultWinner = candidate;
					System.out.println("The Election Winner is: " + candidate.GetName());
					return;
				}
			});			
			
			// Run eliminations
			if (round >= 2) {
				// Eliminate all but max
				final Candidate[] highestVotes = new Candidate[1]; // This needs to be final to use in lambda
				votes.forEach((candidate, voteCount) -> {
					if (highestVotes[0] == null || votes.get(highestVotes[0]) > voteCount) {
						remaining.remove(highestVotes[0]);
						highestVotes[0] = candidate;
						resultWinner = candidate;
					}
				});
			} else {
				// Eliminate zero votes and lowest voted
				final Candidate[] lowestVotes = new Candidate[1]; // This needs to be final to use in lambda
				votes.forEach((candidate, voteCount) -> {
					if (voteCount == 0) {
						remaining.remove(candidate);
					} else {
						if (lowestVotes[0] == null || votes.get(lowestVotes[0]) > voteCount) {
							lowestVotes[0] = candidate;
						}
					}
				});
				remaining.remove(lowestVotes[0]);
			}
		}
	}
}
