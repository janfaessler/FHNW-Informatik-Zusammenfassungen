public class SkipListe {
	private static double p = 0.7;
	private Element m_headAnchor; // kleinst moeglicher key, level = MaxLevel
	private Element m_tailAnchor; // groesst moeglicher key, level = MaxLevel
	private int m_maxLevel;       // speichert den maximalen Level
	public SkipListe(int maxLevel) {
		m_maxLevel = maxLevel;
		m_headAnchor = new Element(Integer.MIN_VALUE, null, m_maxLevel);
		m_tailAnchor = new Element(Integer.MAX_VALUE, null, m_maxLevel);
		for (int i = 0; i <= m_maxLevel; i++) m_headAnchor.setNext(i, m_tailAnchor);			
	}
	public Object suchen(int key) {
		Element aktuell = m_headAnchor;
		for (int i = m_maxLevel; i >= 0; i--) {
			while (aktuell.getNext(i).getKey() <  key) aktuell = aktuell.getNext(i);
		}
		aktuell = aktuell.getNext(0);
		if (aktuell.getKey() == key) return aktuell.getData();
		else return Integer.MAX_VALUE;
	}
	public void einfuegen(int key, Object object) {
		Element[] update = new Element[m_maxLevel + 1];
		Element aktuell = m_headAnchor;
		for (int i = m_maxLevel; i >= 0; i--) {
			while (aktuell.getNext(i).getKey() <  key) aktuell = aktuell.getNext(i);
			update[i] = aktuell;
		}
		aktuell = aktuell.getNext(0);
		if (aktuell.getKey() == key) aktuell.setData(object);
		else {
			Element neuesElement = new Element(key,object,randomLevel());
			for (int i = 0; i <= neuesElement.getLevel(); i++) {
				neuesElement.setNext(i, update[i].getNext(i));
				update[i].setNext(i, neuesElement);
			}
		}
	}	
	public void entfernen(int key) {
		Element[] update = new Element[m_maxLevel + 1];
		Element aktuell = m_headAnchor;
		for (int i = m_maxLevel; i >= 0; i--) {
			while (aktuell.getNext(i).getKey() <  key) aktuell = aktuell.getNext(i);
			update[i] = aktuell;
		}
		aktuell = aktuell.getNext(0);
		for (int i = 0; i <= aktuell.getLevel(); i++) update[i].setNext(i, aktuell.getNext(i));
		aktuell = null;
	}
	public int randomLevel() {
		int level = 0;
		while (level < m_maxLevel && Math.random() < p) level++;
		return level;
	}
}
