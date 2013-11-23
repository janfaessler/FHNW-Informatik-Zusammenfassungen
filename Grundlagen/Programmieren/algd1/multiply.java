long multiply(int x, int y) {
	// 4. schwaechste Vorbedingung WP
	long a = x, b = y, r = 0;
	// 3. INV muss gelten
	while (a != 0) {
		// 2. INV & (a!=0) muss gelten
		if ((a & 1) == 1) r += b;
		b = b << 1;
		a = a >> 1;
		// 1. INV muss gelten
	} // 5. INV & (a = 0) muss gelten
	return r;
} //Nachbedingung S muss gelten