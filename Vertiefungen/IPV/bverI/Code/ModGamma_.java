// Uebung 3.2

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class ModGamma_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G;
	}

	public void run(ImageProcessor ip) {
		final int K = 256; //Anzahl Intensitäten
		final int iMax = K - 1;
		final double gamma = 1/2.4;	// sRGB
		final double x0 = 0.00304;	// sRGB
		//final double gamma = 1/2.222;	// ITU
		//final double x0 = 0.018;	// ITU

		int[] LUT = new int[K]; //Lookup Table

		// Vorberechnungen
		final double s = gamma/(x0*(gamma - 1) + Math.pow(x0, 1 - gamma));
		final double d = 1/(Math.pow(x0, gamma)*(gamma - 1) + 1) - 1;

		// LUT erstellen
		double x;
		for(int i = 0; i < K; i++){
			x = (double)i/iMax;
			if (x <= x0) {
				LUT[i] = (int) Math.round(s*x*iMax);
			} else {	
				LUT[i] = (int) Math.round(((1 + d)*Math.pow(x, gamma) - d)*iMax);
			}			
		}

		ip.applyTable(LUT);
	}

}
