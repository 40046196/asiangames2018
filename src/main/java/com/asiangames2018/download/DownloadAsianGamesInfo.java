package com.asiangames2018.download;

import javax.swing.JPanel;
import javax.sql.rowset.serial.SerialException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

import org.apache.commons.io.IOUtils;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Blob;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;

import com.asiangames2018.dao.AsianGamesDAO;
import com.asiangames2018.entity.Country;
import com.asiangames2018.entity.Sport;
import com.asiangames2018.util.GeneralLogging;
import com.asiangames2018.util.PanelImage;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;


public class DownloadAsianGamesInfo extends JPanel implements ActionListener, PropertyChangeListener {

	

	public DownloadAsianGamesInfo() {
		initComponents();
	}
	
	/**
	 * Task is an inner class.
	 * The user choose the download type  then task.execute
	 * Once execute it will run the task in background
	 * 
	 * Since we have 3 different data to download, we do not define the Collection<Entity>
	 * @author lion
	 *
	 */
	class Task extends SwingWorker<Collection, Void> {
		AsianGamesDAO dao = new AsianGamesDAO();   
		Collection colData;
	    private final int DOWNLOAD_COUNTRY = 1;
	    private final int DOWNLOAD_SPORTS = 2;
	    private final int DOWNLOAD_ATHLETES = 3;
	    private int option = DOWNLOAD_COUNTRY;
	    private PanelImage panelImage;
	    
	    
	    //  The format for flag :    [CountryCode].png      Sport : [SportCode].png
	    private String flagURL = "https://en.asiangames2018.id/d3images/ml/flags/xl/";  // the flag directory
	    private String sportIconURL = "https://en.asiangames2018.id/d3images/mobile/picto/";  // the sport icon directory
		
		/**
		 * Constructor of Task
		 *     either Depend on What user click to DOWNLOAD
		 * @param option
		 */
		public Task(int option, PanelImage panelImage) {
			this.option = option;
			this.panelImage = panelImage;
		}
		
		
		/*
		 * Main task. Executed in background thread.
		 * It will download from Asian Games 2018 official website.
		 * Then save it to database if the download button is clicked.
		 * */
	     @Override
	     public Collection doInBackground() {
	    	if (option == DOWNLOAD_COUNTRY) {
		    	this.colData = downloadAsianGamesCountries();
	    		updateCountry(colData);
	    	} 
	    	else if (option == DOWNLOAD_SPORTS) {
	    		this.colData = downloadAsianGamesSports();
	    	} 
//	    		else if (option == DOWNLOAD_ATHLETES) {
//	    		this.colData = downloadAsianGamesAthletes();
//	    		updateAtheletes(colData);
//	    	}
	    	return colData;
	     }

	     /*
	      * Executed in event dispatching thread
	      * Once the task finished, it will return to the caller.
	      * Enabled all disabled button. 
	      */
	     @Override
	     public void done() {
	    	 Toolkit.getDefaultToolkit().beep();
	    	 btnDownloadCountry.setEnabled(true);
	    	 btnDownloadSports.setEnabled(true);
	    	 btnDownloadAthletes.setEnabled(true);
	    	 setCursor(null); //turn off the wait cursor
	         System.out.println("DONE");
	     }
	     
	     /**
	      * updating the database
	      * The input is the collection of data
	      * @param col
	      */
	     private void updateCountry(Collection<Country> col) {
	    	logger.info("Updating countries has  " + col.size() + " records. ");
	        int progressValue = 0;
	        int maxDataLength = col.size();  // calculate the size of the downloaded countries

	    	Iterator<Country> it = col.iterator();
	    		  
	    	// read all and get the image to write to files
	    	while (it.hasNext()) {
	    		Country country = (Country) it.next();  //  get the country
	    		
    	        try {
    	        	dao.insertCountry(country);  // insert to country table
    	        } catch (Exception ex) {
    	        	ex.printStackTrace();
    	        }
    	        progressValue++;     //  add the progress bar value
   				setProgress( progressValue * 100 / maxDataLength);
   				pbCountries.setString("Updating Database "  + progressValue*100/maxDataLength + " %");
  				 // thread to sleep for 1000 milliseconds
   	  		    try {
   	  		//  give some 20 milisecond delay, to make the progress running.  
   	  		    	// without delay, it is also works, but it will be very very fast, since the data is few (less than 1000).
	 	   		   Thread.sleep(20);  
	 	   		   
 	   			} catch (Exception e) {
 	   			  System.out.println(e);
 	   			}

    	  	}
	    	  	
	    	//Testing the progress bar if it already reaches to its maximum value  
	    	logger.info("Completed Update Country Table" );
   	    }  
     
	     /**
	      * update Asian Games Sports Table
	      * @param col
	      */
	     private void updateSports(Collection col) {
	    	 
	     }
	     
	     /**
	      * Using jaunt-api,  it will download banco result from a fixed address
	      * Since the website display daily data, then the download start from 
	      * 	yesterday date for how long DAY_TO_DOWNLOAD , in this case 14 days.
	      * 
	      * nb.  if your data still empty, change the DAY_TO_DOWNLOAD to 365 days (past 1 year). 
	      * 
	      * The format will be:
	      *  winningDate, winningNumber, turbo, extra, bonus thursday 
	      *  Note the bonus thursday is not exist anymore, but we keep the field.
	      * @return
	      */
	     private Collection<Country> downloadAsianGamesCountries() {
	    	Vector<Country> v = new Vector<Country>();
	    	int ctr = 0;
	    	int maxSize = 0;

	    	try {
	    		UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
	        	userAgent.settings.checkSSLCerts = false;
	        	userAgent.visit("https://en.asiangames2018.id/athletes/page/1/");         //visit a url  
	        	  
	        	Elements elements = userAgent.doc.findEvery("<select>"); 
	        	Elements countryElements = elements.getElement(1).findEvery("<option>");
	        	maxSize = countryElements.size()  - 1;  // the max size, reduce one for the ALL country option 
	        	  
	        	for (Element element : countryElements) {
	        		String countryTags = element.getAt("value");
	          		  
	          		String[] split = countryTags.split("/");
	          		String countryId = split[3];
	          		if (countryId.length() != 3) continue;   // valid country id only 3 digits
	          		String countryName = element.getChildText();
	          		
	          		String urlCountryFlag = flagURL + countryId + ".png";
	          		Blob countryFlag = null; 
	          		try {
	          			URL url = new URL (urlCountryFlag);
	              		  
	          			byte[] flagBytes =IOUtils.toByteArray(url);
	          			countryFlag = new javax.sql.rowset.serial.SerialBlob(flagBytes);
	          			
	          			panelImage.setImageFile(flagBytes);
	          			panelImage.revalidate();
	          			panelImage.repaint();

	          		} catch (SerialException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
	          		} finally {
	          			userAgent.close();
	          		}
	          		  
	          		Country country = new Country(countryId, countryName, countryFlag);
	          		  
	 	   			v.add(country);  // add the country info to vector then progress the download bar
	 	   			ctr++;
	   				setProgress( ctr * 100 / maxSize);
	   				pbCountries.setString("Downloading Country Info at "  + ctr*100/maxSize + " %");
	   				try {  // we animate to be visible to user eyes..  actually not necessary.. just cosmetic here. 
		 	   		   Thread.sleep(20);
	 	   			} catch (Exception e) {
	 	   			  System.out.println(e);
	 	   			}
	        	}
	    	} catch (Exception ex) {
	    		
	    	}
	    	return v;  // return vector<Country>
	     }
	     
	     /**
	      * Download the Sports (id, name, icon). 
	      * @return
	      */
	     private Collection<Sport> downloadAsianGamesSports() {
	    	Vector<Sport> v = new Vector<Sport>();
	    	int ctr = 0;
	    	int maxSize = 0;

	    	try {
	    		UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
	        	userAgent.settings.checkSSLCerts = false;
	        	userAgent.visit("https://en.asiangames2018.id/athletes/page/1/");         //visit a url  
	        	  
	        	Elements elements = userAgent.doc.findEvery("<select>"); 
	        	Elements sportElements = elements.getElement(0).findEvery("<option>");  // 0 = sport, 1 = country
	        	maxSize = sportElements.size() -1 ;  // the max size ,  minus 1 for all sport option
	        	  
	        	for (Element element : sportElements) {
	        		String sportTags = element.getAt("value");
	          		  
	          		String[] split = sportTags.split("/");  // structure from the website
	          		String sportId = split[3];
	          		if (sportId.length() != 2) continue;   // valid sport id only 2 digits
	          		String sportName = element.getChildText();

	          		String urlSportIcon = sportIconURL + sportId + ".png";
	          		Blob sportIcon = null; 
	          		try {
	          			URL url = new URL (urlSportIcon);
	              		  
	          			byte[] iconBytes =IOUtils.toByteArray(url);
	          			sportIcon = new javax.sql.rowset.serial.SerialBlob(iconBytes);
	          			
	          			panelImage.setImageFile(iconBytes);
	          			panelImage.revalidate();
	          			panelImage.repaint();
	          		} catch (SerialException e) {
						e.printStackTrace();
	          		} finally {
	          			userAgent.close();
	          		}
	          		  
	          		Sport sport = new Sport(sportId, sportName, sportIcon);
	          		  
	 	   			v.add(sport);  // add the country info to vector then progress the download bar
	 	   			ctr++;
	   				setProgress( ctr * 100 / maxSize);
	   				pbSports.setString("Downloading Sport Info at "  + ctr*100/maxSize + " %");
	   				try {  // we animate to be visible to user eyes..  actually not necessary.. just cosmetic here. 
		 	   		   Thread.sleep(20);
	 	   			} catch (Exception e) {
	 	   			  System.out.println(e);
	 	   			}
	        	}
	    	} catch (Exception ex) {
	    		
	    	}
	    	return v;  // return vector<Country>
	     }
	     
	     
	}	// end of inner class	
	
	
	public void propertyChange(PropertyChangeEvent evt) {
		
	       if ("progress" == evt.getPropertyName()) {
	            int progress = (Integer) evt.getNewValue();
	            if (optionClicked == this.DOWNLOAD_COUNTRY) {
	            	pbCountries.setValue(progress);
	            } else if (optionClicked == this.DOWNLOAD_SPORTS) {
	            	pbSports.setValue(progress);
	            }
	        } 
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}	
	
    private void btnDownloadCountryActionPerformed(java.awt.event.ActionEvent evt) {
    	this.optionClicked = DOWNLOAD_COUNTRY;
    	btnDownloadCountry.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  // user must wait.
        task = new Task(this.DOWNLOAD_COUNTRY, this.panelImages);  // The task type is DOWNLOAD TO DATABASE
        task.addPropertyChangeListener(this);  // task link to this listener
        task.execute();  // run the Task class
    }	
    
    private void btnDownloadSportActionPerformed(java.awt.event.ActionEvent evt) {
    	this.optionClicked = DOWNLOAD_SPORTS;
    	btnDownloadSports.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  // user must wait.
        task = new Task(this.DOWNLOAD_SPORTS, panelImages);  // The task type is DOWNLOAD TO DATABASE
        task.addPropertyChangeListener(this);  // task link to this listener
        task.execute();  // run the Task class
    }	   
    
	
	/**
	 * Create the panel.
	 */
	private void initComponents() {
		
		JLabel lblNewLabel = new JLabel("Download Asian Games 2018 Basic Info");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		JLabel lblNewLabel_1 = new JLabel("This application only download and display data as is of offcial Asian Games 2018 data.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblThereIsNo = new JLabel("There is no feature to manual update, ensuring the data is compatible with the official results");
		lblThereIsNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblBasicData = new JLabel("Basic Data :  Participant countries,  Sports type, and Athletes data.");
		lblBasicData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panelDownload = new JPanel();
		panelDownload.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		
		panelImages = new PanelImage();
		panelImages.setBackground(new Color(250, 240, 230));
		panelImages.setForeground(Color.BLUE);
		panelImages.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Download Images", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(58, Short.MAX_VALUE)
					.addComponent(lblThereIsNo, GroupLayout.PREFERRED_SIZE, 703, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(58)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelImages, GroupLayout.PREFERRED_SIZE, 591, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 703, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBasicData, GroupLayout.PREFERRED_SIZE, 703, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelDownload, GroupLayout.PREFERRED_SIZE, 647, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addComponent(lblNewLabel)
					.addGap(26)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblThereIsNo, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addComponent(lblBasicData, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addComponent(panelDownload, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
					.addGap(45)
					.addComponent(panelImages, GroupLayout.PREFERRED_SIZE, 409, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(33, Short.MAX_VALUE))
		);
		
		btnDownloadCountry = new JButton("Download Countries");
		btnDownloadCountry.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDownloadCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnDownloadCountryActionPerformed(arg0);
			}
		});
		
		btnDownloadSports = new JButton("Download Sports");
		btnDownloadSports.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDownloadSports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnDownloadSportActionPerformed(arg0);
			}
		});

		
		
		btnDownloadAthletes = new JButton("Download Athletes");
		btnDownloadAthletes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		pbCountries = new JProgressBar();
		pbCountries.setValue(0);
		pbCountries.setStringPainted(true);
		
		pbSports = new JProgressBar();
		pbSports.setValue(0);
		pbSports.setStringPainted(true);

		
		pbAthletes = new JProgressBar();
		pbAthletes.setValue(0);
		pbAthletes.setStringPainted(true);
		
		GroupLayout gl_panelDownload = new GroupLayout(panelDownload);
		gl_panelDownload.setHorizontalGroup(
			gl_panelDownload.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDownload.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_panelDownload.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDownloadCountry, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDownloadSports, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDownloadAthletes, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(gl_panelDownload.createParallelGroup(Alignment.LEADING)
						.addComponent(pbAthletes, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)
						.addComponent(pbSports, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)
						.addComponent(pbCountries, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_panelDownload.setVerticalGroup(
			gl_panelDownload.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDownload.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_panelDownload.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(pbCountries, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDownloadCountry, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelDownload.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelDownload.createSequentialGroup()
							.addComponent(btnDownloadSports, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDownloadAthletes, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelDownload.createSequentialGroup()
							.addComponent(pbSports, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pbAthletes, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		panelDownload.setLayout(gl_panelDownload);
		setLayout(groupLayout);

	}
	
	
	// User can do 3 action download 
    private final int DOWNLOAD_COUNTRY = 1;
    private final int DOWNLOAD_SPORTS = 2;
    private final int DOWNLOAD_ATHLETES = 3;
	
    private int optionClicked = 1;
    
    private static Logger logger = GeneralLogging.getLogger();
	private Task task;  // the inner class is mandatory because ProgressBar run in the background
	
	// the property of the elements to be controlled
	private JPanel panelDownload;
	private PanelImage panelImages;
	private JButton btnDownloadCountry;
	private JButton btnDownloadSports;
	private JButton btnDownloadAthletes;
	private JProgressBar pbCountries;
	private JProgressBar pbSports;
	private JProgressBar pbAthletes;
	

}
