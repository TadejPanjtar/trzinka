import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

public class HSITest {

	public static void main(String[] args) throws Exception {
		File file = new File("servers.txt"); // where to save
		HttpServerInfo si = new HttpServerInfo();

		if (file.exists()) {

				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = null;
				String response = null;
				StringBuilder sb = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					int pos=line.indexOf(" ");
					if (pos>0) line = line.substring(0, pos);
					try {
						response = si.getInfo(line);
						System.out.println("server: " + line + " " + response);
						sb.append(line + " " + response + "\r\n");
					} catch (UnknownHostException e) {
						System.err.println("ERROR: " + line + " UNKNOWN HOST");
					} catch (IOException e) {
						//					e.printStackTrace();
						System.err.println("ERROR: " + line + " " + e.getMessage());
					}		
				}
				reader.close();
				
				FileWriter fileWritter;
				fileWritter = new FileWriter(file.getName());
				PrintWriter bufferWritter = new PrintWriter(fileWritter);
				bufferWritter.print(sb); 
				bufferWritter.close();

		} else // not servers from file
		{
			String server = "192.168.1.1"; // "gambee.com";
			if (args.length > 0)
				server = args[0];
			System.out.println(si.getInfo(server));
		}
	}

}
