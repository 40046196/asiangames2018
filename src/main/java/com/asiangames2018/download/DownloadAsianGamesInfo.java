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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;

import com.asiangames2018.dao.AsianGamesDAO;
import com.asiangames2018.entity.Athlete;
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
		private String sportImageURL = "https://en.asiangames2018.id/sport/";  
	    
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
	    		updateSports(colData);
	    	} else if (option == DOWNLOAD_ATHLETES) {
	    		this.colData = downloadAsianGamesAthletes();
//	    		updateAthletes(colData);
	    	}
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
	 	   		   Thread.sleep(100);  
	 	   		   
 	   			} catch (Exception e) {
 	   			  System.out.println(e);
 	   			}

    	  	}
	    	  	
	    	//Testing the progress bar if it already reaches to its maximum value  
	    	logger.info("Completed Update Country Table" );
   	    }  
	     
	     /**
	      * Update Athletes to database
	      * @param col
	      */
	     private void updateAthletes(Collection<Athlete> col) {
	    	logger.info("Updating athletes has  " + col.size() + " records. ");
	        int progressValue = 0;
	        int maxDataLength = col.size();  // calculate the size of the downloaded countries

	    	Iterator<Athlete> it = col.iterator();
	    		  
	    	// read all and get the image to write to files
	    	while (it.hasNext()) {
	    		Athlete athlete = (Athlete) it.next();  //  get the country
	    		
    	        try {
    	        	dao.insertAthlete(athlete);  // insert to country table
    	        } catch (Exception ex) {
    	        	ex.printStackTrace();
    	        }
    	        progressValue++;     //  add the progress bar value
   				setProgress( progressValue * 100 / maxDataLength);
   				pbAthletes.setString("Updating Database "  + progressValue*100/maxDataLength + " %");
  				 // thread to sleep for 1000 milliseconds
   	  		    try {
   	  		//  give some 20 milisecond delay, to make the progress running.  
   	  		    	// without delay, it is also works, but it will be very very fast, since the data is few (less than 1000).
	 	   		   Thread.sleep(100);  
	 	   		   
 	   			} catch (Exception e) {
 	   			  System.out.println(e);
 	   			}

    	  	}
	    	  	
	    	//Testing the progress bar if it already reaches to its maximum value  
	    	logger.info("Completed Update athlete Table" );
   	    }  
     
	     /**
	      * update Asian Games Sports Table
	      * @param col
	      */
	     private void updateSports(Collection col) {
	    	logger.info("Updating countries has  " + col.size() + " records. ");
		    int progressValue = 0;
		    int maxDataLength = col.size();  // calculate the size of the downloaded countries

		    Iterator<Sport> it = col.iterator();
		    		  
		    // read all and get the image to write to files
		    while (it.hasNext()) {
		    	Sport sport = (Sport) it.next();  //  get the country
		    		
	    	    try {
	    	    	dao.insertSport(sport);  // insert to country table
	    	    } catch (Exception ex) {
	    	    	ex.printStackTrace();
	    	    }
	    	    progressValue++;     //  add the progress bar value
	   			setProgress( progressValue * 100 / maxDataLength);
	   			pbSports.setString("Updating Database "  + progressValue*100/maxDataLength + " %");
	   			// thread to sleep for 1000 milliseconds
	   	  		try {
	   	  			//  give some 20 milisecond delay, to make the progress running.  
	   	  		    // without delay, it is also works, but it will be very very fast, since the data is few (less than 1000).
		 	   		Thread.sleep(100);  
		 	   		   
	 	   		} catch (Exception e) {
	 	   		  System.out.println(e);
	 	   		}
	    	}
	    	//Testing the progress bar if it already reaches to its maximum value  
	    	logger.info("Completed Update Sport Table" );	    	 
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
	    		UserAgent userAgent = new UserAgent();   //create new userAgent (headless browser).
	        	userAgent.settings.checkSSLCerts = false;
	        	userAgent.visit("https://en.asiangames2018.id/athletes/page/1/");         //visit a url  
	        	  
	        	Elements elements = userAgent.doc.findEvery("<select>"); 
	        	Elements countryElements = elements.getElement(1).findEvery("<option>");
	        	maxSize = countryElements.size()  - 1;  // the max size, reduce one for Choose A Country option 
	        	  
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
		 	   		   Thread.sleep(100);
	 	   			} catch (Exception e) {
	 	   			  System.out.println(e);
	 	   			}
	        	}
	    	} catch (Exception ex) {
	    		
	    	}
	    	return v;  // return vector<Country>
	     }
	     
	     /**
	      * Download all athletes
	      * There is 150 web pages for all athletes
	      * The steps :
	      *    Repeat from 1 to 150   {
	      *    		1.  Collect all athletes ids from 150 pages.
	      *    }
	      *    2.  Visit each detail athlete webpage using their athleted id
	      *    3. Completed all the Athleted information. 
	      * 
	      * @return
	      */
	     private Collection<Athlete> downloadAsianGamesAthletes() {
	    	Vector<Athlete> v = new Vector<Athlete>();
	    	int ctr = 0;
	    	int maxSize = 0;
	    	int maxWebPage = 1;
	    	int currentWebPage = 1;

	    	try {
	    		UserAgent userAgent = new UserAgent();   //create new userAgent (headless browser).
	        	userAgent.settings.checkSSLCerts = false;
	        	
	        	while (currentWebPage <= maxWebPage) {
		        	userAgent.visit("https://en.asiangames2018.id/athletes/page/" + currentWebPage + "/");         //visit a url  
		        	Elements elements = userAgent.doc.findEvery("<a class='or-athletes__link'>"); 
		        	for (Element element : elements) {
		        		String detailInfo = element.getAt("href");  // get athlete detail page
		        	}
		        	currentWebPage++;
	        		
	        	}

	        	  
//	        	
//	        	maxSize = countryElements.size()  - 1;  // the max size, reduce one for Choose A Country option 
//	        	  
//	        	for (Element element : countryElements) {
//	        		String countryTags = element.getAt("value");
//	          		  
//	          		String[] split = countryTags.split("/");
//	          		String countryId = split[3];
//	          		if (countryId.length() != 3) continue;   // valid country id only 3 digits
//	          		String countryName = element.getChildText();
//	          		
//	          		String urlCountryFlag = flagURL + countryId + ".png";
//	          		Blob countryFlag = null; 
//	          		try {
//	          			URL url = new URL (urlCountryFlag);
//	              		  
//	          			byte[] flagBytes =IOUtils.toByteArray(url);
//	          			countryFlag = new javax.sql.rowset.serial.SerialBlob(flagBytes);
//	          			
//	          			panelImage.setImageFile(flagBytes);
//	          			panelImage.revalidate();
//	          			panelImage.repaint();
//
//	          		} catch (SerialException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//	          		} finally {
//	          			userAgent.close();
//	          		}
//	          		  
//	          		Country country = new Country(countryId, countryName, countryFlag);
//	          		  
//	 	   			v.add(country);  // add the country info to vector then progress the download bar
//	 	   			ctr++;
//	   				setProgress( ctr * 100 / maxSize);
//	   				pbCountries.setString("Downloading Country Info at "  + ctr*100/maxSize + " %");
//	   				try {  // we animate to be visible to user eyes..  actually not necessary.. just cosmetic here. 
//		 	   		   Thread.sleep(100);
//	 	   			} catch (Exception e) {
//	 	   			  System.out.println(e);
//	 	   			}
//	        	}
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
	    		UserAgent userAgent = new UserAgent();  //create new userAgent (headless browser).
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
	          		String sportName = element.getChildText();  // get the sport name,  see the html structure
	          		

	          		String urlSportIcon = sportIconURL + sportId + ".png"; // since we already check, the icon using sportid.png
	          		Blob sportIcon = null;   // an empty blob
	          		String sportImagePage = sportName.replace('/', '-'); // the website using "-" for spaces and "/"
	          		sportImagePage = sportImagePage.replace(' ', '-');  // now the imagePage url is clean..
	          		
	          		// Special correction for few mistakes on the website that doesnt follow the structure
	          		if (sportImagePage.equals("Artistic-Gymnastics"))  sportImagePage = "Artistic-Gymnastic"; // only 1 sport has inconsistency, maybe their programmer forgot to add the S for the pictures.. :D
	          		if (sportImagePage.equals("Sepaktakraw")) {
	          			sportImagePage = "sepak-takraw";
	          			sportName = "Sepak Takraw";
	          		}
	          		if (sportImagePage.equals("Skateboard")) {
	          			sportImagePage = "skateboarding";
	          			sportName = "Skateboarding";
	          		}

	          		Blob sportImage = grabImageMascot(sportImageURL +  sportImagePage , sportName);  // grab the mascot image on the url, using sportName
	          		
	          		try {
	          			URL url = new URL (urlSportIcon);  //  get the icon sport
	          			byte[] iconBytes =IOUtils.toByteArray(url);
	          			sportIcon = new javax.sql.rowset.serial.SerialBlob(iconBytes);
	          			
	          			panelImage.setImageFile(iconBytes);  // animate the icon on the canvas
	          			panelImage.revalidate();
	          			panelImage.repaint();
	          		} catch (SerialException e) {
						e.printStackTrace();
	          		} finally {
	          			userAgent.close();
	          		}
	          		  
	          		// here you got the sportId, sportName, icon, and mascotImage
	          		Sport sport = new Sport(sportId, sportName, sportIcon, sportImage);
	          		  
	 	   			v.add(sport);  // add the country info to vector then progress the download bar
	 	   			ctr++;
	   				setProgress( ctr * 100 / maxSize);
	   				pbSports.setString("Downloading Sport Info at "  + ctr*100/maxSize + " %");
	   				try {  // we animate to be visible to user eyes..  actually not necessary.. just cosmetic here. 
		 	   		   Thread.sleep(100);
	 	   			} catch (Exception e) {
	 	   			  System.out.println(e);
	 	   			}
	        	}
	    	} catch (Exception ex) {
	    		
	    	}
	    	return v;  // return vector<Country>
	     }
	     
	     
	     
	     /**
	      * Grab the Sport Image
	      * @param urlImagePage
	      * @return
	      */
	     
	     private Blob grabImageMascot(String urlImagePage, String sportName) {
	    	 
	    	 Blob blob = null;
	    	 UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
	         userAgent.settings.checkSSLCerts = false;
	         try {
	        	userAgent.visit(urlImagePage);   
	        	System.out.println(urlImagePage);
	        	 
	        	Element imgs = userAgent.doc.findFirst("<img alt='"  + sportName +"'>");
	        	 
       			URL url = new URL (imgs.getAt("src"));
        		  
       			byte[] iconBytes =IOUtils.toByteArray(url);
       			blob = new javax.sql.rowset.serial.SerialBlob(iconBytes);
       			
       			panelImage.setImageFile(iconBytes);
       			panelImage.revalidate();
       			panelImage.repaint();
       			
   				try {  // we animate to be visible to user eyes..  actually not necessary.. just cosmetic here. 
		 	   		   Thread.sleep(100);
	 	   			} catch (Exception e) {
	 	   			  System.out.println(e);
	 	   			}
       			
	         } catch (JauntException ex) {
	        	 
	         } catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SerialException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 return blob;
	     }
	     
	     
	}	// end of inner class	
	
	
	public void propertyChange(PropertyChangeEvent evt) {
		
	       if ("progress" == evt.getPropertyName()) {
	            int progress = (Integer) evt.getNewValue();
	            if (optionClicked == this.DOWNLOAD_COUNTRY) {
	            	pbCountries.setValue(progress);
	            } else if (optionClicked == this.DOWNLOAD_SPORTS) {
	            	pbSports.setValue(progress);
	            } else if (optionClicked == this.DOWNLOAD_ATHLETES) {
	            	pbAthletes.setValue(progress);
	            }
	        } 
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}	
	
    private void btnDownloadCountryActionPerformed(java.awt.event.ActionEvent evt) {
    	this.optionClicked = DOWNLOAD_COUNTRY;
    	setButtonEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  // user must wait.
        task = new Task(this.DOWNLOAD_COUNTRY, this.panelImages);  // The task type is DOWNLOAD TO DATABASE
        task.addPropertyChangeListener(this);  // task link to this listener
        task.execute();  // run the Task class
    }	
    
    private void btnDownloadSportActionPerformed(java.awt.event.ActionEvent evt) {
    	this.optionClicked = DOWNLOAD_SPORTS;
    	setButtonEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  // user must wait.
        task = new Task(this.DOWNLOAD_SPORTS, panelImages);  // The task type is DOWNLOAD TO DATABASE
        task.addPropertyChangeListener(this);  // task link to this listener
        task.execute();  // run the Task class
    }	   
    
    private void btnDownloadAthletesActionPerformed(java.awt.event.ActionEvent evt) {
    	this.optionClicked = DOWNLOAD_ATHLETES;
    	setButtonEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  // user must wait.
        task = new Task(this.DOWNLOAD_ATHLETES, panelImages);  // The task type is DOWNLOAD TO DATABASE
        task.addPropertyChangeListener(this);  // task link to this listener
        task.execute();  // run the Task class
    }	   
    
    private void setButtonEnabled(boolean enabledOrDisabled) {
    	btnDownloadCountry.setEnabled(enabledOrDisabled);
    	btnDownloadSports.setEnabled(enabledOrDisabled);
    	btnDownloadAthletes.setEnabled(enabledOrDisabled);
    	
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
					.addGap(58)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 651, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblThereIsNo, GroupLayout.PREFERRED_SIZE, 642, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblBasicData))
							.addContainerGap(35, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(panelDownload, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panelImages, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE))
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addComponent(lblNewLabel)
					.addGap(26)
					.addComponent(lblNewLabel_1)
					.addGap(4)
					.addComponent(lblThereIsNo, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblBasicData, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelDownload, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panelImages, GroupLayout.PREFERRED_SIZE, 446, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(30, Short.MAX_VALUE))
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
		btnDownloadAthletes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnDownloadAthletesActionPerformed(arg0);
			}
		});
		
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
						.addGroup(gl_panelDownload.createSequentialGroup()
							.addComponent(btnDownloadAthletes, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
							.addGap(32)
							.addComponent(pbAthletes, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelDownload.createSequentialGroup()
							.addComponent(btnDownloadSports, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
							.addGap(32)
							.addComponent(pbSports, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelDownload.createSequentialGroup()
							.addComponent(btnDownloadCountry, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
							.addGap(32)
							.addComponent(pbCountries, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(35, Short.MAX_VALUE))
		);
		gl_panelDownload.setVerticalGroup(
			gl_panelDownload.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDownload.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_panelDownload.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnDownloadCountry, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pbCountries, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelDownload.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnDownloadSports, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pbSports, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelDownload.createParallelGroup(Alignment.LEADING)
						.addComponent(pbAthletes, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelDownload.createSequentialGroup()
							.addGap(5)
							.addComponent(btnDownloadAthletes, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(87))
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
