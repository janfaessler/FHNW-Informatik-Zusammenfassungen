abstract class Prototype implements Cloneable {
    public Prototype clone() throws CloneNotSupportedException {
        return (Prototype) super.clone();
    }
    public abstract void setX(final short X);
    public abstract short getX();
}
class ConcretePrototype extends Prototype {
    private int x;
    public void setX(int X) { x = X; }
    public short getX() { return x; }
}
public class Client {
    public static void main(String args[])  {
        Prototype origin = new ConcretePrototype(10);
        Prototype clone =  origin.clone();
        clone.setX(4);
    }
}