public class BellmanFord {
	public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;
	private static Knoten startKnoten;
	private static List<DoppelKnoten> reihenfolge = new ArrayList<DoppelKnoten>();
	private static void startBellmanFord(ArrayList<Knoten> knoten) {
		startKnoten = knoten.get(0);
		startKnoten.setDistance(0);
		for(int i = 0; i < knoten.size(); i++) {
			for(int k = 0; k < reihenfolge.size(); k++) {
				test(reihenfolge.get(k).getKnoten1(), reihenfolge.get(k).getKnoten2());
			}
		}
	}
	private static boolean test(Knoten u, Knoten v) {
		if(u.getDistance() != INFINITE_DISTANCE) {
			int weight = u.getWeight(v);
			if(v.getDistance() > (u.getDistance() + weight)) {
				v.setDistance(u.getDistance() + weight);
				v.setShortestNeighboar(u);
				return true;
			} else return false;
		} else return false;
	}
}
