class Line {
	private final Point start, stop;
	public Line(Point start, Point stop) {
		this.start = (Point) start.clone();
		this.stop = (Point) stop.clone();
	}
	public Point getStart() { return (Point) start.clone();  }
	public Point getStop() { return (Point) stop.clone(); }
	public Object clone() { return this; }
}