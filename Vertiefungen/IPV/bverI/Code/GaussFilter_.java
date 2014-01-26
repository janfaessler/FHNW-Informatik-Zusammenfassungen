//BVER
//Übung 6 - Aufgabe 2.3

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class GaussFilter_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G;		
	}

	private void convolution(ImageProcessor ip, int[] filter, int quot, int hotSpot) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
    		final int len = filter.length;
    
    		ImageProcessor copy = ip.duplicate();
		int sum, x, y; 

		// horizontale Filterung an der Position u,v
    		for (int v = 0; v < h; v++) {
      		for (int u = 0; u < w; u++) {
        			sum = 0;
            		for (int i = 0; i < len; i++) {
            			// Randbehandlung
					x = u + i - hotSpot;
            			if (x < 0) x = -x;
            			if (x >= w) x = 2*w - 1 - x;
            		
					// neue Intensität berechnen
                			sum = sum + filter[i]*ip.getPixel(x, v);
            		}
				sum /= quot; 

				// neue Intensität setzen
        			copy.putPixel(u, v, sum);      
      		} 	
		}

		// vertikale Filterung an der Position u,v
    		for (int v = 0; v < h; v++) {
      		for (int u = 0; u < w; u++) {
        			sum = 0;
        			for (int j = 0; j < len; j++) {
            			// Randbehandlung
					y = v + j - hotSpot;	
            			if (y < 0) y = -y;
            			if (y >= h) y = 2*h - 1 - y;
            		
					// neue Intensität berechnen
                			sum = sum + filter[j]*copy.getPixel(u, y);
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
		final int sigma = 3;
		final int sigma2 = 2*sigma*sigma;
		final int factor = 100;
		
		int quot, x, len = 5*sigma;
		int len2 = len/2;
		len = 2*len2 + 1;
		
 		// Filtermatrix erzeugen
    		int[] filter = new int[len];
		quot = 0;
		for (int i=0; i < len; i++) {
			x = i - len2;
			filter[i] = (int)(factor*Math.exp(-x*x/(double)sigma2));
			quot += filter[i];
		}

		convolution(ip, filter, quot, len2);
	}
}
