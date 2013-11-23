int rek(int x) {
    if (x <= 1) {
        return x;
    } else {
        return x * rek(x-1);
    }
}