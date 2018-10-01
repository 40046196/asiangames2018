package com.asiangames2018;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 * 
 * @author Leo Sudarma
 *
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel() {
	try {
	    image = ImageIO.read(new File("image\\bg.jpg"));
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    public ImagePanel(String fileName) {
	try {
	    image = ImageIO.read(new File(fileName));
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    public ImagePanel(File fileImage) {
	try {
	    if (fileImage != null) {
		image = ImageIO.read(fileImage);
	    }
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    public ImagePanel(byte[] imageBytes) {
	try {
	    ImageInputStream iis = javax.imageio.ImageIO.createImageInputStream(imageBytes);
	    // iis.read(imageBytes);
	    image = ImageIO.read(iis);
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    public void setImageFile(byte[] imageBytes) {
	try {
	    ImageInputStream iis = javax.imageio.ImageIO.createImageInputStream(imageBytes);
	    // iis.read(imageBytes);
	    image = ImageIO.read(iis);
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    public void setImageFile(File imageFile) {
	try {
	    image = ImageIO.read(imageFile);
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    public void paintComponent(Graphics gr) {
	super.paintComponent(gr);
	// get the default windows size
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	Graphics g = gr.create();
	g.drawImage(image, 0, 0, screenSize.width, screenSize.height, this);
	g.dispose();
    }

}
