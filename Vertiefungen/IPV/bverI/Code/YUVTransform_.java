// Uebung 5.1

import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import java.awt.*;
import ij.gui.*;

public class YUVTransform_ implements PlugInFilter {
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

	public void run(ImageProcessor ip) {
		// Bit-Masken
		// 00FF0000 = Rot
		// 0000FF00 = Grün
		// 000000FF= Blau

		final int w = ip.getWidth();
		final int h = ip.getHeight();
		final int size = w*h;

		int color;
		int Y, U, V;
		int R, G, B;
		int oldR, oldG, oldB;
		double psnrR = 0, psnrG = 0, psnrB = 0;

		ImageProcessor newIp = ip.duplicate();
		ImagePlus newImg = imp.createImagePlus();
		newImg.setProcessor("Nach YUV-Trans und back", newIp);

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				color = ip.getPixel(x, y);
				oldR = (0x00FF0000 & color) >> 16;
				oldG = (0x0000FF00 & color) >> 8;
				oldB = (0x000000FF & color);

				// Aufgabe a
				Y = (int)Math.round((0.299*oldR) + (0.587*oldG) +(0.114*oldB));
				U = (int)Math.round((-0.147*oldR) + (-0.289*oldG) +(0.436*oldB));
				V = (int)Math.round((0.615*oldR) + (-0.515*oldG) +(-0.1*oldB));
				//U= (int)Math.round(0.492*(oldB-Y));
				//V =(int)Math.round(0.877*(oldR-Y));

				Y = clamp(Y);
				// kein clamping für U und V, weil diese negative Werte haben dürfen

				// Aufgabe b
				R = (int)Math.round(Y - 3.9457e-005*U + 1.1398*V);
				G = (int)Math.round(Y - 0.39461*U - 0.5805*V);
				B = (int)Math.round(Y + 2.032*U - 0.00048138*V);
				
				//G=(int)Math.round(Y-(0.00003947313749*U)-(0.580809209*V));
				//R=(int)Math.round((1.140250855*V)+Y);
				//B=(int)Math.round((2.032520325*U)+Y);

				R = clamp(R);
				G = clamp(G);
				B = clamp(B);

				// Aufgabe c
				psnrR += (oldR - R)*(oldR - R);
				psnrG += (oldG - G)*(oldG - G);
				psnrB += (oldB - B)*(oldB - B);

				newIp.putPixel(x, y, (R << 16) | (G << 8) |  B);
			}
		}

		// Aufgabe c
		psnrR = 20*Math.log10(255/Math.sqrt(psnrR/size));
		psnrB = 20*Math.log10(255/Math.sqrt(psnrG/size));
		psnrG = 20*Math.log10(255/Math.sqrt(psnrB/size));
		
		newImg.show();

		MessageDialog msg = new MessageDialog(imp.getWindow(), "PSNR", "Red: " + psnrR + ", Green:  " + psnrG + ", Blue:  " + psnrB);
	}
}
