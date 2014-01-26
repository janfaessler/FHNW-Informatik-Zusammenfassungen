//BVER
//Übung 6 - Aufgabe 2.1

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class LinearesFilter_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G;		
	}

	private void convolution(ImageProcessor ip, int[][] filter, int quot, int hotSpotX, int hotSpotY) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
    		final int fh = filter.length;
    		final int fw = filter[0].length;
    
    		ImageProcessor copy = ip.duplicate();
		int sum, x, y; 

    		for (int v = 0; v < h; v++) {
      		for (int u = 0; u < w; u++) {
        
				// Anwendung des Filters an der Position u,v
        			sum = 0;
        			for (int j = 0; j < fh; j++) {
            			for (int i = 0; i < fw; i++) {
            				// Randbehandlung
						x = u + i - hotSpotX;
						y = v + j - hotSpotY;	
            				if (x < 0) x = -x;
            				if (x >= w) x = 2*w - 1 - x;
            				if (y < 0) y = -y;
            				if (y >= h) y = 2*h - 1 - y;
            		
						// neue Intensität berechnen
                				sum = sum + filter[j][i]*copy.getPixel(x, y);
            			}
        			}
				sum /= quot; 

        			// clamping
        			if (sum < 0)   sum = 0;
       			if (sum > 255) sum = 255;

				// neue Intensität setzen
        			ip.putPixel(u, v, sum);      
      		} 	
		}
	}

	public void run(ImageProcessor ip) {
 		// filtermatrix H
    		int[][] filter = {
        		{3, 5, 3},
        		{5, 8, 5},
        		{3, 5, 3}
    		};
    		int quot = 40;

		convolution(ip, filter, quot, 1, 1);
	}
}
