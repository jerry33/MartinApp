package data;

import java.util.ArrayList;

public class Match {
	
	private static Match instance = null;
	
	private int[][] matches;
	private ArrayList<String> matchingWords;
	private int numberOfMatches;
	private int numberOfCurrentMatch;

	private Match() {
		numberOfMatches = 0;
	}
	
	public static Match getInstance() {
		if(instance == null) {
			instance = new Match();
		}
		return instance;
	}

	public int[][] getMatches() {
		return matches;
	}

	public void setMatches(int[][] matches) {
		this.matches = matches;
	}
	
	public int getNumberOfMatches() {
		return numberOfMatches;
	}

	public void setNumberOfMatches(int numberOfMatches) {
		this.numberOfMatches = numberOfMatches;
	}
	
	public ArrayList<String> getMatchingWords() {
		return matchingWords;
	}

	public void setMatchingWords(ArrayList<String> matchingWords) {
		this.matchingWords = matchingWords;
	}
	
	public int getNumberOfCurrentMatch() {
		return numberOfCurrentMatch;
	}

	public void setNumberOfCurrentMatch(int numberOfCurrentMatch) {
		this.numberOfCurrentMatch = numberOfCurrentMatch;
	}

}
