public char[] utf32to16(char[] array, int pos) {
	char c = array[pos];
    if (c >= (2^16)) {
        array[pos] = (char) (0xD800 + ((c & 0xFFFFFF) >> 10));
        array[pos+1] = (char) (0xDC00 + (c & 0x3FF));
    } else array[pos] = c;        
    return array; 
}