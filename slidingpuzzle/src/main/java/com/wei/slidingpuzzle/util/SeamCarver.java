package com.wei.slidingpuzzle.util;

import java.awt.Color;


public class SeamCarver {
	private int H;
	private int W;
	private int[][] colorData;
	
	// constructor
	public SeamCarver(UploadedImage image)  {
		H = image.getHeight();
		W = image.getWidth();
		
		colorData = new int[H][W];		
		for (int i = 0; i < H; i++)  {		
			for (int j = 0; j < W; j++)  {
				colorData[i][j] = image.get(j, i).getRGB();
			}
		}		
	}
	
	// current image
	public UploadedImage getCurrImage()  {
		UploadedImage currImg = new UploadedImage(W, H);
		
		for (int i = 0; i < H; i++)  {			
			for (int j = 0; j < W; j++)  {
				Color color = new Color(colorData[i][j]);
				currImg.set(j, i, color);
			}
		}		
		return currImg;
	}
		
	// width of current picture
	public int currWidth()  {
		return W;
	}
		
	// height of current picture
	public int currHeight()  {
		return H;
	}
		
	// energy of pixel at column x and row y
	public double energy(int x, int y)  {
		if (x < 0 || y < 0 || x > W - 1 || y > H - 1)  {
			throw new IndexOutOfBoundsException();
		}
		
		if (x == 0 || y == 0 || x == W - 1 || y == H - 1)  {
			return 195075;  // 3 * (255 * 255)
		}
		else  {
			return deltaX(x, y) + deltaY(x, y);
		}
	}
		
	private double deltaX(int x, int y)  {
		int rgb1 = colorData[y][x - 1];
		int rgb2 = colorData[y][x + 1];
		
		int deltaR = (rgb1 >> 16) & 0x000000FF - (rgb2 >> 16) & 0x000000FF;		 
		int deltaG = (rgb1 >> 8) & 0x000000FF - (rgb2 >> 8) & 0x000000FF;		 
		int deltaB = (rgb1) & 0x000000FF - (rgb2) & 0x000000FF;
		 
		return deltaR * deltaR + deltaG * deltaG + deltaB * deltaB;		 
	}
		
	private double deltaY(int x, int y)  {
		int rgb1 = colorData[y - 1][x];
		int rgb2 = colorData[y + 1][x];
		
		int deltaR = (rgb1 >> 16) & 0x000000FF - (rgb2 >> 16) & 0x000000FF;		 
		int deltaG = (rgb1 >> 8) & 0x000000FF - (rgb2 >> 8) & 0x000000FF;		 
		int deltaB = (rgb1) & 0x000000FF - (rgb2) & 0x000000FF;
		 
		return deltaR * deltaR + deltaG * deltaG + deltaB * deltaB;		 	
	}
		
	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam()  {
		return findSeam(false);
	}
		
	// sequence of indices for vertical seam
	public int[] findVerticalSeam()  {
		return findSeam(true);
	}
	
	private int[] findSeam(boolean isVertical)  {
		int width = isVertical ? W : H;
		int height = isVertical ? H : W;
		
		double[][] energyTo = new double[height][width];
		int[][] vxTo = new int[height][width]; 
		
		// initialization 
		for (int x = 0; x < width; x++)  {
			energyTo[0][x] = 195075;
		} 
			
		for (int y = 1; y < height; y++)  {
			for (int x = 0; x < width; x++)  {
				energyTo[y][x] = Double.POSITIVE_INFINITY;
			}
		}
		
		//relaxing each pixel by topological order
		for (int y = 0; y < height - 1; y++)  {
			for (int x = 0; x < width; x++)  {
				if (x > 0)  {
					relax(x, y, x-1, y + 1, energyTo, vxTo, isVertical);
				}
				
				relax(x, y, x, y + 1, energyTo, vxTo, isVertical);
				
				if (x < width - 1)  {
					relax(x, y, x + 1, y + 1, energyTo, vxTo, isVertical);
				}
			}
		}
		
		// find minimum energy path
		double minEnergy = Double.POSITIVE_INFINITY;
		int minVx = -1;
		int prevX;
		int[] seam = new int[height];
		
		for (int i = 0; i < width; i++)  {
			if (minEnergy > energyTo[height - 1][i])  {
				minEnergy = energyTo[height - 1][i];
				minVx = i;
			}
		}
		
		seam[height - 1] = minVx;
		prevX = vxTo[height - 1][minVx];
		for (int j = height - 2; j >= 0; j--)  {
			seam[j] = prevX;
			prevX = vxTo[j][prevX];
		}
		
		return seam;
	}
	
	private void relax(int x1, int y1, int x2, int y2, double[][] energyTo, int[][] vxTo, boolean isVertical)  {
		double energy2 = isVertical ? energy(x2, y2) : energy(y2, x2);
		if (energyTo[y2][x2] > energyTo[y1][x1] + energy2)  {
			energyTo[y2][x2] = energyTo[y1][x1] + energy2;
			vxTo[y2][x2] = x1;
		}
	}
		
	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam)  {
		 checkSeam(seam, W);
		 
		 int[][] colorTemp = new int[--H][W];
		 for (int j = 0; j < W; j++)  {
			 for (int i = 0; i < H; i++)  {
				 colorTemp[i][j] = (i < seam[j]) ? colorData[i][j] : colorData[i + 1][j];
			 }
		 }
		 
		 colorData = colorTemp;
    }
	
	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam)  {
		checkSeam(seam, H);
		
		int[][] colorTemp = new int[H][--W];
    	for (int i = 0; i < H; i++)  {
    		System.arraycopy(colorData[i], 0, colorTemp[i], 0, seam[i]);
    		System.arraycopy(colorData[i], seam[i] + 1, colorTemp[i], seam[i], W - seam[i]);
    	}
    	
    	colorData = colorTemp;
	}

	private void checkSeam(int[] seam, int expLen)  {
		if (expLen <= 1)
			throw new IllegalArgumentException();
		
		if (seam == null) 
			throw new NullPointerException();
		
		if (seam.length != expLen) 
			throw new IllegalArgumentException();
		
		for (int i = 0; i < expLen - 1; i++)  {
			if (Math.abs(seam[i] - seam[i+1]) > 1)
				throw new IllegalArgumentException();
		}
	}

}
