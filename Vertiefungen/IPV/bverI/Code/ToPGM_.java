// BVERI
// Übung 2.1

import ij.*;
import ij.process.*;
import ij.io.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;
import java.io.*;

import ij.plugin.filter.*;

public class ToPGM_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		SaveDialog sd = new SaveDialog("Save image as PGM in ASCII format", imp.getTitle(), ".pgm");
		if (sd.getFileName() != null) {
			imp.startTiming();
			savePGM(sd.getFileName(), sd.getDirectory(), ip);
		}
	}

	private void savePGM(String filename, String directory, ImageProcessor ip) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
		final int maxGrayVal = 255;
		int p;
		
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(directory + filename));
			
			// Write Header:
			out.write("P2\n");
			out.write("# " + filename + "\n");
			out.write(w + " " + h + "\n");
			out.write(maxGrayVal + "\n");
			
			//Write image data:
			for (int v = 0; v < h; v++) {
				for (int u = 0; u < w; u++) {
					p = ip.getPixel(u,v);
					out.write(ip.getPixel(u, v) + " ");
				}
				out.write("\n");
			}
			out.close();
			
			IJ.showMessage("PGM Writer", "Image has been successfully saved.\n \n");
		} catch(Exception e){
			IJ.showMessage("PGM Writer", "An error occured writing the file.\n \n" + e.toString());
		}
	}
}
