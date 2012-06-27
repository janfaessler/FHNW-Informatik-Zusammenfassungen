class OneBitCounter {
    public static int count (int x) {
        int iEven, iOdd, d = 1;
        iEven = x & 0x55555555; x >>= d; iOdd = x & 0x55555555;
        x = iOdd + iEven; d <<= 1;
        iEven = x & 0x33333333; x >>= d; iOdd = x & 0x33333333;
        x = iOdd + iEven; d <<= 1;
        iEven = x & 0x0F0F0F0F; x >>= d; iOdd = x & 0x0F0F0F0F;
        x = iOdd + iEven; d <<= 1;
        iEven = x & 0x0000FFFF; x >>= d; iOdd = x & 0x0000FFFF;
        x = iOdd + iEven; d <<= 1;
    }
}