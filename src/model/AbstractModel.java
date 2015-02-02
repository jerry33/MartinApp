package model;

import javax.swing.table.AbstractTableModel;


import data.Match;

public class AbstractModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6908489015099346337L;
	protected Object[][] rowData;
	protected Object[] columnNames;
	protected int[][] matchingLines;
	protected Match match = Match.getInstance();
	
	public static int findCounter = 0;
	
	
	public AbstractModel(Object rowData[][], Object columnNames[]) {
		this.rowData = rowData;
		this.columnNames = columnNames;
	}
	
	public AbstractModel() {}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowData.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		System.out.println(columnNames.length + "mehe");
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return rowData[rowIndex][columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return String.valueOf(columnNames[column]);
	}
	
	public void setText(Object rowData[][], Object columnNames[]) {
		this.rowData = rowData;
		this.columnNames = columnNames;
	}
	
	public void findAllMatches() {
		matchingLines = match.getMatches();
		match.setNumberOfCurrentMatch(findCounter);
	}
	
	public int[] findNextMatch() {
		if(findCounter > matchingLines.length-1) {
			System.out.println("Viac zhôd sa nenašlo.");
			findCounter = 0;
			match.setNumberOfCurrentMatch(findCounter);
			return null;
		}
		System.out.println("findCounter = " + findCounter);
		match.setNumberOfCurrentMatch(findCounter);
		return matchingLines[findCounter++];
	}
	
	public int[] findPrevMatch() {
		findCounter--;
		if(findCounter < 0) {
			System.out.println("Ste na zaèiatku preh¾adávania");
			findCounter = 0;
			match.setNumberOfCurrentMatch(findCounter);
			return null;
		}
		System.out.println("findCounter = " + findCounter);
		match.setNumberOfCurrentMatch(findCounter);
		return matchingLines[findCounter];
	}
	

}
