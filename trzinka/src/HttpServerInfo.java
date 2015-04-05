import java.net.*;
import java.io.*;

public class HttpServerInfo {
	public static void main(String[] args) throws Exception {
		String server = "gambee.com";
		if (args.length>0) server=args[0];
		HttpServerInfo si = new HttpServerInfo();
		System.out.println(si.getInfo(server));
	}

	private String getInfo(String server) throws UnknownHostException, IOException {

		Socket s = new Socket(InetAddress.getByName(server), 80);
		PrintWriter pw = new PrintWriter(s.getOutputStream());
		pw.print("GET / HTTP/1.1\r\n");
		pw.print("Host: "+server+"\r\n\r\n");
		pw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String t;
		while((t = br.readLine()) != null) {
			if (t.trim().length()==0) {
				break;
			}
			if (t.startsWith("Server:")) {
				s.close();		
				return t.substring(8);
			}
		}
		br.close();
		s.close();		
		return null;
	}
}

