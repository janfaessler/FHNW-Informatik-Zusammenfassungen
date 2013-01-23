interface DekoratorMuster
{
    abstract class Spielfigur {
        public abstract void Drohe();
    }
    class Monster extends Spielfigur {
        public void Drohe() { System.out.println("Grrrrrrrrrr."); }
    }
    class Dekorierer extends Spielfigur
    {
        private Spielfigur meineFigur;
        public Dekorierer(Spielfigur s) { meineFigur = s; } 
        public override void Drohe() { meineFigur.Drohe(); }
    }
    class HustenDekorierer extends Dekorierer {
        public HustenDekorierer(Spielfigur s) { super(s); }
        public override void Drohe() {
            System.out.println("Hust, hust. ");
            base.Drohe();
        }
    }
    class SchnupfenDekorierer extends Dekorierer {
        public SchnupfenDekorierer(Spielfigur s) { super(s); }
        public override void Drohe() {
            System.out.println("Schniff. ");
            base.Drohe();
        }
    }
    public class Main {
        public static void main(String[] args) {
            Spielfigur monster = new Monster();
            monster.Drohe();
            Spielfigur hustenMonster = new HustenDekorierer(monster);
            hustenMonster.Drohe();
            Spielfigur schnupfenMonster = new SchnupfenDekorierer(monster);
            schnupfenMonster.Drohe();
            Spielfigur hustenSchnupfen = new SchnupfenDekorierer(new HustenDekorierer(monster));
            hustenSchnupfen.Drohe();
        }
    }
}