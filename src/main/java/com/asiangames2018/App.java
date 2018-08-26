package com.asiangames2018;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.asiangames2018.dao.AsianGamesDAO;
import com.asiangames2018.entity.Country;
import com.asiangames2018.util.GeneralLogging;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import com.jaunt.util.IOUtil;

/**
 * Hello world!
 * https://en.asiangames2018.id/d3images/ml/flags/xl/JPN.png
 *
 */
public class App 
{
	public static void main(String[] args) {
		try {
			GeneralLogging.setup();   // setup the logger file, only call once.
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//	 UIManager.getDefaults().put( "TitledBorder.font", new javax.swing.plaf.FontUIResource( new Font( "Arial", Font.BOLD, 12 ) ) ) ;

       SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Font f = new Font("verdana", Font.BOLD, 18);
                UIManager.put("Menu.font", f);
                UIManager.put("MenuItem.font", f);
                UIManager.put("TitledBorder.font", f);
	                
                createGUIMenu();
            }
        });		
	}
	
	
	/*
	 * Create and setup the Frame Windows GUI
	 */
	protected static  void createGUIMenu() {
		mainFrame.setTitle("Lotto Quebec Analysis Software ver 1.3.0 - (c) 2018, Lion du Quebec" ); // Prepare a blank frame  
		javax.swing.JPanel panel = new ImagePanel();
		mainFrame.add(panel);
		App app = new App();  
		mainFrame.setJMenuBar(app.createMenu());  //  set the menu for this application

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   //  get the default windows size
		mainFrame.setSize(screenSize.width, screenSize.height);     // Initialize the frame size (width * height);   (x,y) position..
        Dimension frameSize = mainFrame.getSize();  //  get your frame size as your screen size
		mainFrame.setVisible(true);
	}
	
	/**
	 * Set up the menu bar, on the top of the frame..
	 */
	public JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();  // the bar to handle all menus.
		setFileMenu(menuBar);
		return menuBar;
	}	

	/**
	 * Display all action under File Menus
	 * @param menuBar
	 */
	private void setFileMenu(JMenuBar menuBar) {
		// Define the first Menu for Download and Update database tables.
		JMenu fileMenu = new JMenu("File");   //  First group will handle master file
		fileMenu.setMnemonic(KeyEvent.VK_F);  //  The shortcut is ALT + F (Master File)	
		menuBar.add(fileMenu);   //  add to the menu bar
			/**  The Menu Under File  **/
		JMenuItem downloadAsianGamesBasicData = new JMenuItem("Download Basic Data", KeyEvent.VK_D);
		downloadAsianGamesBasicData.setToolTipText("Download Basic Data: Country, Sport, Athletes");
		downloadAsianGamesBasicData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				downloadAsianGamesBasicDataItemActionPerformed(evt);
			}
		});		
		fileMenu.add(downloadAsianGamesBasicData);     
	}	
	
	/**
	 * download all master tables from asian games websites
	 * @param evt
	 */
	private void downloadAsianGamesBasicDataItemActionPerformed (ActionEvent evt) {
//		DownloadBancoResultat downloadBanco = new DownloadBancoResultat();		
//		JDialog dialog = new JDialog(mainFrame ,"Download Banco Resultat", true);  // mainFrame is the modal,
//
//		dialog.add(downloadBanco);
//		dialog.pack();
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   //  get the default windows size
//        Dimension dialogSize = dialog.getSize();  //  get your frame size
//        dialog.setLocation(new Point((screenSize.width  - dialogSize.width) / 2,                        // set the position of the frame
//               (screenSize.height - dialogSize.height) / 2));                      // to the center of screen
//		dialog.setDefaultCloseOperation( javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//		JDialogHelper.setJDialogTree(dialog,  null, null);   //  Dialog student form is first dialog
//		dialog.setVisible(true);  // When setVisible  this program waiting, until you close the dialog.
//		dialog.dispose();
	}


   // Variables declaration - do not modify
	private static BufferedImage image;
	private static  JFrame mainFrame = new JFrame();
	protected JDialog parentDialog;


}
