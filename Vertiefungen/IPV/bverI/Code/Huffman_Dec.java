/**
 * Huffman decoder
 * @author Christoph Stamm
 *
 */
import java.io.*;

import ij.*;
import ij.io.*;
import ij.gui.*;
import ij.plugin.*;
import java.util.*;

public class Huffman_Dec implements PlugIn {
	ImagePlus img;

	public Huffman_Dec() {
		img = null;
	}

	public void run(String arg) {
		OpenDialog od = new OpenDialog("Open HUF encoded image ...", arg);
		String directory = od.getDirectory();
		String fileName = od.getFileName();
		if (fileName == null)
			return;

		IJ.showStatus("Opening: " + directory + fileName);

		read(directory, fileName);

		if (img == null)
			return;

		img.show();

	}

	protected void read(String dir, String filename) {
		int width, height;
		
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(dir + filename));

			// read Header
			width = in.readInt();
			height = in.readInt();
			
			// read code tree
			Node root = (Node)in.readObject();

			// read compressed data
			BitSet data = (BitSet)in.readObject();

			// close file
			in.close();
			
			// create output image
			img = NewImage.createByteImage(filename, width, height, 1, NewImage.FILL_BLACK);

			// fill in data
			Node node;
			int index = 0;
			byte[] pixels = (byte[])img.getProcessor().getPixels();
			for (int i = 0; i < pixels.length; i++) {
				node = root;
				while(node.isInnerNode()) {
					node = node.decodeBit(data.get(index++));
				}
				pixels[i] = ((Leaf)node).getIntensity();
			}
			
		} catch (Exception e) {
			IJ.error("Huffman Decoder", e.getMessage());
			return;
		}
	}
}
