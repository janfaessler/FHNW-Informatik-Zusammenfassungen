static byte[] utfToLatin1(String s) {
  byte[] array = new byte[s.length()];
  int j = 0;
  for (int i = 0; i < s.length(); i++) {
    char c = s.charAt(i);
    if (c >= 256 && (c < 0xdc00 || c >= 0xdfff)) {
      array[j++] = (byte) '?';
    } else if (c < 256) {
      array[j++] = (byte) c;
    } //else: Low-Surrogate
  }
  for (int i = j; i < s.length(); i++) {
    array[j] = (byte) 0;
  }
  return array;
}