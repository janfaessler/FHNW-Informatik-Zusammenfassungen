//BVER
//Übung 6 - Aufgabe 2.2

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;
import java.util.*;

public class GewMedianFilter_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G;		
	}

	private void median(ImageProcessor ip, int[][] gewichte, int hotSpotX, int hotSpotY) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
    		final int fh = gewichte.length;
    		final int fw = gewichte[0].length;
    
    		ImageProcessor copy = ip.duplicate();
		int gewSum, val, pos, x, y; 

		// Summe der Gewichte bestimmen
		gewSum = 0;
    		for (int v = 0; v < fh; v++) {
      		for (int u = 0; u < fw; u++) {
				gewSum += gewichte[v][u];
			}
		}
		int[] values = new int[gewSum];

    		for (int v = 0; v < h; v++) {
      		for (int u = 0; u < w; u++) {
        
				// Anwendung des Filters an der Position u,v
				pos = 0;
        			for (int j = 0; j < fh; j++) {
            			for (int i = 0; i < fw; i++) {
            				// Randbehandlung
						x = u + i - hotSpotX;
						y = v + j - hotSpotY;	
            				if (x < 0) x = -x;
            				if (x >= w) x = 2*w - 1 - x;
            				if (y < 0) y = -y;
            				if (y >= h) y = 2*h - 1 - y;
            		
						// Intensität im Array zwischenspeichern
						for(int k = 0; k < gewichte[j][i]; k++) {
                					values[pos] = copy.getPixel(x, y);
							pos++;
						}
            			}
        			}

				// neue Intensität bestimmen
				Arrays.sort(values);
				pos = values.length/2;
				if (values.length%2 == 0) {
					// gerade Anzahl: Median als arithmetischer Durchschnitt
					val = (values[pos - 1] + values[pos])/2;
				} else {
					val = values[pos];
				}
	
        			// clamping
        			if (val < 0)   val = 0;
       			if (val > 255) val = 255;

				// neue Intensität setzen
        			ip.putPixel(u, v, val);      
      		} 	
		}
	}

	public void run(ImageProcessor ip) {
 		// Gewichtismatrix
    		int[][] gewichte = {
        		{1, 2, 1},
        		{2, 4, 2},
        		{1, 2, 1}
    		};

		median(ip, gewichte, 1, 1);
	}
}
