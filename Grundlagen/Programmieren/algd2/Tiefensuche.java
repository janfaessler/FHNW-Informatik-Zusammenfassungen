public class Tiefensuche {
	private static Stack<Knoten> knotenMitIndegNull = new Stack<Knoten>();
	private static void tiefensuche(ArrayList<Knoten> knoten) {
		for(int i = 0; i < knoten.size(); i++) 
			if(knoten.get(i).getColor()==Farbe.WEISS) visitKnoten(knoten.get(i));
	}
	private static void visitKnoten(Knoten v) {
		v.setColor(Farbe.GRAU);
		int anzInzidenzeKnoten = v.getInzidenteKnoten().size();
		for(int i = 0; i < anzInzidenzeKnoten; i++) {
			Knoten w = v.getInzidenteKnoten().get(i);
			if(w.getColor() == Farbe.WEISS) {
				w.setVorgaenger(v);
				visitKnoten(w);
			}
		}
		v.setColor(Farbe.SCHWARZ); System.out.print(" -> " + v.getName());
	}
}
