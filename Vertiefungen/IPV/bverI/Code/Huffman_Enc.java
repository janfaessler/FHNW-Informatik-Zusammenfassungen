// BVERI
// Übung 2.2

import ij.*;
import ij.process.*;
import ij.plugin.filter.*;
import ij.gui.*;
import ij.io.*;
import java.io.*;
import java.util.*;

/**
 * Huffman encoder
 * @author Christoph Stamm
 *
 */
public class Huffman_Enc implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
		final int size = w*h;

		int[] hist = ip.getHistogram();
		Leaf[] codes = new Leaf[hist.length];
		Node root = createHuffmanTree(hist, codes, size);
		BitSet data = encodeImage(ip, codes);

		// compute mean code length
		//StringBuilder sb = new StringBuilder();
		double sum = 0;
		for(int i=0; i < codes.length; i++) {
			//sb.append("" + i + ": " + codes[i].getProbability() + ", " + codes[i].getCodeLen() + ", " + codes[i].getCode() + "\n");
			sum += codes[i].getProbability()*codes[i].getCodeLen();
		}

		new MessageDialog(imp.getWindow(), "wirklicher Speicherbedarf", 
			"mittlere Codelänge: " + sum + "\n" +
			"Speicherbedarf: " + sum*size/8 + " Byte");

		SaveDialog sd = new SaveDialog("Save image in HUF format", imp.getTitle(), ".huf");
		if (sd.getFileName() != null) {
			imp.startTiming();
			write(sd.getFileName(), sd.getDirectory(), ip, root, data);
		}
		
	}
	
	/**
	 * Build code tree
	 * @param hist histogram of input image
	 * @param codes code table
	 * @param size number of pixels
	 * @return root node of code tree
	 */
	private Node createHuffmanTree(int[] hist, Leaf[] codes, int size) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>(hist.length);
        	double ld = Math.log(2), H = 0, p;

		// compute probabilities and entropy
		for(int i=0; i < hist.length; i++) {
			p = (double)(hist[i])/size;
            	H -= ((p == 0) ? 0 : p*Math.log(p)/ld);
			codes[i] = new Leaf(p, (byte)i); 
			pq.add(codes[i]);
		}

		// compute needed memory
		new MessageDialog(imp.getWindow(), "geschätzter Speicherbedarf", 
			"geschätzte mittlere Codelänge: [" + (float)H + ", " + (float)(H + 1) + ")\n" +
			"geschätzter Speicherbedarf: [" + (float)H*size/8 + ", " + (float)(H + 1)*size/8 + ") Byte");

		// build Huffman tree
		while(pq.size() >= 2) {
			Node v1 = pq.poll();
			Node v2 = pq.poll();
			
			pq.add(new Node(v1, v2)); 
		}
		Node root = pq.poll();
		//new MessageDialog(imp.getWindow(), "Probability", "p = " + root.getProbability()); 
		root.setCode(0, 0);

		return root;
	}

	/**
	 * Encode image
	 * @param ip
	 * @param codes code table
	 * @return encoded data
	 */
	private BitSet encodeImage(ImageProcessor ip, Leaf[] codes) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
		BitSet bs = new BitSet();
		int index = 0;
		Node node;
		long code;
	
		//encode image data:
		for (int v = 0; v < h; v++) {
			for (int u = 0; u < w; u++) {
				node = codes[ip.getPixel(u, v)];
				code = node.getCode();
				for (int i = node.getCodeLen() - 1; i >= 0; i--) {
					bs.set(index + i, code%2 == 1);
					code >>= 1;
				}
				index += node.getCodeLen();
			}
		}
		return bs;
	}
	
	/**
	 * Write file
	 * @param filename
	 * @param directory
	 * @param ip
	 * @param root root of code tree
	 * @param data encoded data
	 */
	private void write(String filename, String dir, ImageProcessor ip, Node root, BitSet data) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
		
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dir + filename));
			
			// write Header
			out.writeInt(w);
			out.writeInt(h);

			// write code tree
			out.writeObject(root);

			// write compressed data
			out.writeObject(data);

			// close file
			out.close();
			
			IJ.showMessage("Huffman Encoder", "Image has been successfully saved.\n \n");
		} catch(Exception e){
			IJ.error("Huffman Encoder", e.getMessage());
		}
	}

}
