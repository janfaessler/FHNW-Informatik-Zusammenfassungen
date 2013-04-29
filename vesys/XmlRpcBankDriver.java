public class XmlRpcBankDriver implements RemoteDriver {
	private RemoteBank bank = null;
	private XmlRpcClient client = null;
	public void connect(String[] args) throws IOException {
		bank = new RemoteBank(this);
		String address = args[0];
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(address));
		client = new XmlRpcClient();
		client.setConfig(config);
	}
	public void disconnect() throws IOException {
		sendCommand("disconnect");
	}
	public Bank getBank() { return bank; }
	public String sendCommand(String command) throws IOException {
		return sendCommand(command, "");
	}
	public String sendCommand(String command, String param) throws IOException {
		System.out.println("Client send: '"+command+":"+param+"'");
		List<Object> params = new ArrayList<Object>();
		params.add(command);
		params.add(param);
		String result;
		try {
			result = (String) client.execute("Bank.handle", params );
			System.out.println("Client receive: '"+result+"'");
		} catch (XmlRpcException e) { }
		return (result.length() > 0 ? result : null );
	}

}
