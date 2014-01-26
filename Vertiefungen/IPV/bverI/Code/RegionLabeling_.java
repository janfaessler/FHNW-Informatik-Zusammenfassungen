import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import java.awt.*;
import java.util.*;
import java.util.Random;

public class RegionLabeling_ implements PlugInFilter {

	class Node {
		int x, y;
		Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	};

	public int setup(String arg, ImagePlus img)
	{
		return DOES_8G + NO_CHANGES;
	}

	// Recursive implementation, just for small images.
	public void floodFillRecursive(ImageProcessor image, int u, int v, int regionLabel)
	{
		if(u >= 0 && u < image.getWidth() && v >= 0 && v < image.getHeight() && image.get(u, v) == 0) {
			image.set(u, v, regionLabel);

			floodFill(image, u+1, v, regionLabel);
			floodFill(image, u, v+1, regionLabel);
			floodFill(image, u-1, v, regionLabel);
			floodFill(image, u, v-1, regionLabel);
			
		}
	}

	// Breath first implementation with queue
	public void floodFill(ImageProcessor image, int u, int v, int regionLabel)
	{
		final int M = image.getWidth();
		final int N = image.getHeight();
		Deque<Node> q = new LinkedList<Node>();
		
		q.addFirst(new Node(u, v));
		while (!q.isEmpty()) {
			Node n = q.removeLast();
			if((n.x >= 0) && (n.x < M) && (n.y >= 0) && (n.y < N) && (image.get(n.x, n.y) == 0) ) {
				image.set(n.x, n.y, regionLabel);

				q.addFirst(new Node(n.x+1, n.y));
				q.addFirst(new Node(n.x, n.y+1));
				q.addFirst(new Node(n.x, n.y-1));
				q.addFirst(new Node(n.x-1, n.y));
			}
		}
	}

	public ByteProcessor convertToBinaryImage(ImageProcessor orig, int threshold)
	{
		int M = orig.getWidth();
		int N = orig.getHeight();
		ByteProcessor image = new ByteProcessor(M, N);

		for(int v=0; v < N; v++) {
			for(int u = 0; u<M; u++) {
				if (orig.get(u, v) < threshold) {
					image.set(u, v, 1);
				} else {
					image.set(u, v, 0);
				}
			}
		}
		return image;
	}

	public void run(ImageProcessor orig)
	{
		int M = orig.getWidth();
		int N = orig.getHeight();

		final int threshold = 100;
		int regionLabel = 2;

		// convert to binary image
		ByteProcessor image = convertToBinaryImage(orig, threshold);
		
		// find and label regions
		for(int v = 0; v<N; v++) {
			for(int u = 0; u<M; u++) {
				if (image.get(u, v) == 0) {
					floodFill(image, u, v, regionLabel);
					regionLabel++;
				}
			}
		}
		
		// create color table
		Random random = new Random();
		Color[] table = new Color[regionLabel - 1];
		table[0] = Color.BLACK;
		for(int i=1; i < table.length; i++) {
			table[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		}
		
		// create false-color image
		int[] rgb = new int[3];
		ImageProcessor color = new ColorProcessor(M, N);
		for(int v = 0; v<N; v++) {
			for(int u = 0; u<M; u++) {
				Color c = table[image.get(u, v) - 1];
				color.set(u, v, c.getRGB());
			}
		}
		ImagePlus im2 = new ImagePlus("Output", color);
		im2.show();
	}
}
