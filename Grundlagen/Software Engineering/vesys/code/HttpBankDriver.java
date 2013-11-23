public class HttpBankDriver implements RemoteDriver {
	private String address;
	private RemoteBank bank = null;
	private URL url = null;
	public void connect(String[] args) throws IOException {
		bank = new RemoteBank(this);
		address = args[0];
		System.out.println(address);
	}
	public void disconnect() throws IOException {
		sendCommand("disconnect");
	}
	public Bank getBank() { return bank; }
	public String sendCommand(String command) throws IOException {
		return sendCommand(command, "");
	}
	public String sendCommand(String command, String param) throws IOException {
		url = new URL(address);
		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		c.setRequestProperty("User-Agent", "SocketBank/HTTPBankDriver");
		c.setRequestMethod("GET");
		c.setUseCaches (false);
	    c.setDoInput(true);
	    c.setDoOutput(true);
	    //Send request
	    DataOutputStream wr = new DataOutputStream(c.getOutputStream ());
	    wr.writeBytes("cmd="+command+"&param="+param);
	    wr.flush ();
	    wr.close ();
	    BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
	    String line;
		StringBuffer response = new StringBuffer(); 
	    while((line = r.readLine()) != null) { response.append(line); }
	    r.close();
	    String input = response.toString();
		String[] temp = input.split(":");
		return (temp.length > 1 ? temp[1] : null );
	}
}
