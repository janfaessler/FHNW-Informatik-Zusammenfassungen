class Klient {
        public static void main(final String[] ARGS) {
                Kontext k = new Kontext();
                k.setStrategie(new KonkreteStrategieA());
                k.arbeite();    // "Weg A"
                k.setStrategie(new KonkreteStrategieB());
                k.arbeite();    // "Weg B"
        }
}
class Kontext {
        private Strategie strategie = null;
        public void setStrategie(final Strategie STRATEGIE) {
                strategie = STRATEGIE;
        }
        public void arbeite() {
                if (strategie != null) strategie.algorithmus();
        }
}
interface Strategie { void algorithmus(); }
class KonkreteStrategieA implements Strategie {
        public void algorithmus() { System.out.println("Weg A"); }
}
class KonkreteStrategieB implements Strategie {
        public void algorithmus() { System.out.println("Weg B"); }
}