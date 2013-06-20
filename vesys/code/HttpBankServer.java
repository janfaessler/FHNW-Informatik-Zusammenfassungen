public class HttpBankServer {
    private final int port;
	private MyBank bank;
	public HttpBankServer(int p) {
		port = p;
		bank = new MyBank();
	}
	public void start() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/bank", new HttpRequestHandler(bank)).getFilters().add(new ParameterParser());
		server.start();
	}
    public static void main(String[] args) throws IOException {
		HttpBankServer server = new HttpBankServer(Integer.valueOf(args[0]));
		server.start();
	}
}
public class HttpRequestHandler implements HttpHandler, RequestHandler {
	private CommandHandler cHandler;
	private HttpExchange exchange;
	public HttpRequestHandler(MyBank b) {
		cHandler = new CommandHandler(b, this);
	}
	public void handle(HttpExchange httpExchange) throws IOException {
		exchange = httpExchange;
		exchange.getResponseHeaders().add("Content-type", "text/html");
		
		Map<String, Object> params = (Map<String, Object>) httpExchange.getAttribute("parameters");
		String cmd = (String) params.get("cmd");
		String param = (String) params.get("param");
		String result = cHandler.handleCommand(cmd, param);
		String response =  cmd + ":" + result;
		exchange.sendResponseHeaders(200, response.length());
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}

