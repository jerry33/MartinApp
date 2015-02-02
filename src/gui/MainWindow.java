package gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import config.Config;
import controller.Controller;
import util.MatcherUtil;
import view.MainView;
import model.AbstractModel;
import model.DatabaseModel;
import model.ProgramModel;
import net.miginfocom.swing.MigLayout;


public class MainWindow {

	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGui();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	private static void createAndShowGui() {
		AbstractModel model = new AbstractModel();
		MainView view = new MainView("Martinova appka");
		Controller controller = new Controller(view, model);
		
		view.registerObserver(controller);
	}
	
}
