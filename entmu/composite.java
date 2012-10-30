interface Grafik {
    public void print();
}
class GrafikKompositum implements Grafik {
    final private List<Grafik> children = new ArrayList<Grafik>(10);
    public void print() {
        for (final Grafik grafik : children) grafik.print();
    }
    public void add(final Grafik component){children.add(component);}
    public void remove(final Grafik component){children.remove(component);}
}
class Ellipse implements Grafik {
    public void print() { System.out.println("Ellipse"); }
};
public class GrafikTest {
    public static void main(final String... args) {
        Ellipse ellipse1 = new Ellipse(),
                ellipse2 = new Ellipse(),
                ellipse3 = new Ellipse(),
                ellipse4 = new Ellipse();
        GrafikKompositum grafik1 = new GrafikKompositum(),
                         grafik2 = new GrafikKompositum(),
                         grafikGesamt = new GrafikKompositum();
        grafik1.add(ellipse1);
        grafik1.add(ellipse2);
        grafik1.add(ellipse3);
        grafik2.add(ellipse4);
        grafikGesamt.add(grafik1);
        grafikGesamt.add(grafik2);
        grafikGesamt.print();
    }
}