public class Dijkstra {
	private static List<Knoten> PERM;
	public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;
	private static Knoten startKnoten;
	private static PriorityQueue<Knoten> PQ;
    private final static Comparator<Knoten> shortestDistanceComparator = ;
	private static void startDijkstra(ArrayList<Knoten> knoten) {
		PERM = new ArrayList<Knoten>();
		PQ = new PriorityQueue<Knoten>(8, new Comparator<Knoten>() { 
			public int compare(Knoten left, Knoten right) {
            	int result = left.getDistance() - right.getDistance(); return result;
            }});
		startKnoten = knoten.get(0);
		startKnoten.setShortestNeighboar(null);
		startKnoten.setDistance(0);
		PQ.add(startKnoten);
		while(!PQ.isEmpty()) {
			Knoten u = PQ.remove();
			for(int i = 0; i < u.getInzidenteKnoten().size(); i++) {
				Knoten v = u.getInzidenteKnoten().get(i).getKnoten();
				if(v.getDistance() == INFINITE_DISTANCE) PQ.add(v);
				if(test(u,v)) { // update prio
					PQ.remove(v); 
					PQ.add(v);
				}
			}x
			PERM.add(u);
		}
	}
	private static boolean test(Knoten u, Knoten v) {
		int weight = u.getWeight(v);
		if(v.getDistance() > (u.getDistance() + weight)) {
			v.setDistance(u.getDistance() + weight);
			v.setShortestNeighboar(u);
			return true;
		} else return false;
	}
	public static class Knoten {
		int distance = INFINITE_DISTANCE;;
		Knoten shortestNeighboar = null;
		String name;
		List<Kante> inzidenteKnoten = new ArrayList<Kante>();
		public Knoten(String name) { name = name; }
		public int getWeight(Knoten v) {
			for(Kante k : inzidenteKnoten) if(k.getKnoten() == v) return k.getWeight();
			return 0;
		}
	    public int compareTo(Knoten k) { return this.distance - k.distance; }
	    
	}
}
