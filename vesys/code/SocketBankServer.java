public class SocketBankServer {
	private final int port;
	private MyBank bank;
	
	public SocketBankServer(int p) {
		port = p; bank = new MyBank();
	}
	public void start() {
		try (ServerSocket server = new ServerSocket(port)) {
			while (true) {
				Socket s = server.accept();
				Thread t = new Thread(new SocketHandler(s, bank));
				t.start();
			}
		} catch (IOException e) { System.err.println(e.getMessage()); }
	}
	public static void main (String args[]) {
		SocketBankServer server = new SocketBankServer(Integer.valueOf(args[0]));
		server.start();
	}
}
public class SocketHandler implements RequestHandler, Runnable {
	private final Socket socket;
	private CommandHandler cHandler;
	private boolean running = false;
	public SocketHandler(Socket s, MyBank b) {
		socket = s;
		cHandler = new CommandHandler(b, this);
	}
	public void run() {
		running = true;
		System.out.println("handle connection from " + socket);
		try {
			while (running) {
				Request req = receiveResult();
				String result = cHandler.handleCommand(req.getCommand(), req.getParam());
				if (!req.getCommand().equals("disconnect")) sendResponse(req.getCommand(), result);
			}
		} catch (IOException e) { }
        finally {
			try { socket.close();
			} catch (IOException e) { }
		}
	}
	public void stop() throws IOException {
		socket.close(); running = false;
	}
	public void sendResponse(String command, String param) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		System.out.println("Server send: '"+command+":"+param+"'");
		out.println(command + ":" + param);
		out.flush();
	}
	public Request receiveResult() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String input = in.readLine();
		System.out.println("Server receive: '"+input+"'");
		String[] temp = input.split(":");
		return new Request(temp[0],temp.length > 1 ? temp[1] : "");
	}
}
