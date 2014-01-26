import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class LinHistAusgleich_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		//return DOES_ALL;
		return DOES_8G;
	}

	public void run(ImageProcessor ip) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();

		// Histogramm erstellen
		int[] H = ip.getHistogram();
		int K = H.length; // Anzahl Intensitäts-Stufen
		int k1 = K-1; 	// maximale Intensität
		int n = w*h;	// Anzahl Pixel
		int[] LUT = new int[K]; // lookup table
		int hKum = 0;	// kumuliertes Histogramm

		for (int i = 0; i < K; i++) {
			// kumuliertes Histogramm berechnen
			hKum += H[i];

			// Histogrammausgleich auf LUT anwenden
			LUT[i] = hKum*k1/n;
		}

		// LUT aufs Bild anwenden		
		ip.applyTable(LUT);
	}
}
