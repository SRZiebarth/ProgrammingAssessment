package com.srziebarth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.srziebarth.Election;
import com.srziebarth.Voter;
import com.srziebarth.CSVReader;

public class UI {
	
	public void start() {
		System.out.println("Running with UI.\n");
		
		boolean running = true;
		while (running) {
			Election election = new Election();
			
			// Loop prompt before election
			boolean fileSelecting = true;
			while (fileSelecting) {
				System.out.print("Welcome! Please choose an option (A = Add File, R = Run Election, or E = Exit): ");
		        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		        String line;
		        try {
					line = reader.readLine();
				} catch (IOException exception) {
					exception.printStackTrace();
					continue;
				}
		        
		        char letter = line.toLowerCase().charAt(0);
		        switch (letter) {
		        case 'a':
		        	System.out.print("Please enter a filename: ");
			        try {
						line = reader.readLine();
					} catch (IOException exception) {
						exception.printStackTrace();
						continue;
					}			        
			        System.out.println("Importing " + line + "...");
					try {
						CSVReader p = new CSVReader(line);
						List<Voter> voters = p.ParseVoters();
						election.addVoters(voters);
						System.out.println("	" + voters.size() + " votes successfully imported.");
					} catch (FileNotFoundException exception) {
						System.out.println("WARNING: Could not find file on disk: " + line);
					} catch (Exception exception) {
						System.out.println("WARNING: An unknown error has occurred!");
					}
		        	break;
		        case 'r':
		        	fileSelecting = false;
		        	break;
		        case 'e':
		        	return;
		        default:
		        	System.out.println("'"+letter+"' is not an option. Please try again.");
		        }
			}
			
			System.out.println("Running election...");
			election.compute();
			System.out.println();
			
			// Loop prompt after election
			boolean postCompute = true;
			while (postCompute) {
				System.out.println();
				System.out.print("Please choose another option (W = Show Winner, S = Save Results, N = New Election, E = Exit): ");
		        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		        String line;
		        try {
					line = reader.readLine();
				} catch (IOException exception) {
					exception.printStackTrace();
					continue;
				}
		        
		        char letter = line.toLowerCase().charAt(0);
		        switch (letter) {
		        case 'w':
		        	System.out.println();
		        	System.out.println("The Election Winner is: " + election.GetWinner().GetName());
		        	break;
		        case 's':
		        	System.out.print("Please enter a filename: ");
			        try {
						line = reader.readLine();
					} catch (IOException exception) {
						exception.printStackTrace();
						continue;
					}
			        ElectionToFile toFile = new ElectionToFile(election, new File(line));
			        try {
						toFile.write();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
		        	break;
		        case 'n':
		        	postCompute = false;
		        	break;
		        case 'e':
		        	return;
		        default:
		        	System.out.println("'"+letter+"' is not an option. Please try again.");
		        }
			}
		}
	}

}
