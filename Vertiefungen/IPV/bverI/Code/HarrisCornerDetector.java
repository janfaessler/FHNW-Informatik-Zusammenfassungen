import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.Convolver;
import ij.process.Blitter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

public class HarrisCornerDetector {
	public static final float DEFAULT_ALPHA = 0.050f;
	public static final int DEFAULT_THRESHOLD = 20000;
	
	float alpha = DEFAULT_ALPHA;
	int threshold = DEFAULT_THRESHOLD;
	double dmin = 10;
	
	final int border = 20;
	
	final float [] pfilt = {0.223755f, 0.552490f, 0.223755f};
	final float [] dfilt = {0.453014f, 0.0f, -0.453014f};
	final float [] bfilt = {0.01563f, 0.09375f, 0.234375f, 0.3125f, 
					0.234375f, 0.09375f, 0.01563f};
					// = {1, 6, 15, 20, 15, 6, 1}/64

	ImageProcessor ipOrig;
	FloatProcessor A;
	FloatProcessor B;
	FloatProcessor C;
	FloatProcessor Q;
	
	Vector corners;
	
	HarrisCornerDetector(ImageProcessor ip){
		this.ipOrig = ip;
	}
	
	HarrisCornerDetector(ImageProcessor ip, float alpha, int threshold){
		this.ipOrig = ip;
		this.alpha = alpha;
		this.threshold = threshold;
	}
	
	void findCorners(){
		makeDerivatives();
		makeCrf();
		corners = collectCorners(border);
		corners = cleanupCorners(corners);
	}
	
	void makeDerivatives(){
		FloatProcessor Ix = (FloatProcessor) ipOrig.convertToFloat();
		FloatProcessor Iy = (FloatProcessor) ipOrig.convertToFloat();
		
		Ix = convolve1h(convolve1h(Ix, pfilt), dfilt);
		Iy = convolve1v(convolve1v(Iy, pfilt), dfilt);
		
		A = sqr((FloatProcessor) Ix.duplicate());
		A = convolve2(A, bfilt);

		B = sqr((FloatProcessor) Iy.duplicate());
		B = convolve2(B, bfilt);

		C = mult((FloatProcessor)Ix.duplicate(), Iy);
		C = convolve2(C, bfilt);
	}
	
	void makeCrf(){ 		// Corner Response Function
		int w = ipOrig.getWidth();
		int h = ipOrig.getHeight();
		Q = new FloatProcessor(w, h);

		float[] Apix = (float[]) A.getPixels();
		float[] Bpix = (float[]) B.getPixels();
		float[] Cpix = (float[]) C.getPixels();
		float[] Qpix = (float[]) Q.getPixels();
	
		for (int v = 0; v < h; v++){
			for (int u = 0; u < w; u++){
				int i = v * w + u;
				float a = Apix[i];
				float b = Bpix[i];
				float c = Cpix[i];
				// Determinante berechnen 
				// Spur berechnen
				// Corner strength Qpix berechnen

//////////// Löschen
				float det = a*b - c*c;
				float trace = a+b;
				Qpix[i] = det -alpha * (trace * trace);
//////////// Ende Löschen
			}
		}
	}

	Vector collectCorners(int border){
		Vector<Corner> cornerList = new Vector<Corner>(1000);
		int w = Q.getWidth();
		int h = Q.getHeight();
		
		float[] Qpix = (float[]) Q.getPixels();
		for (int v = border; v<h-border; v++){
			for (int u = border; u<w-border; u++){
				float q = Qpix[v*w + u];
				if (q>threshold && isLocalMax(Q, u, v)){
					Corner c = new Corner(u, v, q);
					cornerList.add(c);
				}
			}
		}
		Collections.sort(cornerList);
		return cornerList;
	}
	
	Vector cleanupCorners(Vector corners){
		double dmin2 = dmin * dmin;
		Object[] cornerArray = corners.toArray();
		Vector<Corner> goodCorners = new Vector<Corner>(corners.size());
		for (int i=0; i<cornerArray.length; i++){
			if (cornerArray[i] != null){
				Corner c1 = (Corner) cornerArray[i];
				goodCorners.add(c1);
				for (int j = i+1; j<cornerArray.length; j++){
					if(cornerArray[j] != null){
						Corner c2 = (Corner) cornerArray[j];
						if (c1.dist2(c2)<dmin2){
							cornerArray[j] = null;
						}
					}
				}
			}
		}

		return goodCorners;
	}
	
	void printCornerPoints(Vector crf){
		Iterator it = crf.iterator();
		for (int i=0; it.hasNext(); i++){
			Corner ipt = (Corner) it.next();
			IJ.write(i + ": " + (int)ipt.q + " " + ipt.u + " " + ipt.v);
		}
	}
	
	ImageProcessor showCornerPoints(ImageProcessor ip){
		ByteProcessor ipResult = (ByteProcessor)ip.duplicate();
		
		int[] lookupTable = new int[256];
		for (int i = 0; i< 256; i++){
			lookupTable[i] = 128+(i/2);
		}
		ipResult.applyTable(lookupTable);
		
		Iterator it = corners.iterator();
		for (int i=0; it.hasNext(); i++){
			Corner c = (Corner) it.next();
			c.draw(ipResult);
		}
		return ipResult;
	}
	
	void showProcessor (ImageProcessor ip, String title){
		ImagePlus win = new ImagePlus(title, ip);
		win.show();
	}
	
	void dummy(){
		Corner[] cornerArray = new Corner[100];
		cornerArray[0] = new Corner(10, 20, 0.7f);
		cornerArray[2] = new Corner(10, 20, 1.7f);
		Arrays.sort(cornerArray);
	}
	
	static FloatProcessor convolve1h(FloatProcessor p, float[] h){
		Convolver conv = new Convolver();
		conv.setNormalize(false);
		conv.convolve(p, h, 1, h.length);
		return p;
	}

	static FloatProcessor convolve1v(FloatProcessor p, float[] h){
		Convolver conv = new Convolver();
		conv.setNormalize(false);
		conv.convolve(p, h, h.length, 1);
		return p;
	}

	static FloatProcessor convolve2(FloatProcessor p, float[] h){
		convolve1h(p, h);
		convolve1v(p, h);
		return p;
	}
	
	static FloatProcessor sqr(FloatProcessor fp1){
		fp1.sqr();
		return fp1;
	}
	
	static FloatProcessor mult(FloatProcessor  fp1, FloatProcessor fp2){
		int mode = Blitter.MULTIPLY;
		fp1.copyBits(fp2, 0, 0, mode);
		return fp1;
	}
	
	static boolean isLocalMax(FloatProcessor fp, int u, int v){
		int w = fp.getWidth();
		int h = fp.getHeight();
		if (u <= 0 || u >= w-1 || v <= 0 || v >= h-1){
			return false;
		}
		else {
			float[] pix = (float[]) fp.getPixels();
			int i0 = (v-1)*w+u;
			int i1 = v*w+u;
			int i2 = (v+1)*w+u;
			float cp = pix[i1];
			
			return 
			cp >= pix[i0-1] && cp >= pix[i0] && cp >= pix[i0+1] &&
			cp >= pix[i1-1] && 		      cp > pix[i1+1] &&
			cp >= pix[i2-1] && cp >= pix[i2] && cp >= pix[i2+1];
		}
	}
}
