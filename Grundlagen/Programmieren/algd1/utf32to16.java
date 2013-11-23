static char[] codepoint2chars(int cp) {
  assert Character.isValidCodePoint(cp) : "illegal code point";
  if (cp < 0x10000) {
    if (cp >= 0xD800 && cp <= 0xDFFF)
      throw new IllegalArgumentException("illegal code point");
    return new char[] { (char) (cp & 0xFFFF) };
  } else {
    if (cp > 0x10FFFF)
      throw new IllegalArgumentException("illegal code point");
    cp -= 0x10000;
    char c1 = (char) ((cp >> 10) | 0xD800);
    char c2 = (char) ((cp & 0x3FF) | 0xDC00);
    return new char[] { c1, c2 };
  }
}