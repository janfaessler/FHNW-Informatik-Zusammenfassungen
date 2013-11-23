public class Breitensuche {
	private Queue<Knoten> nachbarknoten = new LinkedList<Knoten>();
	private void breitensuche(ArrayList<Knoten> knoten) {
		Knoten start = knoten.get(0);
		start.setFound(true);
		nachbarknoten.add(start);
		
		// Solange Warteschlange nicht leer
		while(nachbarknoten.size() > 0) {
			Knoten u = nachbarknoten.poll();
			
			for(int i = 0; i < u.getInzidenteKnoten().size(); i++) {
				Knoten v = u.getInzidenteKnoten().get(i);
				if(!v.isFound()) {
					System.out.print(" -> " + v.getName());
					v.setFound(true);
					nachbarknoten.add(v);
				}
			}
		}
	}
}
