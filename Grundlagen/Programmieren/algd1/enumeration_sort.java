public static void sort(byte[] a) {
        int[] t = new int[256];
        int pos = 0;
        for (byte b: a) {
                t[b+128] ++;
        }
        for (int i=0; i< t.length; i++) {
                for (int k = 0; k<t[i]; k++) {
                        a[pos++] = (byte) (i-128);
                }
        }
}