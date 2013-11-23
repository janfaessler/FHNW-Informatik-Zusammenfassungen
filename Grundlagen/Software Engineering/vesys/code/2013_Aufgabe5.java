// -------------------------------------------------------------
// Aufgabe a)
// -------------------------------------------------------------
class ClockServer {
	List<DataOutputStream> clients = new LinkedList<>();

	public static void main(String[] args) {
		new AdminThread().start();
		
		ServerSocket ss = new ServerSocket(1234);
		while(true) {
			Socket s = ss.accept();
			clients.add(new DataOutputStream(s.getOutputStream));
		}
	}

	notifyClients() {
		Iterator<DataOutputStream> it = clients.iterator();
		while(it.hasNext()) {
			DataOutputStream dos = it.next();
			try {
				dos.writeLong(time);
			} catch (IOException e) {
				it.remove();
			}
		}
	}
}

class AdminThread {
	@Override
	public void run() {
		ServerSocket ss = new ServerSocket(1235);
		while(true) {
			Socket s = ss.accept();
			DataInputStream dis = new DataInputStream(s.getInputStream);
			time = dis.readLong();
		}
	}
}

// -------------------------------------------------------------
// Aufgabe b)
// -------------------------------------------------------------
class ClockClient {
	public static void main(String[] args) {
		Socket s = new Socket("localhost", 1234);
		DataInputStream dis = new DataInputStream(s.getInputStream());
		while(true) {
			println(dis.readLong());
		}
	}
}

// -------------------------------------------------------------
// Aufgabe c)
// -------------------------------------------------------------
Der Client muss regelmässig (alle Sekunden) eine GET-Anfrage an den Server schicken.