class Addition {
    public static int add (int a, int b) {
        int c, r, t;

        r = a ^ b;
        c = a & b;
        while (c != 0) {
            c <<= 1;
            t = r;
            r ^= c;
            c &= t;
        }
        return r;
    }
}