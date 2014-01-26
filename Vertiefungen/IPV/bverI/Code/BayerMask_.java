// BVERI
// Übung 1.3

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class BayerMask_ implements PlugInFilter {
	static final int R = 0;
	static final int G = 1;
	static final int B = 2;
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
		ImageProcessor ip2 = new ColorProcessor(w, h);
		int[] rgb = new int[3];
		int g1, g2, g3, g4;
		int b1, b2, b3, b4;
		int r1, r2, r3, r4;
		
		// um die Aufgabe zu vereinfachen, führen wir keine Randbehandlung durch und lassen am Rand 2 Pixel schwarz
		for (int v=2; v < h - 2; v++) {
			for (int u=2; u < w - 2; u++) {
				// hier die fehlenden Farbkomponenten interpolieren
				if (v%2 == 0) {
					if (u%2 == 0) {
						// b
						rgb[B] = ip.getPixel(u, v);
						g1 = ip.getPixel(u-1, v);
						g2 = ip.getPixel(u+1, v);
						g3 = ip.getPixel(u, v-1);
						g4 = ip.getPixel(u, v+1);
						rgb[G] = (g1 + g2 + g3 + g4) >> 2;
						r1 = ip.getPixel(u-1, v-1);
						r2 = ip.getPixel(u+1, v-1);
						r3 = ip.getPixel(u-1, v+1);
						r4 = ip.getPixel(u+1, v+1);
						rgb[R] = (r1 + r2 + r3 + r4) >> 2;
					} else {
						// g
						rgb[G] = ip.getPixel(u, v);
						r1 = ip.getPixel(u, v-1);
						r2 = ip.getPixel(u, v+1);
						rgb[R] = (r1 + r2) >> 1;
						b1 = ip.getPixel(u-1, v);
						b2 = ip.getPixel(u+1, v);
						rgb[B] = (b1 + b2) >> 1;
					}
				} else {
					if (u%2 == 0) {
						// g
						rgb[G] = ip.getPixel(u, v);
						r1 = ip.getPixel(u-1, v);
						r2 = ip.getPixel(u+1, v);
						rgb[R] = (r1 + r2) >> 1;
						b1 = ip.getPixel(u, v-1);
						b2 = ip.getPixel(u, v+1);
						rgb[B] = (b1 + b2) >> 1;
					} else {
						// r
						rgb[R] = ip.getPixel(u, v);
						g1 = ip.getPixel(u-1, v);
						g2 = ip.getPixel(u+1, v);
						g3 = ip.getPixel(u, v-1);
						g4 = ip.getPixel(u, v+1);
						rgb[G] = (g1 + g2 + g3 + g4) >> 2;
						b1 = ip.getPixel(u-1, v-1);
						b2 = ip.getPixel(u+1, v-1);
						b3 = ip.getPixel(u-1, v+1);
						b4 = ip.getPixel(u+1, v+1);
						rgb[B] = (b1 + b2 + b3 + b4) >> 2;
					}
				}	

				ip2.putPixel(u, v, rgb);
			}
		}
		ImagePlus imp2 = new ImagePlus("RGB", ip2);
		imp2.show();
	}

}
