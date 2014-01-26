//BVER
//Übung 7.2

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class GrauwertErosion_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G;		
	}

	private void erosion(ImageProcessor ip, int[][] struct, int hotSpotX, int hotSpotY) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
    		final int sh = struct.length;
    		final int sw = struct[0].length;
    
    		ImageProcessor copy = ip.duplicate();
		int tmp, min, x, y; 

    		for (int v = 0; v < h; v++) {
      		for (int u = 0; u < w; u++) {
        
				// Anwendung des Filters an der Position u,v
        			min = Integer.MAX_VALUE;
        			for (int j = 0; j < sh; j++) {
            			for (int i = 0; i < sw; i++) {
						if (struct[j][i] >= 0) {
            					// Randbehandlung
							x = u + i - hotSpotX;
							y = v + j - hotSpotY;	
            					if (x < 0) x = -x;
            					if (x >= w) x = 2*w - 1 - x;
            					if (y < 0) y = -y;
            					if (y >= h) y = 2*h - 1 - y;
            		
							// neue Intensität berechnen
                					tmp = copy.get(x, y) - struct[j][i];
							if (tmp < min) min = tmp;
						}
            			}
        			}
        			// clamping
        			if (min < 0)   min = 0;
       			if (min > 255) min = 255;

				// neue Intensität setzen
        			ip.putPixel(u, v, min);      
      		} 	
		}
	}

	public void run(ImageProcessor ip) {
 		// Strukturmatrix H
    		int[][] struct = {
        		{-1, 1, -1},
        		{ 1, 2,  1},
        		{-1, 1, -1}
    		};

		erosion(ip, struct, 1, 1);
	}
}
