//BVER
//Übung 7.1

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class GrauwertDilation_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G;		
	}

	private void dilation(ImageProcessor ip, int[][] struct, int hotSpotX, int hotSpotY) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
    		final int sh = struct.length;
    		final int sw = struct[0].length;
    
    		ImageProcessor copy = ip.duplicate();
		int tmp, max, x, y; 

    		for (int v = 0; v < h; v++) {
      		for (int u = 0; u < w; u++) {
        
				// Anwendung des Filters an der Position u,v
        			max = -1;
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
                					tmp = struct[j][i] + copy.get(x, y);
							if (tmp > max) max = tmp;
						}
            			}
        			}
        			// clamping
        			if (max < 0)   max = 0;
       			if (max > 255) max = 255;

				// neue Intensität setzen
        			ip.putPixel(u, v, max);      
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

		dilation(ip, struct, 1, 1);
	}
}
