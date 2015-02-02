package controller;

import javax.swing.table.AbstractTableModel;

import config.Config;
import data.Match;
import model.AbstractModel;
import model.DatabaseModel;
import model.ProgramModel;
import util.FileUtil;
import view.MainView;

public class Controller {
	
	private MainView view;
	private AbstractModel model;
	private static int tableType;
	private Match match;
	
	private static final int SEARCH_NEXT = 0;
	private static final int SEARCH_PREV = 1;
	
	public Controller(MainView view, AbstractModel model) {
		this.view = view;
		this.model = model;
		match = Match.getInstance();
	}
	
	private static void updateView(MainView view, AbstractModel model) {
		view.updateTable(model, tableType);
	}
	
	private static void updateViewByMatchingLines(MainView view, AbstractModel model, int direction) {
		int[] foundLines = new int[2];
		
		switch (direction) {
		case SEARCH_NEXT:
			if(!((foundLines = model.findNextMatch()) != null)) {
				System.out.println("Koniec prehladavania");
				return;
			}
			break;
		case SEARCH_PREV:
			if(!((foundLines = model.findPrevMatch()) != null)) {
				System.out.println("Zaciatok prehladavnia");
				return;
			}
			break;
		default:
			break;
		}
		
//		if(!((foundLines = model.findNextMatch()) != null)) {
//			System.out.println("Koniec prehladavania");
//			return;
//		}
		
		view.highlightMatchingLines(foundLines);
		view.displayMatchingWord();
	}
	
	public void setTextToTable(Object[][] rowData, Object[] columnNames, AbstractModel abstractModel, int tableType) {
		
		if(tableType == Config.PROGRAM_FILE) {
			abstractModel = new ProgramModel(rowData, columnNames);
			this.tableType = tableType;
		} else if(tableType == Config.DB_FILE) {
			abstractModel = new DatabaseModel(rowData, columnNames);
			this.tableType = tableType;
		}
		
		abstractModel.setText(rowData, columnNames);
		updateView(view, abstractModel);
	}
	
	public void findAllMatchingLines() {
		model.findAllMatches();
		findNextMatchingLine();
	}
	
	public void findNextMatchingLine() {
		updateViewByMatchingLines(view, this.model, SEARCH_NEXT);
	}
	
	public void findPreviousMatchingLine() {
		updateViewByMatchingLines(view, this.model, SEARCH_PREV);
	}
	
	public void resetMatches() {
		match.setNumberOfCurrentMatch(0); // when the current file is changed, all the matches should reset
		AbstractModel.findCounter = 0;
	}

}
