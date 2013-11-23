public class HTTPSSErver {
	public static void main(String[] args) {
		SSLServerSocketFactory ssf = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		SSLServerSocket ss = ssf.createServerSocket(8443);
		
		System.out.println("SSLSocketServer is listening...");
		
		while (true) {
			try {
				Socket socket = ss.accept();
				OutputStream out = socket.getOutputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line = null;
				while(((line = in.readLine()) != null) && (!("".equals(line)))) {
					System.out.println(line);
				}
			
				StringBuffer buffer = new StringBuffer();
				buffer.append("<html>\n");
				buffer.append("<head><title>HTTPS Server</title></head>\n");
				buffer.append("<body>");
				// more noise here ...
				buffer.append("</body>\n</html>");

				byte[] data = buffer.toString().getBytes();
				out.write("HTTP/1.0 200 OK\n".getBytes());
				out.write(new String("Content-Length: "+data.length+"\n").getBytes());
				out.write(data);
				out.flush();
				out.close();
				in.close();
				s.close();
			} catch(Exception e) { e.printStackTrace(); }
		}
	}
}			