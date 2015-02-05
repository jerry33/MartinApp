package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import config.Config;
import data.Match;

public class MatcherUtil {
	
	
	public static Match findMatches(String pathProgram, String pathDB, boolean isAdvancedMatching) {
		
		Match match = Match.getInstance();
			
		StringBuilder sbProgram = new StringBuilder();
		StringBuilder sbDB = new StringBuilder();
		
		ArrayList<Integer> programFileMatches = new ArrayList<>();
		ArrayList<Integer> dbFileMatches = new ArrayList<>();
		ArrayList<String> wordMatches = new ArrayList<>();
		
		int matchesArray[][] = null; //array to be returned
		
		BufferedReader bfProgram = null;
		BufferedReader bfDB = null;
		
		try {
			bfProgram = new BufferedReader(new FileReader(pathProgram));
			bfDB = new BufferedReader(new FileReader(pathDB));
			String programLine;
			String dBLine;
			String cleanProgramLine = "";
			String cleanDBLine = "";
			
			while((programLine = bfProgram.readLine()) != null) {
				cleanProgramLine = java.text.Normalizer.normalize(programLine, java.text.Normalizer.Form.NFD);
				cleanProgramLine = cleanProgramLine.toLowerCase();
				cleanProgramLine = cleanProgramLine.replaceAll("[^\\p{ASCII}]", "");
				sbProgram.append(cleanProgramLine + Config.NEW_LINE_HASH + "\n");
			}
			
//			String[] programArray = sbProgram.toString().split(":\\s|\t| |\n|_|\\.|!|-|\\d{2}\\:\\d{2}|\\d{2,}|\\,");
			String[] programArray = sbProgram.toString().split("[\\W+&&[^@#]]|\\d{3,}");

			while((dBLine = bfDB.readLine()) != null) {
				cleanDBLine = java.text.Normalizer.normalize(dBLine, java.text.Normalizer.Form.NFD);
				cleanDBLine = cleanDBLine.toLowerCase();
				cleanDBLine = cleanDBLine.replaceAll("[^\\p{ASCII}]", "");
				sbDB.append(cleanDBLine + Config.NEW_LINE_HASH + "\n");
			}

//			String[] dBArray = sbDB.toString().split(":\\s|\t| |\n|_|\\.|!|-|\\d{2}\\:\\d{2}|\\d{2,}|\\,");
			String[] dBArray = sbDB.toString().split("[\\W+&&[^@#]]|\\d{3,}");
			
			boolean newLineInNextIteration = false;
			int currProgramLine = 1;
			
			boolean isFound = false; // founded occurence at line x
			
			for(int i = 0; i < programArray.length; i++) {
				
				if(newLineInNextIteration) {
					currProgramLine++;
					isFound = false; //reset isFound only when another line is coming up... otherwise results would be duplicated (as there may be more matching words on the same line)
					newLineInNextIteration = false;
				}
				
				int currDBLine = 1;
				System.out.println(programArray[i]);
				
				if(programArray[i].contains(Config.NEW_LINE_HASH)) newLineInNextIteration = true;
				
				for(int j = 0; j < dBArray.length; j++) {
					int counter = 1;
					
					if(dBArray[j].contains(Config.NEW_LINE_HASH)) {
						currDBLine++;
						if(isAdvancedMatching) {
							isFound = false; //detailed matching
						}
					}
					
//					System.out.println("old word = " + dBArray[j]);
					String newDBString = dBArray[j].replaceAll(Config.NEW_LINE_HASH + "|a0|a1|\\[|\\]|\\/", ""); //so that it matches rosselova with rosselova@#
					String newProgramString = programArray[i].replaceAll(Config.NEW_LINE_HASH + "|a0|a1|\\[|\\]|\\/", "");
//					System.out.println("newDBString = " + newDBString);
//					newDBString = newDBString.replaceAll("a0|a1|\\[|\\]|/", "");
//					newProgramString = newProgramString.replaceAll("a0|a1|\\[|\\]|/", "");
//					if(dBArray[j].length() >= 3 && (programArray[i].contains(newString) && newString.length() >= 3) ) { // && !wasAlreadyFound
					
					if(dBArray[j].length() >= 3 && newProgramString.equals(newDBString)) {  //(programArray[i].contains(newString) && newString.length() >= 3) ) { // && !wasAlreadyFound
						
						if(newDBString.equals("pri") || newDBString.equals("pre") || newDBString.equals("tom") || newDBString.equals("nie")) {
							continue;
						}
						
//						System.out.println("programArray[" + i + "] = " + programArray[i]);
//						System.out.println("newProgramString = " + newProgramString);
						
						String lineMatch = "";
						BufferedReader bfProgramNew = new BufferedReader(new FileReader(pathProgram));
						
						while((lineMatch = bfProgramNew.readLine()) != null && !isFound) {
							if(currProgramLine == counter) {
								programFileMatches.add(Integer.valueOf(currProgramLine));
								wordMatches.add(newDBString);
								// if there's only one matching word, it's considered to be in the next line (because of currDBLine++), that's why currDBLine-1 in the next line
								dbFileMatches.add((dBArray[j].contains(Config.NEW_LINE_HASH)) ? Integer.valueOf(currDBLine-1) : Integer.valueOf(currDBLine));
								isFound = true;
								break;
							}
							counter++;
						}
						bfProgramNew.close();
					}
				}
			}
			
			if(programFileMatches.size() == dbFileMatches.size()) {
				System.out.println("OK, pocet zhod je v oboch suboroch rovnaky");
			} else {
				System.out.println("ERROR, nerovnaky pocet zhod v suboroch");
			}
			
			matchesArray = new int[programFileMatches.size()][2];
			for(int i = 0; i < matchesArray.length; i++) {
				matchesArray[i][0] = programFileMatches.get(i).intValue();
				matchesArray[i][1] = dbFileMatches.get(i).intValue();
			}
			
			match.setMatches(matchesArray);
			match.setMatchingWords(wordMatches);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				bfProgram.close();
				bfDB.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//			int[][] testArray = new int[][]{{5,6}, {6,8}, {7,10}};
		
		return match;
	}
}
