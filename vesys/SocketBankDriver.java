public class SocketBankDriver implements RemoteDriver {
	private RemoteBank bank = null;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	public void connect(String[] args) throws IOException {
		socket = new Socket(args[0], Integer.valueOf(args[1]));
		bank = new RemoteBank(this);
		out = new PrintWriter(socket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public void disconnect() throws IOException{
		sendCommand("disconnect");
		socket.close(); bank = null;
	}
	public bank.Bank getBank(){ return bank; }
	public String sendCommand(String command) throws IOException {
		return sendCommand(command, "");
	}
	public String  sendCommand(String command, String param) throws IOException {
		out.println(command + ":" + param);
		out.flush();
		String input = in.readLine();
		String result = "";
		if (input != null) {
			String[] temp = input.split(":");
			result = (temp.length > 1 ? temp[1] : null );
		} 
		return result;
	}
}
