public class XmlRpcBankServer {
	private final int port;
	private static MyBank bank;
	public XmlRpcBankServer(int p) {
		port = p;
		bank = new MyBank();
	}
	public static MyBank getBank() { return bank; }
	public void start() throws XmlRpcException, IOException {
		WebServer server = new WebServer(port);
		XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		phm.addHandler("Bank", ch.fhnw.jfmk.bank.server.handler.XmlRpcHandler.class);
		xmlRpcServer.setHandlerMapping(phm);
        server.start();
	}
	public static void main(String[] args) throws XmlRpcException, IOException {
		XmlRpcBankServer server = new XmlRpcBankServer(Integer.valueOf(args[0]));
		server.start();
	}
}
public class XmlRpcHandler implements RequestHandler{
	private CommandHandler cHandler;
	public XmlRpcHandler () {
		cHandler = new CommandHandler(XmlRpcBankServer.getBank(), this);
	}
	public String handle(String command, String param) throws IOException {
		return cHandler.handleCommand(command, param);;
	}
}