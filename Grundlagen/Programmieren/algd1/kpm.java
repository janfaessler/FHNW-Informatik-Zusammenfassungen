public class KPM {
    String m_text;
    String m_pattern;
    int[] m_next;

    public KPM(String text, String pattern) {
        m_text = text;
        m_pattern = pattern;
        m_next = new int[pattern.length()];
    }
    
    public void initnext() {
        int i = 0; // wird das Pattern einmal durchlaufen
        int j = -1; // wird zum Vergleich mit dem Anfagsst¸ck verwendet

        m_next[i] = j;
        while (i < m_pattern.length() - 1) {
            if (j < 0 || m_pattern.charAt(i) == m_pattern.charAt(j)) {
                i++;
                j++;
                m_next[i] = j;

            } else {
                j = m_next[j];
            }
        }
    }

    public void search_kmp() {
        int t = 0;
        int p = 0;

        while (t < m_text.length()) {
            // p weiss, mit was im Pattern verglichen werden muss
            // t geht durch den Text
            if ((p < 0) || m_text.charAt(t) == m_pattern.charAt(p)) {
                t++;
                p++;
            } else {
                p = m_next[p];
            }
            if (p == m_pattern.length()) {
                System.out.println("Gefunden, Uebereinstimmung startet bei "
                        + (t - p + 1) + ". Zeichen");
                p = 0;
            }
        }
    }   
    
    public static void main(String[] args) {
         KPM kpm = new KPM("baabcabac", "bac");
         kpm.initnext();
         kpm.search_kmp();
    }
}