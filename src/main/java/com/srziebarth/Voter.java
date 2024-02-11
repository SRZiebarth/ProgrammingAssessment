package com.srziebarth;

import java.util.List;

import com.srziebarth.Candidate;

public class Voter {
	
	int ID;
	List<Candidate> voterChoices;
	
	public Voter(int ID, List<Candidate> voterChoices) {
		this.ID = ID;
		this.voterChoices = voterChoices;
	}
	
	public int GetID() {return ID;}
	public void SetID(int newID) {ID = newID;}
	
	public List<Candidate> GetChoices() {return voterChoices;}
}
