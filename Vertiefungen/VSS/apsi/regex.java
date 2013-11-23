public class Email AddressChecker {
	public static void main(String[] args) {
		// Checks for email addresses starting with
		// inappropriate symbols like dots or @ signs.
		Pattern p = Pattern.compile("^\\.|^\\@");
		Matcher m = p.matcher(args[0]);
		if (m.find()) System.err.println("Email addresses don't start with dorts or @ signs.");
		
		// Cekcs for email addresses that start with
		// www. and prints a message if it does.
		p = Pattern.compile("^www\\.");
		m = p.matcher(args[0]);
		if (m.find()) System.err.println("Email addresses don't start with www, only web pages");
		
		p = Pattern.compile("[\\@] [\\.]+");
		m = p.matcher(args[0]);
		if (!m.find()) System.err.println("Emails must contain at most a @ and at least a .");
		
		p = Pattern.compile("^([A-Za-z0-9]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
		m = p.matcher(args[0]);
		Stringbuffer sb = new StringBuffer();
		boolean result = m.find();
		boolean deletedIllegalChars = false;
		while (result) {
			deletedIllegalChars = true;
			m.appendReplacement(sb, "");
			results = m.find();
		}
		
		// Add the last segment of input to the new String
		m.appendTail(sb);
		String input = sb.toString();
		System.out.println(input);
		if (deletedIllegalChars) {
			System.out.println("It contained incorrect characters, such as spaces or commas.");
		}
	}
}
	