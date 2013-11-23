void Line_Bresenham(int x0, int y0, int x1, int y1) { 
	int dy =y1-y0;
	int dx = x1 - x0;
	int deltaE = (dy<<1) - (dx<<1);
	int deltaNE = (dy<<1);
	
	int stepx, stepy;
	if(dy<0){dy=-dy; stepy=-1;} else {stepy=1;} 
	if(dx<0){dx=-dx; stepx=-1;} else {stepx=1;}
	
	drawPoint(x0, y0);
	
	if (dx > dy) {
		int D = (dy<<1) - dx;
		while (x0 != x1) { 
			if(D<0) { D=D+deltaE; } 
			else { y0=y0+stepy; D = D + deltaNE; }
			x0 = x0 + stepx;
			drawPoint(x0, y0); }
		} else {...}
	}
}