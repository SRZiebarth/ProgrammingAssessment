package com.srziebarth;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.srziebarth.Voter;
import com.srziebarth.Candidate;


public class BallotProcessor 
{
	
    public static void main( String[] args )
    {
        System.out.println( "Starting Elections..." );
        System.out.println();
        
//        BallotProcessor test = new BallotProcessor();
//        test.Test();
        UI ui = new UI();
        ui.start();
    }
    
    public void Test() {
//		int ID1 = 1001;
//		int ID2 = 1002;
//		int ID3 = 1003;
//		
//		Candidate candidate1 = new Candidate("Dalinar Kholin");
//		Candidate candidate2 = new Candidate("Jasnah Kholin");
//		Candidate candidate3 = new Candidate("Renarin Kholin");
//		Candidate candidate4 = new Candidate("Kaladin Stormblessed");
//		Candidate candidate5 = new Candidate("Torol Sadeas");
//		Candidate candidate6 = new Candidate("Meridas Amaram");
//		
//		Voter voter1 = new Voter(ID1, Arrays.asList(candidate1, candidate2, candidate3));
//		Voter voter2 = new Voter(ID2, Arrays.asList(candidate1, candidate2, candidate4));
//		Voter voter3 = new Voter(ID3, Arrays.asList(candidate5, candidate6, candidate3));
		
//		Election election = new Election();
//		election.addVoters(Arrays.asList(voter1, voter2, voter3));
//		election.compute();y

		
//    	CSVReader test = new CSVReader("SmallListBallots.csv");
//    	try {
//			List<Voter> test2 = test.parseVoters();
//			for (Voter v : test2) {
//				System.out.println(v.GetID());
//				for (Candidate c : v.GetChoices()) {
//					System.out.println(c.GetName());
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
    	List<Voter> votes = new ArrayList();
    	//CSVReader test = new CSVReader("SmallListBallots.csv");
    	CSVReader test = new CSVReader("LargeListBallots.csv");
    	try {
    		votes = test.ParseVoters();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Election election = new Election();
		election.addVoters(votes);
		election.compute();
	}
}
