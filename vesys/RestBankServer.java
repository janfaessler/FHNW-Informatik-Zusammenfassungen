public class RestBankServer {
	private final int port;
	private HttpServer server;
	public RestBankServer(int p) throws IllegalArgumentException, NullPointerException, IOException {
		port = p;
		ResourceConfig rc = new ApplicationAdapter(new BankApplication(new MyBank()));
		server = GrizzlyServerFactory.createHttpServer("http://localhost:"+port, rc);
	}
	public void start() throws IOException {
		server.start();
		System.in.read();
		server.stop();
	}
	public static void main(String[] args) throws IOException {
		RestBankServer server = new RestBankServer(Integer.valueOf(args[0]));
		server.start();
	}
	public class BankApplication extends Application {
		private Set<Object> singletons = new HashSet<Object>();
		private Set<Class<?>> classes = new HashSet<Class<?>>();
		public BankApplication(MyBank b) {
			singletons.add(new RestHandler(b));
		}
		public Set<Class<?>> getClasses() { return classes; }
		public Set<Object> getSingletons() { return singletons; }
	}
}

@Singleton @Path("/bank")
public class RestHandler implements RequestHandler {
	private CommandHandler cHandler;
	public RestHandler(MyBank b) {
		cHandler = new CommandHandler(b, this);
	}
    @POST @Path("/accounts/create") @Produces("application/plain")
	public String postCreateAccount(@FormParam("owner") String owner) throws IOException {
		return cHandler.handleCommand("createAccount", owner);
	}
    
	@DELETE @Path("/accounts/close/{id}") @Produces("application/plain")
	public String deleteCloseAccount(@PathParam("id") String id) throws IOException {
		return cHandler.handleCommand("closeAccount", id);
	}
	@GET @Path("/accounts/owner/{id}") @Produces("application/plain")
	public String getOwner(@PathParam("id") String id) throws IOException {
		return cHandler.handleCommand("getOwner", id);
	}
	@GET @Path("/accounts") @Produces("application/plain")
	public String getAccountNumbers() throws IOException {
		return cHandler.handleCommand("getAccountNumbers", "");
	}
	@GET @Path("/accounts/status/") @Produces("application/plain")
	public String getStatus() throws IOException {
		return "null";
	}
	@GET @Path("/accounts/status/{id}") @Produces("application/plain")
	public String getStatus(@PathParam("id") String id) throws IOException {
		return cHandler.handleCommand("isActive", id);
	}
	@PUT @Path("accounts/deposit/{id}") @Produces("application/plain")
	public String putDeposit(@PathParam("id") String id, @FormParam("value") String value) throws IOException {
		return cHandler.handleCommand("deposit", id + ";" + value);
	}
	@PUT @Path("accounts/withdraw/{id}") @Produces("application/plain")
	public String putWithdraw(@PathParam("id") String id, @FormParam("value") String value) throws IOException {
		return cHandler.handleCommand("withdraw", id + ";" + value);
	}
	@GET @Path("/accounts/balance/{id}") @Produces("application/plain")
	public String getBalance(@PathParam("id") String id) throws IOException {
		return cHandler.handleCommand("getBalance", id);
	}
}

