package com.wei.slidingpuzzle.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class UploadedImage {
	
	private BufferedImage image;
	private boolean isOriginUpperLeft = true;
	private int width;
	private int height;

	// construct a blank image with given height and width
	public UploadedImage(int width, int height) {
		if (width < 0)
			throw new IllegalArgumentException("Image width must be nonnegative");
		if (height < 0)
			throw new IllegalArgumentException("Image height must be nonnegative");

		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	// construct image reading in a .png, .gif, or .jpg File
	public UploadedImage(File file) {
		
		try { 
			image = ImageIO.read(file); 
		} catch (IOException exc) {
			exc.printStackTrace();
			throw new RuntimeException("Could not open file: " + file);
		}
		
	    if (image == null) {
	    	throw new RuntimeException("Invalid image file: " + file);
	    }
		
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	public void setOriginUpperLeft() {
		isOriginUpperLeft = true;
	}

	public void setOriginLowerLeft() {
		isOriginUpperLeft = false;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public Color get(int col, int row) {
		if (col < 0 || col >= getWidth())
			throw new IndexOutOfBoundsException("col must be between 0 and " + (getWidth() - 1));
		if (row < 0 || row >= getHeight())
			throw new IndexOutOfBoundsException("row must be between 0 and " + (getHeight() - 1));
		if (isOriginUpperLeft)
			return new Color(image.getRGB(col, row));
		else
			return new Color(image.getRGB(col, height - row - 1));
	}

	public void set(int col, int row, Color color) {
		if (col < 0 || col >= getWidth())
			throw new IndexOutOfBoundsException("col must be between 0 and " + (getWidth() - 1));
		if (row < 0 || row >= getHeight())
			throw new IndexOutOfBoundsException("row must be between 0 and " + (getHeight() - 1));
		if (color == null)
			throw new NullPointerException("can't set Color to null");
		if (isOriginUpperLeft)
			image.setRGB(col, row, color.getRGB());
		else
			image.setRGB(col, height - row - 1, color.getRGB());
	}

	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (obj.getClass() != this.getClass()) return false;

		UploadedImage that = (UploadedImage) obj;
		if (this.getWidth() != that.getWidth()) return false;
		if (this.getHeight() != that.getHeight()) return false;
		for (int col = 0; col < getWidth(); col++) {
			for (int row = 0; row < getHeight(); row++) {
				if (!this.get(col, row).equals(that.get(col, row))) {
					return false;
				}
			}
		}

		return true;
	}

	// Save image to a file as .png or .jpg
	public void save(File file) {
		String fileName = file.getName();
		
		String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
		suffix = suffix.toLowerCase();
		if (suffix.equals("jpg") || suffix.equals("png")) {
			try {
				ImageIO.write(image, suffix, file);
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		} else {
			System.out.println("Filename must end in .jpg or .png");
		}
	}

}
