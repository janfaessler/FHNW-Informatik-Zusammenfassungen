public class Common {
    public static String firstLetterToUpper(final String WORDS) {
        String firstLetter = "";
        String restOfString = "";
        if (WORDS != null) {
            char[] letters = new char[1];
            letters[0] = WORDS.charAt(0);
            firstLetter = new String(letters).toUpperCase();
            restOfString = WORDS.toLowerCase().substring(1);
        }
        return firstLetter + restOfString;
    }
}
interface Statelike {
    void writeName(final StateContext STATE_CONTEXT, final String NAME);
}
class StateA implements Statelike {
    public void writeName(final StateContext STATE_CONTEXT, final String NAME) {
        System.out.println(Common.firstLetterToUpper(NAME));
        STATE_CONTEXT.setState(new StateB());
    }
}
class StateB implements Statelike {
    public void writeName(final StateContext STATE_CONTEXT, final String NAME) {
        System.out.println(NAME.toUpperCase());
        STATE_CONTEXT.setState(new StateA());
    }
}
public class StateContext {
    private Statelike myState;
    public StateContext() { setState(new StateA()); }
    public void setState(final Statelike NEW_STATE) { myState = NEW_STATE; }
    public void writeName(final String NAME) { myState.writeName(this, NAME); }
}