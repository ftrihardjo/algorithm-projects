import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.AcyclicSP;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private final double BORDER = 1000.0;
    private double[][] energy;
    public SeamCarver(Picture picture) { // create a seam carver object based on the given picture
	if (picture == null) {
	    throw new IllegalArgumentException("invalid picture");
	} else {
	    this.picture = new Picture(picture);
	    energy = new double[height()][width()];
	    for (int i = 0; i < height(); i++) {
		for (int j = 0; j < width(); j++) {
		    energy[i][j] = energy(j,i);
		}
	    }
	}
    }
    public Picture picture() { // current picture
	return new Picture(picture);
    }
    public int width() { // width of current picture
	return picture.width();
    }
    public int height() { // height of current picture
	return picture.height();
    }
    public double energy(int x, int y) { // energy of pixel at column x and row y
	if (x >= 0 && y >= 0 && x < width() && y < height() && (x == 0 || x == width()-1 || y == 0 || y == height()-1)) {
	    return BORDER;
	} else if (x >= 0 && y >= 0 && x < width() && y < height()) {
	    Color color1 = picture.get(x-1,y), color2 = picture.get(x+1,y), color3 = picture.get(x,y-1), color4 = picture.get(x,y+1);
	    double Rx = color1.getRed()-color2.getRed(), Ry = color3.getRed()-color4.getRed(), Gx = color1.getGreen()-color2.getGreen();
	    double Gy = color3.getGreen()-color4.getGreen(), Bx = color1.getBlue()-color2.getBlue(), By = color3.getBlue()-color4.getBlue();
	    return Math.sqrt(Rx*Rx+Gx*Gx+Bx*Bx+Ry*Ry+Gy*Gy+By*By);
	} else {
	    throw new IllegalArgumentException("invalid coordinate");
	}
    }
    public int[] findHorizontalSeam() { // sequence of indices for horizontal seam
	int n = 0;
	int[] seam = new int[width()];
	EdgeWeightedDigraph G = new EdgeWeightedDigraph(width()*height()+2);	
	for (int i = 0; i < height(); i++) {
	    G.addEdge(new DirectedEdge(width()*height(),i*width(),energy[i][0]));
	    G.addEdge(new DirectedEdge((i+1)*width()-1,width()*height()+1,0.0));
	}	    
	for (int i = 0; i < height(); i++) {
	    for (int j = 0; j < width()-1; j++) {
		if (i != 0) {
		    G.addEdge(new DirectedEdge(i*width()+j,(i-1)*width()+j+1,energy[i-1][j+1]));
		}
		if (i != height()-1) {
		    G.addEdge(new DirectedEdge(i*width()+j,(i+1)*width()+j+1,energy[i+1][j+1]));
		}
		G.addEdge(new DirectedEdge(i*width()+j,i*width()+j+1,energy[i][j+1]));
	    }
	}
	for (DirectedEdge e : new AcyclicSP(G,height()*width()).pathTo(height()*width()+1)) {
	    if (n < seam.length) {
		seam[n++] = e.to()/width();
	    }
	}
	return seam;
    }
    public int[] findVerticalSeam() { // sequence of indices for vertical seam
	int n = 0;
	int[] seam = new int[height()];
	EdgeWeightedDigraph G = new EdgeWeightedDigraph(width()*height()+2);
	for (int i = 0; i < width(); i++) {
	    G.addEdge(new DirectedEdge(width()*height(),i,energy[0][i]));
	    G.addEdge(new DirectedEdge((height()-1)*width()+i,width()*height()+1,0.0));
	}
	for (int i = 0; i < height()-1; i++) {
	    for (int j = 0; j < width(); j++) {
		if (j != 0) {
		    G.addEdge(new DirectedEdge(i*width()+j,(i+1)*width()+j-1,energy[i+1][j-1]));
		}
		if (j != width()-1) {
		    G.addEdge(new DirectedEdge(i*width()+j,(i+1)*width()+j+1,energy[i+1][j+1]));
		}
		G.addEdge(new DirectedEdge(i*width()+j,(i+1)*width()+j,energy[i+1][j]));
	    }
	}
	for (DirectedEdge e : new AcyclicSP(G,height()*width()).pathTo(height()*width()+1)) {
	    if (n < seam.length) {
		seam[n++] = e.to()%width();
	    }
	}
	return seam;
    }
    public void removeHorizontalSeam(int[] seam) { // remove horizontal seam from current picture
	if (seam == null || wrong_horizontal_seam(seam) || height() <= 1 || width() != seam.length) {
	    throw new IllegalArgumentException("invalid seam or operation");
	} else {
	    Picture temp = new Picture(width(),height()-1);
	    for (int i = 0; i < width(); i++) {
		for (int j = 0, n = 0; j < height(); j++) {
		    if (seam[i] != j) {
			temp.setRGB(i,n,picture.getRGB(i,j));
		        energy[n++][i] = energy[j][i];
		    }
		}
	    }
	    picture = temp;
	    for (int i = 0; i < width(); i++) {
	        if (seam[i] != 0) {
		    energy[seam[i]-1][i] = energy(i,seam[i]-1);
		}
		if (seam[i] != height()) {
		    energy[seam[i]][i] = energy(i,seam[i]);
		}
	    }
	}
    }
    public void removeVerticalSeam(int[] seam) { // remove vertical seam from current picture
	if (seam == null || wrong_vertical_seam(seam) || width() <= 1 || height() != seam.length) {
	    throw new IllegalArgumentException("invalid seam or operation");
	} else {
	    Picture temp = new Picture(width()-1,height());
	    for (int i = 0; i < height(); i++) {
		for (int j = 0, n = 0; j < width(); j++) {
		    if (seam[i] != j) {
			temp.setRGB(n,i,picture.getRGB(j,i));
		        energy[i][n++] = energy[i][j];
		    }
		}
	    }
	    picture = temp;
	    for (int i = 0; i < height(); i++) {
	        if (seam[i] != 0) {
		    energy[i][seam[i]-1] = energy(seam[i]-1,i);
		}
		if (seam[i] != width()) {
		    energy[i][seam[i]] = energy(seam[i],i);
		}
	    }
	}
    }
    private boolean wrong_vertical_seam(int[] seam) {
	int i;
	for (i = 0; i < seam.length-1; i++) {
	    if (seam[i] < 0 || seam[i] >= width() || Math.abs(seam[i]-seam[i+1]) > 1) {
		return true;
	    }
	}
	if (seam[i] < 0 || seam[i] >= width()) {
	    return true;
	}
	return false;
    }
    private boolean wrong_horizontal_seam(int[] seam) {
	int i;
        for (i = 0; i < seam.length-1; i++) {
	    if (seam[i] < 0 || seam[i] >= height() || Math.abs(seam[i]-seam[i+1]) > 1) {
		return true;
	    }
	}
	if (seam[i] < 0 || seam[i] >= height()) {
	    return true;
	}
	return false;
    }
}
