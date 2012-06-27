class Multiplication {
    public static long mult (int a, int b) {
        long y = 0;

        while (a != 0) {
            if (a % 2 == 1) y += b;
            b <<= 1;
            a >>= 1;
        }
        return y;
    }
}