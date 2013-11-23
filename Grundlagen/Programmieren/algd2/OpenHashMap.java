abstract public class OpenHashMap<T> implements HashMap<T> {
	private static enum Zustand { FREI, BELEGT, ENTFERNT };
	private class Element { 
		private T m_data;
		private int m_key;
		private Zustand m_zustand;
        
public Element(int key, T data) { 
			m_data = data;
			m_key = key;
			m_zustand = Zustand.FREI;
		}
	}
	private Element[] m_HashTable;
	private int m_n;
	public OpenHashMap(int size) { 
		m_n = 0;
		m_HashTable = new OpenHashMap.Element[size]; 
		for (int i = 0; i < size; i++) m_HashTable[i] = new Element(-1, null);
	}
	protected int getTableSize() { return m_HashTable.length; }
	private Element find(int key) { 
		int j = 0, i, hashValue = h(key); 
		do {
			i = ((hashValue - s(j, key))%getTableSize() + getTableSize())%getTableSize();
			j++;
		} while (m_HashTable[i].m_zustand != Zustand.FREI && m_HashTable[i].m_key != key);
		if (m_HashTable[i].m_zustand == Zustand.BELEGT) {
			assert m_HashTable[i].m_key == key;
			return m_HashTable[i]; 
		} else return null;
	}
	abstract int h(int key);
	abstract int s(int j, int key);
}