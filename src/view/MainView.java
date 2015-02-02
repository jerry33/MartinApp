package view;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import util.FileUtil;
import util.MatcherUtil;
import model.AbstractModel;
import model.DatabaseModel;
import model.ProgramModel;
import net.miginfocom.swing.MigLayout;
import config.Config;
import controller.Controller;
import data.Match;

public class MainView extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4717028554138549353L;
	private JScrollPane scrollPaneDB;
	private JScrollPane scrollPaneProgram;
	private JTable tableProgram;
	private JTable tableDB;
	private ProgramModel programModel;
	private DatabaseModel databaseModel;
	
	private static final String FOLDER_ICON = "folder.png";
	
	private String programFilePath = "";
	private String dbFilePath = "";
	private String currentLeft = "";
	private String currentRight = "";
	
	private Match match = Match.getInstance();

	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuFileExit;
	private JButton buttonProgram;
	private JButton buttonDB;
	private JButton buttonFind;
	private JButton buttonFindAll;
	private JButton folderButtonProgram;
	private JButton folderButtonDB;
	private JButton searchButton;
	private JButton searchNext;
	private JButton searchPrev;
	private JButton swapFiles;
	private JTextField matchingWordText;
	
	private JLabel titleProgram;
	private JLabel titleDB;
	private JLabel matchInWord;
	
	private JCheckBox advancedMatching;
	
	private JFileChooser fileChooser;
	
	private JToolBar toolBarMain;
	private JToolBar toolBarProgram;
	private JToolBar toolBarDB;
	
	private Controller controller;
	
	JFrame frame;
	
	public MainView(String title) {
		
		super(new MigLayout("wrap 2", "[grow, fill][grow, fill]", "[][]"));
		
		frame = new JFrame("Martinova appka");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		MatcherUtil.findMatches(); //important! finds all the matches
		
		tableProgram = new JTable();
		tableDB = new JTable();
		
		scrollPaneProgram = new JScrollPane(tableProgram, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneDB = new JScrollPane(tableDB, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		fileChooser = new JFileChooser(System.getProperty("user.home"));
		
		titleProgram = new JLabel();
		titleDB = new JLabel();
		matchInWord = new JLabel("Zhoda v slove:");
		matchingWordText = new JTextField();

		initMenuBar();
		initToolbars();
		initButtons();
		
		add(toolBarMain, "span");
		add(toolBarProgram);
		add(toolBarDB, "wrap");
		add(scrollPaneProgram);
		add(scrollPaneDB);
		add(advancedMatching, "wrap");
		add(matchInWord, "wrap");
		add(matchingWordText, "wrap");
//		add(swapFiles);
		
		frame.setBounds(0, 0, 1024, 768);
		frame.setVisible(true);
		frame.setContentPane(this);
		frame.setJMenuBar(menuBar);
	}

	public void setTableView(Object[][] rowData, JTable jTable, AbstractModel abstractModel) {
		jTable.setModel(abstractModel);
	}
	
	public void registerObserver(Controller controller) {
		this.controller = controller;
	}
	
	public void setButtonText(String text) {
		buttonDB.setText(text);
	}
	
	public void highlightMatchingLines(int matchingLines[]) {
		tableProgram.setRowSelectionInterval(matchingLines[0] - 1, matchingLines[0] - 1);
		scrollToVisible(tableProgram, matchingLines[0] - 1, 0); //table scrolls automatically to found line
		
		tableDB.setRowSelectionInterval(matchingLines[1] - 1, matchingLines[1] - 1);
		scrollToVisible(tableDB, matchingLines[1] - 1, 0);
	}
	
	public void displayMatchingWord() {
		matchingWordText.setText(match.getMatchingWords().get(match.getNumberOfCurrentMatch()));
	}
	
	
	public void updateTable(AbstractModel abstractModel, int tableType) {
		if(tableType == Config.PROGRAM_FILE) {
			tableProgram.setModel(abstractModel);
			tableProgram.getColumnModel().getColumn(0).setMaxWidth(Config.MAX_COLUMN_WIDTH);
		} else if(tableType == Config.DB_FILE) {
			tableDB.setModel(abstractModel);
			tableDB.getColumnModel().getColumn(0).setMaxWidth(Config.MAX_COLUMN_WIDTH);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		if(source == buttonProgram) {
//			controller.setTextToTable(FileUtil.getRowsFromFile(Config.PROGRAM_FILE_PATH), Config.COLUMN_NAMES, programModel, Config.PROGRAM_FILE);
		} else if(source == buttonDB) {
//			controller.setTextToTable(FileUtil.getRowsFromFile(Config.DB_FILE_PATH), Config.COLUMN_NAMES, databaseModel, Config.DB_FILE);
		} else if(source == buttonFindAll) {
			controller.findAllMatchingLines();
		} else if(source == searchNext) {
			controller.findNextMatchingLine();
		} else if(source == searchPrev) {
			controller.findPreviousMatchingLine();
		} else if(source == menuFileExit) {
			System.exit(0);
		} else if(source == searchButton) {
//			MatcherUtil.findMatches(programFilePath, dbFilePath);
			controller.resetMatches();
			MatcherUtil.findMatches(currentLeft, currentRight, advancedMatching.isSelected());
			controller.findAllMatchingLines();
		} else if(source == folderButtonProgram) {
			openFile(Config.PROGRAM_FILE, programModel);
		} else if(source == folderButtonDB) {
			openFile(Config.DB_FILE, databaseModel);
		} else if(source == swapFiles) {
			swapFiles();
		} else if(source == advancedMatching) {
			controller.resetMatches();
			MatcherUtil.findMatches(currentLeft, currentRight, advancedMatching.isSelected());
			controller.findAllMatchingLines();
		}
		
	}
	
	/**
	 * Scrolls table to the specific row.
	 * @param table
	 * @param rowIndex
	 * @param vColIndex
	 */
	public void scrollToVisible(JTable table, int rowIndex, int vColIndex)
	{
	    if (!(table.getParent() instanceof JViewport)) return;
	    JViewport viewport = (JViewport)table.getParent();
	    Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);
	    Point pt = viewport.getViewPosition();
	    rect.setLocation(rect.x-pt.x, rect.y-pt.y);
	    viewport.scrollRectToVisible(rect);
	}
	
	public void initToolbars() {
		
		toolBarMain = new JToolBar();
		
		ImageIcon searchIcon = new ImageIcon("search.png");
		searchButton = new JButton(searchIcon);
		searchButton.setFocusPainted(false);
		searchButton.setFocusable(false);
		searchButton.setToolTipText("Vyhæadaù zhody");
		toolBarMain.add(searchButton);
		toolBarMain.add(Box.createRigidArea(new Dimension(5,0)));
		
		ImageIcon prevIcon = new ImageIcon("back.png");
		searchPrev = new JButton(prevIcon);
		searchPrev.setFocusPainted(false);
		searchPrev.setFocusable(false);
		searchPrev.setToolTipText("Predch·dzaj˙ca zhoda");
		toolBarMain.add(searchPrev);
		toolBarMain.add(Box.createRigidArea(new Dimension(5,0)));
		
		ImageIcon nextIcon = new ImageIcon("forward.png");
		searchNext = new JButton(nextIcon);
		searchNext.setFocusPainted(false);
		searchNext.setFocusable(false);
		searchNext.setToolTipText("Nasleduj˙ca zhoda");
		toolBarMain.add(searchNext);
		toolBarMain.setFloatable(false);
		
		ImageIcon swapIcon = new ImageIcon("double_arrow.png");
		swapFiles = new JButton(swapIcon);
		swapFiles.setFocusPainted(false);
		swapFiles.setFocusable(false);
		swapFiles.setToolTipText("Vymeniù s˙bory");
		toolBarMain.add(swapFiles);
		toolBarMain.setFloatable(false);
		
		toolBarProgram = new JToolBar();
		ImageIcon folderIconProgram = new ImageIcon(FOLDER_ICON);
		folderButtonProgram = new JButton(folderIconProgram);
		folderButtonProgram.setFocusPainted(false);
		folderButtonProgram.setFocusable(false);
		toolBarProgram.add(folderButtonProgram);
		toolBarProgram.add(Box.createRigidArea(new Dimension(5,0)));
		toolBarProgram.add(titleProgram);
		toolBarProgram.setFloatable(false);
		
		toolBarDB = new JToolBar();
		ImageIcon folderIconDB = new ImageIcon(FOLDER_ICON);
		folderButtonDB = new JButton(folderIconDB);
		folderButtonDB.setFocusPainted(false);
		folderButtonDB.setFocusable(false);
		toolBarDB.add(folderButtonDB);
		toolBarDB.add(Box.createRigidArea(new Dimension(5,0)));
		toolBarDB.add(titleDB);
		toolBarDB.setFloatable(false);
		
	}
	
	public void initMenuBar() {
		
		menuBar = new JMenuBar();
		menuFile = new JMenu("S˙bor");
		
		ImageIcon imageExit = new ImageIcon("exit.png");
		menuFileExit = new JMenuItem("Zatvoriù");
		menuFileExit.setIcon(imageExit);
		
		menuBar.add(menuFile);
		
		menuFile.add(menuFileExit);
		menuFileExit.addActionListener(this);
		
	}
	
	public void initButtons() {
		
		buttonProgram = new JButton("Program");
		buttonDB = new JButton("DB");
		buttonFindAll = new JButton("N·jdi vöetky zhody");
		buttonFind = new JButton("N·jdi Ôalöiu zhodu");
		advancedMatching = new JCheckBox("Vyhæad·vanie po slov·ch");
		
		buttonProgram.addActionListener(this);
		buttonDB.addActionListener(this);
		buttonFindAll.addActionListener(this);
		buttonFind.addActionListener(this);
		
		folderButtonProgram.addActionListener(this);
		folderButtonDB.addActionListener(this);
		searchButton.addActionListener(this);
		searchPrev.addActionListener(this);
		searchNext.addActionListener(this);
		advancedMatching.addActionListener(this);
		swapFiles.addActionListener(this);
		
	}
	
	public void openFile(int tableType, AbstractModel abstractModel) {
		int returnVal = fileChooser.showOpenDialog(this);
		
		File file = null;
//		match.setNumberOfCurrentMatch(0); // when the current file is changed, all the matches should reset
//		AbstractModel.findCounter = 0;
		controller.resetMatches();
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			String filePath = file.getAbsolutePath();
			System.out.println(file.getAbsolutePath());
			controller.setTextToTable(FileUtil.getRowsFromFile(filePath), Config.COLUMN_NAMES, abstractModel, tableType);
			
			if(tableType == Config.PROGRAM_FILE) {
				programFilePath = file.getAbsolutePath();
				currentLeft = programFilePath;
				titleProgram.setText(programFilePath);
			} else if(tableType == Config.DB_FILE) {
				dbFilePath = file.getAbsolutePath();
				currentRight = dbFilePath;
				titleDB.setText(dbFilePath);
			}
		}
		
	}
	
	public void swapFiles() {
//		match.setNumberOfCurrentMatch(0); // when the current file is changed, all the matches should reset
//		AbstractModel.findCounter = 0;
		
		controller.resetMatches();
		
		String tmp = currentLeft;
		currentLeft = currentRight;
		currentRight = tmp;
		
		controller.setTextToTable(FileUtil.getRowsFromFile(currentLeft), Config.COLUMN_NAMES, databaseModel, Config.PROGRAM_FILE);
		controller.setTextToTable(FileUtil.getRowsFromFile(currentRight), Config.COLUMN_NAMES, programModel, Config.DB_FILE);
		
		titleProgram.setText(currentLeft);
		titleDB.setText(currentRight);
	}

}
