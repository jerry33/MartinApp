package model;

public class DatabaseModel extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5177631419479215595L;

	public DatabaseModel(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowData.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return rowData[rowIndex][columnIndex];
	}
	
	@Override
	public void setText(Object[][] rowData, Object[] columnNames) {
		// TODO Auto-generated method stub
		super.setText(rowData, columnNames);
	}

}
