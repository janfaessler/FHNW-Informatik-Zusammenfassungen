// Uebung 5.2

import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import java.awt.*;
import ij.gui.*;

public class RGBtoGray_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_ALL + NO_CHANGES;
	}

	private int clamp(int v) {
		if (v > 255) return 255;
		else if (v < 0) return 0;
		return v;
	}

	private int[] createInverseGammaLUT() {
		final int K = 256; //Anzahl Intensitäten
		final int iMax = K - 1;
		final double gamma = 1/2.4;
		final double x0 = 0.00304;

		int[] LUT = new int[K]; //Lookup Table

		// Vorberechnungen
		final double s = gamma/(x0*(gamma - 1) + Math.pow(x0, 1 - gamma));
		final double d = 1/(Math.pow(x0, gamma)*(gamma - 1) + 1) - 1;
		//MessageDialog msg = new MessageDialog(imp.getWindow(), "LUT", "s: " + s + ", d: " + d);

		// LUT erstellen
		double y;
		for(int i = 0; i < K; i++){
			y = (double)i/iMax;
			if (y <= s*x0) {
				LUT[i] = (int) Math.round(y/s*iMax);
			} else {	
				LUT[i] = (int) Math.round(Math.pow((y + d)/(1 + d), 1/gamma)*iMax);
			}			
		}
		return LUT;
	}

	public void run(ImageProcessor ip) {
		// Bit-Masken
		// 00FF0000 = Rot
		// 0000FF00 = Grün
		// 000000FF= Blau

		final int w = ip.getWidth();
		final int h = ip.getHeight();
		final int size = w*h;

		int color;
		int Y1, Y2;
		int R, G, B;
		double psnr = 0;
		int[] LUT = createInverseGammaLUT();	// Aufgabe a

		ImageProcessor ip2 = ip.duplicate();
		ImagePlus imp2 = imp.createImagePlus();;
		imp2.setProcessor("Gammakorrigiertes RGB", ip2);

		ImageProcessor grayIp1 = new ByteProcessor(w, h);
		ImagePlus grayImg1 = imp.createImagePlus();;
		grayImg1.setProcessor("Graustufen-Bild", grayIp1);
		
		ImageProcessor grayIp2 = new ByteProcessor(w, h);
		ImagePlus grayImg2 = imp.createImagePlus();;
		grayImg2.setProcessor("Graustufen-Bild ohne Linearisierung", grayIp2);

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				color = ip.get(x, y);
				R = (0x00FF0000 & color) >> 16;
				G = (0x0000FF00 & color) >> 8;
				B = (0x000000FF & color);

				// Aufgabe c
				Y2 = clamp((int)Math.round(0.309*R + 0.609*G + 0.082*B));
				grayIp2.putPixel(x, y, Y2);

				// Aufgabe a
				R = LUT[R];
				G = LUT[G];
				B = LUT[B];
				ip2.putPixel(x, y, ((((R << 8) | G) << 8) | B));

				// Aufgabe b
				Y1 = clamp((int)Math.round(0.299*R + 0.587*G + 0.114*B));	// YUV
				Y1 = clamp((int)Math.round(0.2125*R + 0.7154*G + 0.072*B));	// ITU
				grayIp1.putPixel(x, y, Y1);

				// Aufgabe d
				psnr += (Y1 - Y2)*(Y1 - Y2);
			}
		}

		// Aufgabe d
		psnr = 20*Math.log10(255/Math.sqrt(psnr/size));
		
		imp2.show();
		grayImg1.show();
		grayImg2.show();

		MessageDialog msg = new MessageDialog(imp.getWindow(), "PSNR", "PSNR: " + psnr);
	}
}
