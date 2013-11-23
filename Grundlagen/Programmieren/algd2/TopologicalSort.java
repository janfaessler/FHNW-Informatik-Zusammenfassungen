	private void topolopigalSort(HashSet<Knoten> knoten) {
		private Stack<Knoten> knotenMitIndegNull = new Stack<Knoten>();
		Iterator<Knoten> it = knoten.it();
		while (it.hasNext()) { 
			Knoten current = it.next(); 
			if (current.getIndeg() == 0) knotenMitIndegNull.add(current);
		}
		for(int i = 0; i < knoten.size(); i++) {
			if(knotenMitIndegNull.size() == 0) break; // keine Sortierung
			Knoten u = knotenMitIndegNull.get(0); 
			System.out.print(u.getName() + "->");
			int anzInzidenzeKnoten = u.getInzidenteKnoten().size();
			for(int k = 0; k < anzInzidenzeKnoten; k++) {
				Knoten v = u.getInzidenteKnoten().get(0);
				if(v.getIndeg() == 0) knotenMitIndegNull.add(v); 
				u.removeInzidenterKnoten(v);
			} knotenMitIndegNull.remove(u);
		}
	}

