void LineIncrement(int x0, int y0, int x1, int y1) { 
	int dy=y1-y0;
	int dx = x1 - x0;
	float t = 0.5f; // offset
	drawPoint(x0, y0);
	if (Abs(dx) > Abs(dy)) { 
		float m = (float) dy / (float) dx;
		t = t + y0;  // Initialisieren von y
		dx = (dx < 0) ? -1 : 1;
		m = m * dx;  // Teilsteigung berechnen
		while (x0 != x1) { 
			x0 = x0+dx; 
			t = t + m; // Steigung zu y addieren
			drawPoint(x0, (int) t);  // Runden durch Casting 
		} else {...}
	}
}